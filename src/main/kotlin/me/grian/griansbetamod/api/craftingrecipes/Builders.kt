package me.grian.griansbetamod.api.craftingrecipes

inline fun addShapedRecipe(init: ShapedRecipeBuilder.() -> Unit): ShapedRecipeBuilder {
    val shapedRecipe = ShapedRecipeBuilder()
    shapedRecipe.init()
    shapedRecipe.registerRecipe()
    return shapedRecipe;
}