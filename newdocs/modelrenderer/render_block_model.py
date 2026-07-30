#!/usr/bin/env python3
"""Render a Minecraft-style block or JSON model to a transparent PNG.

This intentionally implements only the model features used by Project Beta
Expanded: axis-aligned elements, per-face UVs, texture variables, face texture
rotation, and element shading. It is an offline documentation asset tool, not a
game renderer. When the source is a texture instead of JSON, the renderer
automatically wraps it around a default six-sided cube.
"""

from __future__ import annotations

import argparse
import json
import math
from dataclasses import dataclass
from pathlib import Path
from typing import Iterable

from PIL import Image, ImageDraw, ImageFilter


FACE_NORMALS = {
    "up": (0.0, 1.0, 0.0),
    "down": (0.0, -1.0, 0.0),
    "north": (0.0, 0.0, -1.0),
    "south": (0.0, 0.0, 1.0),
    "west": (-1.0, 0.0, 0.0),
    "east": (1.0, 0.0, 0.0),
}

SHADE = {
    "up": 1.00,
    "down": 0.52,
    "north": 0.72,
    "south": 0.72,
    "west": 0.86,
    "east": 0.86,
}


@dataclass
class Texel:
    depth: float
    points: list[tuple[float, float]]
    color: tuple[int, int, int, int]


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description="Render a JSON block model or a texture-wrapped default cube."
    )
    parser.add_argument(
        "source",
        help=(
            "Model JSON, namespaced texture, PNG, or atlas tile in the form "
            "'atlas:path/to/atlas.png@x,y'"
        ),
    )
    parser.add_argument("assets", type=Path, help="Path to the Minecraft assets root")
    parser.add_argument("output", type=Path, help="Destination PNG")
    parser.add_argument("--size", type=int, default=512, help="Square output size")
    parser.add_argument("--yaw", type=float, default=45.0, help="Model yaw in degrees")
    parser.add_argument("--pitch", type=float, default=30.0, help="Model pitch in degrees")
    parser.add_argument(
        "--padding", type=float, default=0.17, help="Fractional image padding"
    )
    return parser.parse_args()


def load_model(path: Path) -> dict:
    with path.open("r", encoding="utf-8") as handle:
        return json.load(handle)


def default_cube(texture: str) -> dict:
    """Create the implicit six-sided model used by ordinary full blocks."""
    faces = {
        face: {"uv": [0, 0, 16, 16], "texture": "#all"}
        for face in FACE_NORMALS
    }
    return {
        "textures": {"all": texture},
        "elements": [
            {
                "from": [0, 0, 0],
                "to": [16, 16, 16],
                "faces": faces,
            }
        ],
    }


def resolve_texture_name(name: str, variables: dict[str, str]) -> str:
    seen: set[str] = set()
    while name.startswith("#"):
        key = name[1:]
        if key in seen:
            raise ValueError(f"Circular texture reference: {name}")
        seen.add(key)
        try:
            name = variables[key]
        except KeyError as error:
            raise ValueError(f"Unknown texture variable: #{key}") from error
    return name


def resolve_texture_path(name: str, assets: Path) -> Path:
    if ":" in name:
        namespace, relative = name.split(":", 1)
    else:
        namespace, relative = "minecraft", name

    candidates = (
        assets / namespace / "stationapi" / "textures" / f"{relative}.png",
        assets / namespace / "textures" / f"{relative}.png",
    )
    for candidate in candidates:
        if candidate.is_file():
            return candidate
    options = "\n".join(f"  - {candidate}" for candidate in candidates)
    raise FileNotFoundError(f"Unable to resolve texture {name!r}. Tried:\n{options}")


def load_texture(name: str, assets: Path) -> Image.Image:
    if name.startswith("atlas:"):
        specification = name.removeprefix("atlas:")
        try:
            atlas_path, tile = specification.rsplit("@", 1)
            tile_x, tile_y = (int(value) for value in tile.split(",", 1))
        except ValueError as error:
            raise ValueError(
                "Atlas textures must use 'atlas:path/to/atlas.png@x,y'"
            ) from error

        atlas = Image.open(Path(atlas_path)).convert("RGBA")
        return atlas.crop(
            (
                tile_x * 16,
                tile_y * 16,
                tile_x * 16 + 16,
                tile_y * 16 + 16,
            )
        )

    direct_path = Path(name)
    if direct_path.is_file():
        return Image.open(direct_path).convert("RGBA")

    return Image.open(resolve_texture_path(name, assets)).convert("RGBA")


