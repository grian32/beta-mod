package me.grian.griansbetamod.itemenhancements

import net.minecraft.achievement.Achievements
import net.minecraft.block.Block
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.screen.slot.Slot

class EnhancementTableResultSlot(
    private val player: PlayerEntity,
    private val input: Inventory,
    inventory: Inventory?,
    index: Int,
    x: Int,
    y: Int
) : Slot(inventory, index, x, y) {
    override fun canInsert(stack: ItemStack): Boolean {
        return false
    }

    override fun onTakeItem(stack: ItemStack) {
        stack.onCraft(player.world, this.player)

        for (var2 in 0..<input.size()) {
            val var3 = input.getStack(var2)
            if (var3 != null) {
                input.removeStack(var2, 1)
                if (var3.item.hasCraftingReturnItem()) {
                    input.setStack(var2, ItemStack(var3.item.craftingReturnItem))
                }
            }
        }
    }
}