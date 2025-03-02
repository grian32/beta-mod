package me.grian.griansbetamod

import me.grian.griansbetamod.api.craftingrecipes.addShapedRecipe
import me.grian.griansbetamod.config.ConfigScreen
import net.mine_diver.unsafeevents.listener.EventListener
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent

object IcyStoneRecipesListener {
    @EventListener
    fun registerRecipes(event: RecipeRegisterEvent) {
        if (!ConfigScreen.config.icyStone) return

        addShapedRecipe {
            output(Block.FURNACE)

            top(BetaMod.icyCobblestone, BetaMod.icyCobblestone, BetaMod.icyCobblestone)
            middle(BetaMod.icyCobblestone, null, BetaMod.icyCobblestone)
            bottom(BetaMod.icyCobblestone, BetaMod.icyCobblestone, BetaMod.icyCobblestone)
        }
    }
}