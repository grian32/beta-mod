package me.grian.griansbetamod

import me.grian.griansbetamod.api.craftingrecipes.addShapedRecipe
import me.grian.griansbetamod.api.craftingrecipes.addShapelessRecipe
import me.grian.griansbetamod.api.craftingrecipes.addSmeltingRecipe
import me.grian.griansbetamod.blocks.*
import me.grian.griansbetamod.config.ConfigScreen
import me.grian.griansbetamod.itemenhancements.EnhancementTableBlock
import me.grian.griansbetamod.items.GrassyBootsItem
import me.grian.griansbetamod.items.ResinItem
import me.grian.griansbetamod.items.SpeedCrystalItem
import me.grian.griansbetamod.items.TotemOfHealthItem
import me.grian.griansbetamod.lilyofthelake.LilyOfTheLakeBlock
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

    lateinit var netherGlass: Block

    lateinit var enhancementTable: Block
    lateinit var pileOfLogs: Block
    lateinit var resin: Item
    lateinit var resinBlock: Block
    lateinit var totemOfHealth: Item

    lateinit var grassyBoots: Item

    lateinit var speedCrystal: Item

    lateinit var lilyOfTheLake: Block

    @EventListener
    fun registerBlocks(event: BlockRegistryEvent) {
        if (ConfigScreen.config.enableRedstoneBlock) {
            redstoneBlock = RedstoneBlock(NAMESPACE.id("redstone_block"))
                .setTranslationKey(NAMESPACE, "redstone_block")
                .setSoundGroup(Block.METAL_SOUND_GROUP)
                .setResistance(6.0f)
                .setHardness(5.0f)
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

        if (ConfigScreen.config.enhancementSystem) {
            enhancementTable = EnhancementTableBlock(NAMESPACE.id("enhancement_table"))
                .setTranslationKey(NAMESPACE, "enhancement_table")
                .setSoundGroup(Block.STONE_SOUND_GROUP)
                .setHardness(5.0f)

            pileOfLogs = PileOfLogsBlock(NAMESPACE.id("pile_of_logs"))
                .setTranslationKey(NAMESPACE, "pile_of_logs")
                .setSoundGroup(Block.WOOD_SOUND_GROUP)
                .setHardness(2.0f)

            resinBlock = ResinBlock(NAMESPACE.id("resin_block"))
                .setTranslationKey(NAMESPACE, "resin_block")
                .setSoundGroup(Block.DIRT_SOUND_GROUP)
                .setHardness(2.0f)
        }

        if (ConfigScreen.config.lilyOfTheLake) {
            lilyOfTheLake = LilyOfTheLakeBlock(NAMESPACE.id("lily_of_the_lake"))
                .setTranslationKey(NAMESPACE, "lily_of_the_lake")
                .setHardness(0.0F)
                .setSoundGroup(Block.DIRT_SOUND_GROUP)
                .setLuminance(0.695f)
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

        if (ConfigScreen.config.enhancementSystem) {
            resin = ResinItem(NAMESPACE.id("resin"))
                .setTranslationKey(NAMESPACE, "resin")

            totemOfHealth = TotemOfHealthItem(NAMESPACE.id("totem_of_health"))
                .setTranslationKey(NAMESPACE, "totem_of_health")
                .setMaxCount(1)
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

            if (ConfigScreen.config.enhancementSystem) {
                // TODO: maybe move this to sep file, not 100% sure
                addShapedRecipe {
                    output(pileOfLogs)

                    val logStack = ItemStack(Block.LOG, 4, 0)

                    top(logStack, logStack, null)
                    middle(logStack, logStack, null)
                }

                addShapedRecipe {
                    output(enhancementTable)

                    val lightBlueDye = ItemStack(Item.DYE, 1, 12)

                    top(lightBlueDye, ItemStack(Item.DIAMOND), lightBlueDye)
                    middle(Block.STONE.asItemStack(), lightBlueDye, Block.STONE.asItemStack())
                    bottom(Block.STONE, null, Block.STONE)
                }

                addShapedRecipe {
                    output(resinBlock)

                    top(resin, resin, null)
                    middle(resin, resin, null)
                }

                addShapedRecipe {
                    output(Block.STICKY_PISTON)

                    top(null, resin, null)
                    middle(null, Block.PISTON, null)
                }

                addShapedRecipe {
                    output(totemOfHealth)

                    top(Block.DIAMOND_BLOCK.asItem(), resin, Block.DIAMOND_BLOCK.asItem())
                    middle(resin, resin, resin)
                    bottom(null, resin, null)
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