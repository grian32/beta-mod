package me.grian.griansbetamod.compat.itemenhancements

import me.grian.griansbetamod.BetaMod
import me.grian.griansbetamod.itemenhancements.Enhancement
import me.grian.griansbetamod.itemenhancements.ToolType
import me.grian.griansbetamod.itemenhancements.recipe.EnhancementRecipe
import me.grian.griansbetamod.itemenhancements.recipe.EnhancementRecipeManager
import me.grian.griansbetamod.itemenhancements.setEnhancement
import me.grian.griansbetamod.itemenhancements.setEnhancementTier
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper
import net.glasslauncher.mods.alwaysmoreitems.recipe.Focus
import net.glasslauncher.mods.alwaysmoreitems.util.HoverChecker
import net.minecraft.client.Minecraft
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import java.util.ArrayList
import net.glasslauncher.mods.alwaysmoreitems.gui.screen.OverlayScreen as OverlayScreen1

class EnhancementRecipeWrapper(val recipe: EnhancementRecipe) : RecipeWrapper {
    private val hoverChecker = HoverChecker(4, 21, 76, 93)

    override fun getInputs(): List<*> {
        val tool = getTool(recipe.toolType)
        return if (recipe.enhancementTier == 1) {
            listOf(ItemStack(tool), recipe.ingredients)
        } else {
            listOf(
                enhancedTool(tool, recipe.enhancement, recipe.enhancementTier - 1),
                recipe.ingredients
            )
        }
    }

    override fun getOutputs(): List<*> {
        val tool = getTool(recipe.toolType)
        return listOf(
            enhancedTool(tool, recipe.enhancement, recipe.enhancementTier),
            fakeStack(recipe.enhancement, recipe.enhancementTier)
        )
    }


    override fun drawInfo(
        minecraft: Minecraft,
        recipeWidth: Int,
        recipeHeight: Int,
        mouseX: Int,
        mouseY: Int
    ) {
    }

    override fun drawAnimations(
        minecraft: Minecraft,
        recipeWidth: Int,
        recipeHeight: Int
    ) {}

    override fun getTooltip(mouseX: Int, mouseY: Int): ArrayList<in Any> = arrayListOf()

    override fun handleClick(
        minecraft: Minecraft,
        mouseX: Int,
        mouseY: Int,
        mouseButton: Int
    ): Boolean {
        if (hoverChecker.isOver(mouseX, mouseY) && mouseButton == 0) {
            val fake = fakeStack(recipe.enhancement, recipe.enhancementTier + 1)
            if (fake != null) {
                OverlayScreen1.INSTANCE.recipesGui.showRecipes(Focus(fake))
            }
            return true
        }
        return false
    }

    private fun getTool(toolType: ToolType): Item =
        when (toolType) {
            ToolType.SWORD -> Item.DIAMOND_SWORD
            ToolType.PICKAXE -> Item.DIAMOND_PICKAXE
            ToolType.AXE -> Item.DIAMOND_AXE
            ToolType.SHOVEL -> Item.DIAMOND_SHOVEL
            ToolType.HOE -> Item.DIAMOND_HOE
        }


    private fun enhancedTool(item: Item, enhancement: Enhancement, tier: Int) =
        ItemStack(item).apply {
            setEnhancement(enhancement)
            setEnhancementTier(tier)
        }

    private fun fakeStack(enhancement: Enhancement, tier: Int): ItemStack? {
        when (enhancement) {
            Enhancement.EXTRA_LOGS, Enhancement.AXE_UNBREAKING -> if (tier > 4) return null
            Enhancement.LANDSCAPER, Enhancement.REPLANTER -> if (tier > 1) return null
            else -> if (tier > 3) return null
        }

        return when (enhancement) {
            Enhancement.EXTRA_LOGS -> BetaMod.extraLogsAMIItem.stackForTier(tier)
            Enhancement.RESIN -> BetaMod.resinHarvestAMIItem.stackForTier(tier)
            Enhancement.AXE_UNBREAKING -> BetaMod.reinforcedAMIItem.stackForTier(tier)
            Enhancement.STEADY_HAND -> BetaMod.steadyHandAMIItem.stackForTier(tier)
            Enhancement.REPLANTER -> BetaMod.replanterAMIItem.stackForTier(tier)
            Enhancement.LANDSCAPER -> BetaMod.landscaperAMIItem.stackForTier(tier)
            Enhancement.BOUNTIFUL -> BetaMod.bountifulAMIItem.stackForTier(tier)
            Enhancement.SIFTER -> BetaMod.sifterAMIItem.stackForTier(tier)
            Enhancement.QUARRYMAN -> BetaMod.quarrymanAMIItem.stackForTier(tier)
            Enhancement.NONE, Enhancement.__LAPIS_MINER ->
                throw IllegalArgumentException("Enhancement $enhancement has no AMI display item")
        }
    }
}
