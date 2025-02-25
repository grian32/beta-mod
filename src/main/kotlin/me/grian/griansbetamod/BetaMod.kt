package me.grian.griansbetamod

import me.grian.griansbetamod.blocks.RedstoneBlock
import net.mine_diver.unsafeevents.listener.EventListener
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent
import net.modificationstation.stationapi.api.recipe.CraftingRegistry
import net.modificationstation.stationapi.api.util.Namespace
import org.apache.logging.log4j.Logger

object BetaMod {
    val NAMESPACE: Namespace = Namespace.of("griansbetamod")

    private val LOGGER: Logger = NAMESPACE.logger

    @JvmStatic
    lateinit var redstoneBlock: Block

    @EventListener
    fun registerBlocks(event: BlockRegistryEvent) {
        redstoneBlock = RedstoneBlock(NAMESPACE.id("redstone_block"))
            .setTranslationKey(NAMESPACE, "redstone_block")
            .setSoundGroup(Block.METAL_SOUND_GROUP)
            .setResistance(6.0f)
            .setHardness(5.0f)
    }

    @EventListener
    fun registerRecipes(event: RecipeRegisterEvent) {
        val type = RecipeRegisterEvent.Vanilla.fromType(event.recipeId)

        CraftingRegistry.addShapedRecipe(
            ItemStack(redstoneBlock), // outpu
            "rrr", // pattern
            "rrr",
            "rrr",
            'r', // key
            ItemStack(Item.REDSTONE) // value
        )
    }
}