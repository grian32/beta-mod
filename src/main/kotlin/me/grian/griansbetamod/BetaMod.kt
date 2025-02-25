package me.grian.griansbetamod

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
            CraftingRegistry.addShapedRecipe(
                ItemStack(redstoneBlock), // output
                "rrr", // pattern
                "rrr",
                "rrr",
                'r', // key
                ItemStack(Item.REDSTONE) // value
            )

            CraftingRegistry.addShapelessRecipe(
                ItemStack(Item.REDSTONE, 9),
                redstoneBlock
            )
        }

        if (ConfigScreen.config.leatherBootsTrampleCrops) {
            CraftingRegistry.addShapedRecipe(
                ItemStack(grassyBoots),
                " w ",
                "sbs",
                " w ",
                'w',
                ItemStack(Item.WHEAT),
                'b',
                ItemStack(Item.LEATHER_BOOTS),
                's',
                ItemStack(Item.SEEDS)
            )
        }
    }
}