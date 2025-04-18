package me.grian.griansbetamod

import me.grian.griansbetamod.api.craftingrecipes.addShapedRecipe
import me.grian.griansbetamod.api.craftingrecipes.addShapelessRecipe
import me.grian.griansbetamod.api.craftingrecipes.addSmeltingRecipe
import me.grian.griansbetamod.blocks.NetherGlassBlock
import me.grian.griansbetamod.blocks.RedstoneBlock
import me.grian.griansbetamod.blocks.SawmillBlock
import me.grian.griansbetamod.blocks.icystone.*
import me.grian.griansbetamod.config.ConfigScreen
import me.grian.griansbetamod.items.GrassyBootsItem
import me.grian.griansbetamod.items.SpeedCrystalItem
import me.grian.griansbetamod.util.asItemStack
import me.grian.griansbetamod.util.isEventTypeShaped
import me.grian.griansbetamod.util.isEventTypeShapeless
import me.grian.griansbetamod.util.isEventTypeSmelting
import net.mine_diver.unsafeevents.listener.EventListener
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent
import net.modificationstation.stationapi.api.util.Namespace
import org.apache.logging.log4j.Logger

object BetaMod {
    val NAMESPACE: Namespace = Namespace.of("griansbetamod")

    private val LOGGER: Logger = NAMESPACE.logger

    lateinit var redstoneBlock: Block

    lateinit var icyStone: Block
    lateinit var icyCobblestone: Block
    lateinit var icyCoalOre: Block
    lateinit var icyIronOre: Block
    lateinit var icyGoldOre: Block
    lateinit var icyDiamondOre: Block
    lateinit var icyLapisLazuliOre: Block
    lateinit var icyRedstoneOre: Block
    // TODO: mark this so it doesnt show up in ami
    lateinit var litIcyRedstoneOre: Block

    lateinit var sawmill: Block

    lateinit var grassyBoots: Item

    lateinit var netherGlass: Block

    lateinit var speedCrystal: Item

    @EventListener
    fun registerBlocks(event: BlockRegistryEvent) {
        if (ConfigScreen.config.enableRedstoneBlock) {
            redstoneBlock = RedstoneBlock(NAMESPACE.id("redstone_block"))
                .setTranslationKey(NAMESPACE, "redstone_block")
                .setSoundGroup(Block.METAL_SOUND_GROUP)
                .setResistance(6.0f)
                .setHardness(5.0f)
        }

        // TODO: refactor this to IcyStoneListener, along with the recipes/texture as its going to get bulky
        if (ConfigScreen.config.icyStone) {
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

        if (ConfigScreen.config.sawmillBlock) {
            sawmill = SawmillBlock(NAMESPACE.id("sawmill"))
                .setTranslationKey(NAMESPACE, "sawmill")
                .setSoundGroup(Block.WOOD_SOUND_GROUP)
                .setHardness(3.5f)
        }

        if (ConfigScreen.config.netherGlass) {
            netherGlass = NetherGlassBlock(NAMESPACE.id("nether_glass"))
                .setTranslationKey(NAMESPACE, "nether_glass")
                .setSoundGroup(Block.GLASS_SOUND_GROUP)
                .setHardness(0.3f)
        }
    }

    @EventListener
    fun registerItems(event: ItemRegistryEvent) {
        if (ConfigScreen.config.leatherBootsTrampleCrops) {
            grassyBoots = GrassyBootsItem(NAMESPACE.id("grassy_boots"))
                .setTranslationKey(NAMESPACE, "grassy_boots")
                .setTexturePosition(0, 3)
        }

        if (ConfigScreen.config.lapisSpeedBoost) {
            speedCrystal = SpeedCrystalItem(NAMESPACE.id("speed_crystal"))
                .setTranslationKey(NAMESPACE, "speed_crystal")
        }
    }

    @EventListener
    fun registerRecipes(event: RecipeRegisterEvent) {
        val type = RecipeRegisterEvent.Vanilla.fromType(event.recipeId)

        if (isEventTypeShaped(event.recipeId)) {
            if (ConfigScreen.config.enableRedstoneBlock) {
                addShapedRecipe {
                    output(redstoneBlock.asItem())

                    top(Item.REDSTONE, Item.REDSTONE, Item.REDSTONE)
                    middle(Item.REDSTONE, Item.REDSTONE, Item.REDSTONE)
                    bottom(Item.REDSTONE, Item.REDSTONE, Item.REDSTONE)
                }

                addShapelessRecipe {
                    output(ItemStack(Item.REDSTONE, 9))

                    ingredient(redstoneBlock.asItem())
                }
            }

            if (ConfigScreen.config.leatherBootsTrampleCrops) {
                addShapedRecipe {
                    output(grassyBoots)

                    top(null, Item.WHEAT, null)
                    middle(Item.SEEDS, Item.LEATHER_BOOTS, Item.SEEDS)
                    bottom(null, Item.WHEAT, null)
                }
            }

            if (ConfigScreen.config.sawmillBlock) {
                addShapedRecipe {
                    output(sawmill)

                    top(null, Item.IRON_INGOT, null)
                    middle(Block.PLANKS.asItem(), Item.IRON_INGOT, Block.PLANKS.asItem())
                    bottom(Block.STONE.asItem(), Item.IRON_INGOT, Block.STONE.asItem() )
                }
            }

            if (ConfigScreen.config.lapisSpeedBoost) {
                addShapedRecipe {
                    output(speedCrystal)

                    top(null, Block.COBBLESTONE, null)
                    // dmg 4 = lapis
                    middle(Block.COBBLESTONE.asItemStack(), ItemStack(Item.DYE, 1, 4), Block.COBBLESTONE.asItemStack())
                    bottom(null, Block.COBBLESTONE, null)
                }
            }
        }

        if (ConfigScreen.config.decraftSaddles && isEventTypeShapeless(event.recipeId)) {
            addShapelessRecipe {
                output(ItemStack(Item.LEATHER, 5))

                ingredient(Item.SADDLE)
            }
        }

        if (isEventTypeSmelting(event.recipeId)) {
            if (ConfigScreen.config.netherGlass) {
                addSmeltingRecipe {
                    input(Block.SOUL_SAND)
                    output(netherGlass)
                }
            }
        }
    }
}