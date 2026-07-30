---
title: Climate
type: mechanics
description: Biome-dependent behavior for trees, flowers, and saplings.
categories:
  - Mechanics
  - World generation
---

The **Climate** system introduces new behavior based on the climate and biome the player is in.

## Birch Trees

Birch trees now generate with flowers around them, including the new Blue Peony flower.

{{< gallery >}}
  {{< gallery-image src="img/climate_birch_tree.png" alt="A birch tree surrounded by flowers" caption="A birch tree surrounded by flowers and Blue Peonies." >}}
{{< /gallery >}}

## Saplings

All leaf types drop the same sapling. The tree type is determined when that sapling is planted:

- In Taiga and Tundra biomes, it becomes a spruce sapling.
- Near at least three Blue Peonies, it becomes a birch sapling.
- In a Desert biome, it requires at least three nearby water blocks. Without them, it becomes a dead bush. With enough water, it becomes an oak sapling—or a birch sapling if enough Blue Peonies are also nearby.
- In all other cases, it remains an oak sapling.

{{< gallery >}}
  {{< gallery-image src="img/spruce_climate_saplings.png" alt="Spruce saplings in a Tundra biome" caption="Saplings become spruce saplings in Taiga and Tundra biomes." >}}
  {{< gallery-image src="img/desert_climate_saplings.png" alt="A setup for birch saplings in the Desert" caption="Water and Blue Peonies allow a birch sapling to survive in the Desert." >}}
{{< /gallery >}}

