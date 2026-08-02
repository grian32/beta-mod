package me.grian.griansbetamod.compat.itemenhancements

import me.grian.griansbetamod.itemenhancements.recipe.EnhancementRecipe
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeHandler
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper

class EnhancementRecipeHandler : RecipeHandler<EnhancementRecipe> {
    override fun getRecipeClass(): Class<EnhancementRecipe> {
        return EnhancementRecipe::class.java
    }

    override fun getRecipeCategoryUid(): String {
        return EnhancementRecipeCategory.UID
    }

    override fun getRecipeWrapper(recipe: EnhancementRecipe): RecipeWrapper {
        return EnhancementRecipeWrapper(recipe)
    }

    override fun isRecipeValid(recipe: EnhancementRecipe): Boolean {
        return recipe.ingredients.count > 0
    }
}