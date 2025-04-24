package me.grian.griansbetamod.itemenhancements

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandler
import net.minecraft.world.World

class EnhancementTableScreenHandler(inventory: PlayerInventory, world: World, x: Int, y: Int, z: Int) : ScreenHandler() {
    override fun canUse(player: PlayerEntity?): Boolean {
        return true
    }
}