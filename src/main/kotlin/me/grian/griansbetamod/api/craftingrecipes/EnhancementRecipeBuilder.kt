package me.grian.griansbetamod.api.craftingrecipes

import me.grian.griansbetamod.itemenhancements.Enhancement
import me.grian.griansbetamod.itemenhancements.recipe.EnhancementRecipeManager
import me.grian.griansbetamod.itemenhancements.recipe.EnhancementRecipe
import me.grian.griansbetamod.itemenhancements.ToolType
import net.minecraft.item.ItemStack

class EnhancementRecipeBuilder {
    var toolType: ToolType? = null
    var ingredients: ItemStack? = null
    var enhancement: Enhancement? = null

    fun registerRecipe() {
        if (toolType == null || ingredients == null || enhancement == null) return

        EnhancementRecipeManager.recipes.add(
            EnhancementRecipe(
                toolType!!,
                ingredients!!,
                enhancement!!
            )
        )
    }
}