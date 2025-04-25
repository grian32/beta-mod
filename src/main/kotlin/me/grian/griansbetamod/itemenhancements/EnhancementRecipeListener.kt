package me.grian.griansbetamod.itemenhancements

import me.grian.griansbetamod.api.craftingrecipes.addEnhancementRecipe
import net.mine_diver.unsafeevents.listener.EventListener
import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.modificationstation.stationapi.api.event.mod.InitEvent

object EnhancementRecipeListener {
    @EventListener
    fun init(event: InitEvent) {
        addEnhancementRecipe {
            toolType = ToolType.AXE
            ingredients = ItemStack(Block.LOG, 48, 0)
            enhancement = Enhancement.EXTRA_LOGS
        }
    }
}