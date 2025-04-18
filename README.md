# Grian's Beta Mod

Grian's Beta Mod is a mod for Minecraft Beta 1.7.3, that aims to add new features that I thought were fun or made sense to add as a feature either to solve problems or introduce more content, while trying to stay true to the beta style/feel.

This mod is meant to be played standalone, it may work alongside other mods, but there may be compatibility issues.

### Current possibe places of conflict with other mods

- Vanilla Overworld Gen - Mainly the Icy Stone generation in cold biomes detailed below.

## Features

### New
- You can now consume Speed Crystal's which will lead to a 1.5x Speed Boost for 20 seconds.
    - Recipe: [![Image from Gyazo](https://i.gyazo.com/fc3a14850ea2879ae6bb47f923238fd2.png)](https://gyazo.com/fc3a14850ea2879ae6bb47f923238fd2)
- Backports redstone blocks for storage purposes, currently does not feature the redstone functionality.
- Adds Grassy Boots
    - While wearing these you will now longer trample crops.
    - Recipe: [![Image from Gyazo](https://i.gyazo.com/07979b73a12646950b87fc707cda5541.png)](https://gyazo.com/07979b73a12646950b87fc707cda5541)
- Adds recipe that lets you decraft a saddle into 5 Leather as saddles have no realistic uses.
- Taiga/Tundra Biomes now generate with Icy Stone & all associated variants (i.e ores, etc.)
    - This can be enabled/disabled like every other new feature but it will only affect newly generated chunks. So if you do this on an existing world, you'll only get the icy stone generated in new chunks.
- F3 Debug Menu now displays biome.
    - This is not configurable as it's for debugging.
- Adds a Sawmill Block
    - This block allows you to convert planks to stairs and slabs at a 1:2 rate for slabs and 1:1 rate for stairs
    - This block is configured by placing the desired output(i.e the slab or stair) on the left side of the block as indicated by the textures
    - Once there is a block configured, you can right click with a stack of planks and it will convert them to the output
    - Recipe: [![Image from Gyazo](https://i.gyazo.com/57c04b8fa47de9ddc228cb70283672af.png)](https://gyazo.com/57c04b8fa47de9ddc228cb70283672af)
- Adds Nether Glass
    - Recipe (Furnace): Soul Sand -> Nether Glass
    - It's somewhat red tinted glass but the transparent parts aren't tinted as the renderer doesn't support that out of the box and I don't want to bother with backporting it for one block, I might do it at some point in the future though.


### Fixes
- The following items now break faster with axes: Wooden Stairs, Crafting Table, Wooden Slab, Wooden Fence.
- The following items now break faster with pickaxes: Cobblestone Stairs, Redstone Ore, Furnace.
- Stairs now drop themselves.

## Configuration

All of the above New Features can be enabled/disabled individually, the fixes cannot be disabled.
