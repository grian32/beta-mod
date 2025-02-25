package me.grian.griansbetamod

import me.grian.griansbetamod.api.craftingrecipes.ShapelessRecipeBuilder
import me.grian.griansbetamod.api.craftingrecipes.addShapedRecipe
import me.grian.griansbetamod.api.craftingrecipes.addShapelessRecipe
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
import net.modificationstation.stationapi.api.recipe.CraftingRegistry
import net.modificationstation.stationapi.api.util.Namespace
import org.apache.logging.log4j.Logger

object BetaMod {
    val NAMESPACE: Namespace = Namespace.of("griansbetamod")

    private val LOGGER: Logger = NAMESPACE.logger

    @JvmStatic
    lateinit var redstoneBlock: Block

    @JvmStatic
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
    }

    @EventListener
    fun registerItems(event: ItemRegistryEvent) {
        if (ConfigScreen.config.leatherBootsTrampleCrops) {
            grassyBoots = GrassyBoots(NAMESPACE.id("grassy_boots"))
                .setTranslationKey(NAMESPACE, "grassy_boots")
                .setHandheld()
                .setTexturePosition(0, 3)
        }
    }

    @EventListener
    fun registerRecipes(event: RecipeRegisterEvent) {
        val type = RecipeRegisterEvent.Vanilla.fromType(event.recipeId)

        if (ConfigScreen.config.enableRedstoneBlock) {
            addShapedRecipe {
                output = ItemStack(redstoneBlock)

                top("rrr")
                middle("rrr")
                bottom("rrr")

                key('r', ItemStack(Item.REDSTONE))
            }

            addShapelessRecipe {
                output = ItemStack(Item.REDSTONE, 9)

                ingredient(ItemStack(redstoneBlock))
            }
        }

        if (ConfigScreen.config.leatherBootsTrampleCrops) {
            addShapedRecipe {
                output = ItemStack(grassyBoots)

                top(" w ")
                middle("sbs")
                bottom(" w ")

                key('w', ItemStack(Item.WHEAT))
                key('s', ItemStack(Item.SEEDS))
                key('b', ItemStack(Item.LEATHER_BOOTS))
            }
        }
    }
}