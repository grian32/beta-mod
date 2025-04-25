package me.grian.griansbetamod.icystone

import me.grian.griansbetamod.BetaMod
import me.grian.griansbetamod.api.craftingrecipes.ShapedRecipeBuilder
import me.grian.griansbetamod.api.craftingrecipes.addShapedRecipe
import me.grian.griansbetamod.api.craftingrecipes.addSmeltingRecipe
import me.grian.griansbetamod.config.ConfigScreen
import me.grian.griansbetamod.icystone.blocks.*
import me.grian.griansbetamod.util.isEventTypeShapeless
import net.mine_diver.unsafeevents.listener.EventListener
import net.mine_diver.unsafeevents.listener.ListenerPriority
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent

object IcyStoneListener {
    private var redstoneOre = 0

    @EventListener
    fun registerBlocks(event: BlockRegistryEvent) {
        if (ConfigScreen.config.icyStone) {
            with (BetaMod) {
                icyStone = IcyStoneBlock(NAMESPACE.id("icy_stone"))
                    .setTranslationKey(NAMESPACE, "icy_stone")
                    .setSoundGroup(Block.STONE_SOUND_GROUP)
                    .setHardness(1.5f)
                    .setResistance(10.0f)

                icyCobblestone = IcyCobblestoneBlock(NAMESPACE.id("icy_cobblestone"))
                    .setTranslationKey(NAMESPACE, "icy_cobblestone")
                    .setSoundGroup(Block.STONE_SOUND_GROUP)
                    .setHardness(2.0f)
                    .setResistance(10.0f)

                icyCoalOre = IcyCoalOreBlock(NAMESPACE.id("icy_coal_ore"))
                    .setTranslationKey(NAMESPACE, "icy_coal_ore")
                    .setSoundGroup(Block.STONE_SOUND_GROUP)
                    .setHardness(3.0F)
                    .setResistance(5.0F)

                icyIronOre = IcyIronOreBlock(NAMESPACE.id("icy_iron_ore"))
                    .setTranslationKey(NAMESPACE, "icy_iron_ore")
                    .setSoundGroup(Block.STONE_SOUND_GROUP)
                    .setHardness(3.0F)
                    .setResistance(5.0F)

                icyGoldOre = IcyGoldOreBlock(NAMESPACE.id("icy_gold_ore"))
                    .setTranslationKey(NAMESPACE, "icy_gold_ore")
                    .setSoundGroup(Block.STONE_SOUND_GROUP)
                    .setHardness(3.0F)
                    .setResistance(5.0F)

                icyDiamondOre = IcyDiamondOreBlock(NAMESPACE.id("icy_diamond_ore"))
                    .setTranslationKey(NAMESPACE, "icy_diamond_ore")
                    .setSoundGroup(Block.STONE_SOUND_GROUP)
                    .setHardness(3.0F)
                    .setResistance(5.0F)

                icyLapisLazuliOre = IcyLapisLazuliOreBlock(NAMESPACE.id("icy_lapis_lazuli_ore"))
                    .setTranslationKey(NAMESPACE, "icy_lapis_lazuli_ore")
                    .setSoundGroup(Block.STONE_SOUND_GROUP)
                    .setHardness(3.0F)
                    .setResistance(5.0F)

                icyRedstoneOre = IcyRedstoneOreBlock(NAMESPACE.id("icy_redstone_ore"), false)
                    .setTranslationKey(NAMESPACE, "icy_redstone_ore")
                    .setSoundGroup(Block.STONE_SOUND_GROUP)
                    .setHardness(3.0F)
                    .setResistance(5.0F)

                litIcyRedstoneOre = IcyRedstoneOreBlock(NAMESPACE.id("lit_icy_redstone_ore"), true)
                    .setTranslationKey(NAMESPACE, "lit_icy_redstone_ore")
                    .setSoundGroup(Block.STONE_SOUND_GROUP)
                    .setHardness(3.0F)
                    .setResistance(5.0F)
                    .setLuminance(0.625F)
                    .ignoreMetaUpdates()
            }
        }
    }

