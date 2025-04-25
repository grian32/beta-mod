package me.grian.griansbetamod.itemenhancements.screen

import me.grian.griansbetamod.itemenhancements.recipe.EnhancementRecipeManager
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.CraftingInventory
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.slot.Slot

class EnhancementResultSlot(
    private val player: PlayerEntity,
    private val input: CraftingInventory,
    inventory: Inventory?,
    index: Int,
    x: Int,
    y: Int
) : Slot(inventory, index, x, y) {
    override fun canInsert(stack: ItemStack): Boolean {
        return false
    }

    override fun onTakeItem(stack: ItemStack) {
        val recipe = EnhancementRecipeManager.findRecipe(input) ?: return

        input.removeStack(0, 1)
        input.removeStack(1, recipe.ingredients.count)
    }
}