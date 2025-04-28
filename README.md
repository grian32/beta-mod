# Grian's Beta Expansion

Grian's Beta Expansion is a mod for Minecraft Beta 1.7.3, that aims to add new features that I thought were fun or made sense to add as a feature either to solve problems or introduce more content, while trying to stay true to the beta style/feel.

This mod is meant to be played standalone, it may work alongside other mods, but there may be compatibility issues.

The project is currently in pretty early stages & doesn't include much additional content as of now, a lot more is planned however, so stay tuned!

## Disclaimer

I have no association with and I do not claim to be the youtuber of the same name, it's an unfortunate coincidence, I have had this name for many years and many people know me by it, which means I cannot change it.

## Installation

1. Install the Babric Prism Instance available [here](https://github.com/babric/prism-instance)
2. Add the following mods: [Glass Config API](https://modrinth.com/mod/glass-config-api), [Glass Networking](https://modrinth.com/mod/glass-networking), [Fabric Language Kotlin 1.10.18+kotlin1.9.22.](https://modrinth.com/mod/fabric-language-kotlin/version/1.10.18+kotlin.1.9.22), [StationAPI](https://modrinth.com/mod/stationapi) & [Mod Menu Beta](https://modrinth.com/mod/modmenu-beta) to your mods folder.
3. Add the jar you downloaded to the mods folder.

## Current possible places of conflict with other mods

- Vanilla Overworld Gen - Mainly the Icy Stone generation in cold biomes detailed below.
- WARNING: Item Enhancements modifies several vanilla blocks by adding blockstates to support certain features efficiently, this means that the Item Enhancements feature is not compatible with pre-existing worlds.

## Features

### Item Enhancements

Enhancements are my take on the enchantments added in later versions, this allows you to enhance your tools with various effects, for a set amount of a certain item/block. To give a tool an enhancement you must apply the ingredients of any base enhancement in the Enhancement Table

Unlike enchantments, you can only have one enhancement per tool & if you wish to gain a higher tier of each enhancement then you must upgrade it using the Enhancement Table.

Enhancement Table Recipe:
TODO

#### Enhancements

**Axes**
- Extra Logs: This enhancement double logs when broken from a naturally spawned tree, the chance goes up from 20% to guaranteed based on the tier.
  - Tier One - 20% - 48 Oak Logs
  - Tier Two - 45% - 16 Pile Of Logs
  - Tier Three - 75% - 32 Pile Of Logs
  - Tier Four - Guaranteed - 64 Pile Of Logs
- Resin Harvest: Logs have a chance to drop resin, resin can be used for [ TODO ]
  - Tier One - 20% - 64 Logs (any kind)
  - Tier Two - 35% - 48 Resin 
  - Tier Three - 60% - 32 Resin Blocks
- Sawdust Collector - Logs have a chance to drop 1-3 sawdust, which can be used as an fuel or to craft planks.
  - Tier One - 10% - 32 Logs (any kind)
  - Tier Two - 25% - TODO
  - Tier Three - 45% - TODO
  - Tier Four - 75$ - TODO
  - Tier Five - 90% - TODO
- Rotbane - Leaves decay instantly when one tree is broken.
  - Tier One - 64 Saplings (any kind)

#### Example Usage

TODO


### New

- You can now consume Speed Crystal's which will lead to a 1.5x Speed Boost for 20 seconds.
    - Recipe: [![Image from Gyazo](https://i.gyazo.com/fc3a14850ea2879ae6bb47f923238fd2.png)](https://gyazo.com/fc3a14850ea2879ae6bb47f923238fd2)
- Backports redstone blocks for storage purposes, currently does not feature the redstone functionality.
- Adds Grassy Boots
    - While wearing these you will now longer trample crops.
    - Recipe: [![Image from Gyazo](https://i.gyazo.com/07979b73a12646950b87fc707cda5541.png)](https://gyazo.com/07979b73a12646950b87fc707cda5541)
- Adds recipe that lets you decraft a saddle into 5 Leather as saddles have no realistic uses.
- WIP DON'T USE: Taiga/Tundra Biomes now generate with Icy Stone & all associated variants (i.e ores, etc.)
    - This can be enabled/disabled like every other new feature but it will only affect newly generated chunks. So if you do this on an existing world, you'll only get the icy stone generated in new chunks.
    - Wouldn't recommend enabling for now as caves don't work properly in both regular vanilla generation and icy stone generation
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
- Fences have smaller collision boxes that fit the model as seen in later versions.
- Adds Pile of Logs
    - A decorative item that can also be used for certain enhancement recipes.
    - Recipe: 

### Fixes
- The following items now break faster with axes: Wooden Stairs, Crafting Table, Wooden Fence.
- The following items now break faster with pickaxes: Cobblestone Stairs, Redstone Ore, Furnace.
- Slabs only break faster with their respective tools, i.e Stone/Sandstone/Cobblestone Slabs break faster with a Pickaxe & Wooden Slabs break faster with an Axe.
- Stairs now drop themselves.
- Code-defined recipes now work with stacks > 1.

## Configuration

All of the above Features & Fixes can be enabled/disabled individually, all of the above are enabled by default unless WIP & the mod is meant to be played with default configuration. If a certain feature is no longer WIP after an update, you will have to manually enable it.

## Credits

Font used in icon - https://fontenddev.com/fonts/alkhemikal/