    @EventListener
    fun registerTextures(event: TextureRegisterEvent) {
        val terrain = Atlases.getTerrain()

        if (ConfigScreen.config.icyStone) {
            with (BetaMod) {
                icyStone.textureId = terrain.addTexture(NAMESPACE.id("block/icy_stone")).index
                icyCobblestone.textureId = terrain.addTexture(NAMESPACE.id("block/icy_cobblestone")).index
                icyCoalOre.textureId = terrain.addTexture(NAMESPACE.id("block/icy_coal_ore")).index
                icyIronOre.textureId = terrain.addTexture(NAMESPACE.id("block/icy_iron_ore")).index
                icyGoldOre.textureId = terrain.addTexture(NAMESPACE.id("block/icy_gold_ore")).index
                icyDiamondOre.textureId = terrain.addTexture(NAMESPACE.id("block/icy_diamond_ore")).index
                icyLapisLazuliOre.textureId =
                    terrain.addTexture(NAMESPACE.id("block/icy_lapis_lazuli_ore")).index
                // not extremely keen on this but it means i only have to override a couple methods instead of c+p the whole
                // redstone ore class
                redstoneOre = terrain.addTexture(NAMESPACE.id("block/icy_redstone_ore")).index
                icyRedstoneOre.textureId = redstoneOre
                litIcyRedstoneOre.textureId = redstoneOre
            }
        }
    }

    @EventListener
    fun registerRecipes(event: RecipeRegisterEvent) {
        if (!ConfigScreen.config.icyStone) return
        if (!isEventTypeShapeless(event.recipeId)) return

        // has to be in listener body so the blocks are initialized when called to be part of the map
        val smeltRecipes = mapOf(
            BetaMod.icyDiamondOre to Item.DIAMOND,
            BetaMod.icyIronOre to Item.IRON_INGOT,
            BetaMod.icyGoldOre to Item.GOLD_INGOT
        )

        addShapedRecipe {
            output(Block.FURNACE)

            top(BetaMod.icyCobblestone, BetaMod.icyCobblestone, BetaMod.icyCobblestone)
            middle(BetaMod.icyCobblestone, null, BetaMod.icyCobblestone)
            bottom(BetaMod.icyCobblestone, BetaMod.icyCobblestone, BetaMod.icyCobblestone)
        }

        addShapedRecipe {
            output(Item.STONE_PICKAXE)

            top(BetaMod.icyCobblestone, BetaMod.icyCobblestone, BetaMod.icyCobblestone)

            applySticks()
        }

        addShapedRecipe {
            output(Item.STONE_AXE)

            top(BetaMod.icyCobblestone, BetaMod.icyCobblestone, null)

            applySticks(BetaMod.icyCobblestone.asItem())
        }

        addShapedRecipe {
            output(Item.STONE_SHOVEL)

            top(null, BetaMod.icyCobblestone, null)

            applySticks()
        }


        addShapedRecipe {
            output(Item.STONE_SWORD)

            top(null, BetaMod.icyCobblestone, null)

            applySticks(secondItem = BetaMod.icyCobblestone.asItem())
        }


        addShapedRecipe {
            output(Item.STONE_HOE)

            top(BetaMod.icyCobblestone, BetaMod.icyCobblestone, null)

            applySticks()
        }

        addShapedRecipe {
            output(Item.REPEATER)

            top(Block.LIT_REDSTONE_TORCH.asItem(), Item.REDSTONE, Block.LIT_REDSTONE_TORCH.asItem())
            middle(BetaMod.icyStone, BetaMod.icyStone, BetaMod.icyStone)
        }

        addSmeltingRecipe {
            input(BetaMod.icyCobblestone)
            output(BetaMod.icyStone)
        }

        for ((input, output) in smeltRecipes) {
            addSmeltingRecipe {
                input(input)
                output(output)
            }
        }
    }

    private fun ShapedRecipeBuilder.applySticks(firstItem: Item? = null, secondItem: Item? = Item.STICK) = apply {
        middle(firstItem, secondItem, null)
        bottom(null, Item.STICK, null)
    }
}