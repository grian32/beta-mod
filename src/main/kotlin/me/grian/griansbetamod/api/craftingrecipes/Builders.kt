package me.grian.griansbetamod.api.craftingrecipes

inline fun addShapedRecipe(init: ShapedRecipeBuilder.() -> Unit): ShapedRecipeBuilder {
    val shapedRecipe = ShapedRecipeBuilder()
    shapedRecipe.init()
    shapedRecipe.registerRecipe()
    return shapedRecipe;
}

inline fun addShapelessRecipe(init: ShapelessRecipeBuilder.() -> Unit): ShapelessRecipeBuilder {
    val shapelessRecipe = ShapelessRecipeBuilder()
    shapelessRecipe.init()
    shapelessRecipe.registerRecipe()
    return shapelessRecipe;
}