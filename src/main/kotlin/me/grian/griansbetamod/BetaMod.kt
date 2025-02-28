package me.grian.griansbetamod

import me.grian.griansbetamod.api.craftingrecipes.addShapedRecipe
import me.grian.griansbetamod.api.craftingrecipes.addShapelessRecipe
import me.grian.griansbetamod.blocks.IcyStoneBlock
import me.grian.griansbetamod.blocks.RedstoneBlock
import me.grian.griansbetamod.config.ConfigScreen
import me.grian.griansbetamod.items.GrassyBoots
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

    lateinit var grassyBoots: Item


    @EventListener
    fun registerBlocks(event: BlockRegistryEvent) {
        if (ConfigScreen.config.enableRedstoneBlock) {
            redstoneBlock = RedstoneBlock(NAMESPACE.id("redstone_block"))
                .setTranslationKey(NAMESPACE, "redstone_block")
                .setSoundGroup(Block.METAL_SOUND_GROUP)
                .setResistance(6.0f)
                .setHardness(5.0f)
        }

        if (ConfigScreen.config.icyStone) {
            icyStone = IcyStoneBlock(NAMESPACE.id("icy_stone"))
                .setTranslationKey(NAMESPACE, "icy_stone")
                .setSoundGroup(Block.STONE_SOUND_GROUP)
                .setHardness(1.5F)
                .setResistance(10.0F)
        }
    }

    @EventListener
    fun registerItems(event: ItemRegistryEvent) {
        if (ConfigScreen.config.leatherBootsTrampleCrops) {
            grassyBoots = GrassyBoots(NAMESPACE.id("grassy_boots"))
                .setTranslationKey(NAMESPACE, "grassy_boots")
                .setTexturePosition(0, 3)
        }
    }

    @EventListener
    fun registerRecipes(event: RecipeRegisterEvent) {
        val type = RecipeRegisterEvent.Vanilla.fromType(event.recipeId)

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

        if (ConfigScreen.config.decraftSaddles) {
            addShapelessRecipe {
                output(ItemStack(Item.LEATHER, 5))

                ingredient(Item.SADDLE)
            }
        }
    }
}