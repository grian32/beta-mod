package me.grian.griansbetamod.compat.itemenhancements

import net.glasslauncher.mods.alwaysmoreitems.api.gui.AMIDrawable
import net.glasslauncher.mods.alwaysmoreitems.api.gui.RecipeLayout
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeCategory
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper
import net.glasslauncher.mods.alwaysmoreitems.gui.DrawableHelper
import net.minecraft.client.Minecraft

class EnhancementRecipeCategory : RecipeCategory {
    private var bg: AMIDrawable = DrawableHelper.createDrawable("/assets/griansbetamod/stationapi/textures/gui/enhancement.png", 39, 30, 98, 26)

    constructor()


    override fun getUid(): String {
        return UID
    }

    override fun getTitle(): String {
        return "Enhancement Table"
    }

    override fun getBackground(): AMIDrawable {
        return bg
    }

    override fun drawExtras(minecraft: Minecraft?) {
    }

    override fun drawAnimations(minecraft: Minecraft?) {
    }

    override fun setRecipe(
        recipeLayout: RecipeLayout,
        recipeWrapper: RecipeWrapper
    ) {
        val stacks = recipeLayout.itemStacks

        stacks.init(0, true, 0, 4)
        stacks.init(1, true, 18, 4)
        stacks.init(2, false, 76, 4)

        stacks.setFromRecipe(0, recipeWrapper.inputs[0] as Any)
        stacks.setFromRecipe(1, recipeWrapper.inputs[1] as Any)
        stacks.setFromRecipe(2, recipeWrapper.outputs[0] as Any)
    }

    companion object {
        const val UID = "griansbetamod:enhancements"
    }
}