def transform_point(
    point: tuple[float, float, float], yaw: float, pitch: float
) -> tuple[float, float, float]:
    x, y, z = (coordinate - 8.0 for coordinate in point)
    yaw_radians = math.radians(yaw)
    pitch_radians = math.radians(pitch)

    rotated_x = x * math.cos(yaw_radians) - z * math.sin(yaw_radians)
    rotated_z = x * math.sin(yaw_radians) + z * math.cos(yaw_radians)
    screen_y = y * math.cos(pitch_radians) - rotated_z * math.sin(pitch_radians)
    depth = y * math.sin(pitch_radians) + rotated_z * math.cos(pitch_radians)
    return rotated_x, -screen_y, depth


def transformed_normal_depth(
    normal: tuple[float, float, float], yaw: float, pitch: float
) -> float:
    x, y, z = normal
    yaw_radians = math.radians(yaw)
    pitch_radians = math.radians(pitch)
    rotated_z = x * math.sin(yaw_radians) + z * math.cos(yaw_radians)
    return y * math.sin(pitch_radians) + rotated_z * math.cos(pitch_radians)


def face_point(
    face: str,
    lower: list[float],
    upper: list[float],
    u: float,
    v: float,
) -> tuple[float, float, float]:
    x0, y0, z0 = lower
    x1, y1, z1 = upper

    if face == "up":
        return x0 + (x1 - x0) * u, y1, z0 + (z1 - z0) * v
    if face == "down":
        return x0 + (x1 - x0) * u, y0, z1 - (z1 - z0) * v
    if face == "north":
        return x1 - (x1 - x0) * u, y1 - (y1 - y0) * v, z0
    if face == "south":
        return x0 + (x1 - x0) * u, y1 - (y1 - y0) * v, z1
    if face == "west":
        return x0, y1 - (y1 - y0) * v, z0 + (z1 - z0) * u
    if face == "east":
        return x1, y1 - (y1 - y0) * v, z1 - (z1 - z0) * u
    raise ValueError(f"Unsupported face: {face}")


def shaded(
    color: tuple[int, int, int, int], face: str, should_shade: bool
) -> tuple[int, int, int, int]:
    if not should_shade:
        return color
    factor = SHADE[face]
    red, green, blue, alpha = color
    return (
        round(red * factor),
        round(green * factor),
        round(blue * factor),
        alpha,
    )


def iter_face_texels(
    face_name: str,
    face: dict,
    lower: list[float],
    upper: list[float],
    texture: Image.Image,
    should_shade: bool,
    yaw: float,
    pitch: float,
) -> Iterable[Texel]:
    uv = face.get("uv", [0, 0, 16, 16])
    left, top, right, bottom = (float(value) for value in uv)
    pixel_left = left / 16.0 * texture.width
    pixel_top = top / 16.0 * texture.height
    pixel_right = right / 16.0 * texture.width
    pixel_bottom = bottom / 16.0 * texture.height
    width = max(1, round(abs(pixel_right - pixel_left)))
    height = max(1, round(abs(pixel_bottom - pixel_top)))

    crop = texture.crop(
        (
            math.floor(pixel_left),
            math.floor(pixel_top),
            math.ceil(pixel_right),
            math.ceil(pixel_bottom),
        )
    )
    crop = crop.resize((width, height), Image.Resampling.NEAREST)
    rotation = int(face.get("rotation", 0)) % 360
    if rotation:
        crop = crop.rotate(-rotation, expand=False, resample=Image.Resampling.NEAREST)

    for pixel_y in range(height):
        for pixel_x in range(width):
            color = crop.getpixel((pixel_x, pixel_y))
            if len(color) == 3:
                color = (*color, 255)
            if color[3] == 0:
                continue

            u0 = pixel_x / width
            u1 = (pixel_x + 1) / width
            v0 = pixel_y / height
            v1 = (pixel_y + 1) / height
            corners = (
                face_point(face_name, lower, upper, u0, v0),
                face_point(face_name, lower, upper, u1, v0),
                face_point(face_name, lower, upper, u1, v1),
                face_point(face_name, lower, upper, u0, v1),
            )
            transformed = [transform_point(point, yaw, pitch) for point in corners]
            yield Texel(
                depth=sum(point[2] for point in transformed) / 4.0,
                points=[(point[0], point[1]) for point in transformed],
                color=shaded(color, face_name, should_shade),
            )


