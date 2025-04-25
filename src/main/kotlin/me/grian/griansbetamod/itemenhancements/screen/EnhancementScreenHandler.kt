package me.grian.griansbetamod.itemenhancements.screen

import me.grian.griansbetamod.BetaMod
import me.grian.griansbetamod.itemenhancements.recipe.EnhancementRecipeManager
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.CraftingInventory
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.Slot
import net.minecraft.world.World

class EnhancementScreenHandler(
    inventory: PlayerInventory,
    val world: World,
    val x: Int,
    val y: Int,
    val z: Int
) : ScreenHandler() {
    private val input: CraftingInventory = CraftingInventory(this, 2, 1)
    private val result: Inventory = EnhancementResultInventory()

    init {
        // x & y coords are literally just texture coords, unlucky

        // result slot
        addSlot(EnhancementResultSlot(inventory.player, input, result, 0, 116, 35))

        // input slots
        addSlot(Slot(input, 0, 40, 35))
        addSlot(Slot(input, 1, 58, 35))


        // player inventory
        for (var8 in 0..2) {
            for (var10 in 0..8) {
                addSlot(Slot(inventory, var10 + var8 * 9 + 9, 8 + var10 * 18, 84 + var8 * 18))
            }
        }

        // hotbar
        for (var9 in 0..8) {
            addSlot(Slot(inventory, var9, 8 + var9 * 18, 142))
        }

        onSlotUpdate(input)
    }

    override fun onSlotUpdate(inventory: Inventory?) {
        result.setStack(0, EnhancementRecipeManager.craft(input))
    }

    override fun canUse(player: PlayerEntity?): Boolean {
        return world.getBlockId(x, y, z) == BetaMod.enhancementTable.id
    }

    override fun onClosed(player: PlayerEntity) {
        super.onClosed(player)
        if (!world.isRemote) {
            val first = input.getStack(0)
            val second = input.getStack(1)

            if (first != null) player.dropItem(first)
            if (second != null) player.dropItem(second)
        }
    }

    override fun quickMove(slot: Int): ItemStack? {
        var stack: ItemStack? = null
        val foundSlot = slots[slot] as Slot?

        if (foundSlot != null && foundSlot.hasStack()) {
            val slotStack = foundSlot.stack
            stack = slotStack.copy()

            // result > inventory
            if (slot == 0) {
                this.insertItem(stack, 10, 39, true)
            }

            if (slotStack.count == 0) {
                foundSlot.stack = null
            } else {
                foundSlot.markDirty()
            }

            if (slotStack.count == stack.count) {
                return null
            }

            foundSlot.onTakeItem(slotStack)
        }

        return stack
    }
}