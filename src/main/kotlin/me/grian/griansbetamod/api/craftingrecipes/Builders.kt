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

inline fun addSmeltingRecipe(init: SmeltingRecipeBuilder.() -> Unit): SmeltingRecipeBuilder {
    val smeltingRecipe = SmeltingRecipeBuilder()
    smeltingRecipe.init()
    smeltingRecipe.registerRecipe()
    return smeltingRecipe
}

inline fun addEnhancementRecipe(init: EnhancementRecipeBuilder.() -> Unit): EnhancementRecipeBuilder {
    val enhancementRecipe = EnhancementRecipeBuilder()
    enhancementRecipe.init()
    enhancementRecipe.registerRecipe()
    return enhancementRecipe
}