def render(model: dict, assets: Path, size: int, yaw: float, pitch: float, padding: float) -> Image.Image:
    variables = model.get("textures", {})
    textures: dict[str, Image.Image] = {}
    texels: list[Texel] = []
    projected_model_points: list[tuple[float, float]] = []

    for element in model.get("elements", []):
        lower = element["from"]
        upper = element["to"]
        should_shade = element.get("shade", True)

        for x in (lower[0], upper[0]):
            for y in (lower[1], upper[1]):
                for z in (lower[2], upper[2]):
                    px, py, _ = transform_point((x, y, z), yaw, pitch)
                    projected_model_points.append((px, py))

        for face_name, face in element.get("faces", {}).items():
            if transformed_normal_depth(FACE_NORMALS[face_name], yaw, pitch) <= 0:
                continue

            texture_name = resolve_texture_name(face["texture"], variables)
            if texture_name not in textures:
                textures[texture_name] = load_texture(texture_name, assets)

            texels.extend(
                iter_face_texels(
                    face_name,
                    face,
                    lower,
                    upper,
                    textures[texture_name],
                    should_shade,
                    yaw,
                    pitch,
                )
            )

    if not texels:
        raise ValueError("Model produced no visible texels")

    min_x = min(point[0] for point in projected_model_points)
    max_x = max(point[0] for point in projected_model_points)
    min_y = min(point[1] for point in projected_model_points)
    max_y = max(point[1] for point in projected_model_points)
    usable = size * (1.0 - padding * 2.0)
    scale = min(usable / (max_x - min_x), usable / (max_y - min_y))
    offset_x = size / 2.0 - ((min_x + max_x) / 2.0) * scale
    offset_y = size / 2.0 - ((min_y + max_y) / 2.0) * scale - size * 0.015

    shadow = Image.new("RGBA", (size, size), (0, 0, 0, 0))
    shadow_draw = ImageDraw.Draw(shadow)
    shadow_width = round(size * 0.48)
    shadow_height = round(size * 0.115)
    shadow_y = round(size * 0.755)
    shadow_draw.ellipse(
        (
            (size - shadow_width) // 2,
            shadow_y,
            (size + shadow_width) // 2,
            shadow_y + shadow_height,
        ),
        fill=(0, 0, 0, 90),
    )
    shadow = shadow.filter(ImageFilter.GaussianBlur(size * 0.025))

    output = Image.new("RGBA", (size, size), (0, 0, 0, 0))
    output.alpha_composite(shadow)
    draw = ImageDraw.Draw(output, "RGBA")

    for texel in sorted(texels, key=lambda entry: entry.depth):
        points = [
            (round(x * scale + offset_x), round(y * scale + offset_y))
            for x, y in texel.points
        ]
        draw.polygon(points, fill=texel.color)

    return output


def main() -> None:
    args = parse_args()
    source_path = Path(args.source)
    if source_path.suffix.lower() == ".json":
        model = load_model(source_path)
    else:
        model = default_cube(args.source)
    image = render(
        model=model,
        assets=args.assets,
        size=args.size,
        yaw=args.yaw,
        pitch=args.pitch,
        padding=args.padding,
    )
    args.output.parent.mkdir(parents=True, exist_ok=True)
    image.save(args.output, optimize=True)
    print(f"Rendered {args.source} -> {args.output}")


if __name__ == "__main__":
    main()
