package me.grian.griansbetamod.itemenhancements.sifter

import me.grian.griansbetamod.BetaMod
import me.grian.griansbetamod.itemenhancements.Enhancement
import me.grian.griansbetamod.itemenhancements.getEnhancement
import net.minecraft.block.material.Material
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.stat.Stats
import net.minecraft.world.World
import net.modificationstation.stationapi.api.template.block.TemplateBlock
import net.modificationstation.stationapi.api.util.Identifier
import java.util.Random

class ScorchedClayBlock(identifier: Identifier): TemplateBlock(identifier, Material.CLAY) {
    override fun getDroppedItemId(blockMeta: Int, random: Random?): Int {
        return BetaMod.scorchedClayBall.id
    }

    override fun getDroppedItemCount(random: Random?): Int {
        return 4
    }

    override fun afterBreak(world: World, playerEntity: PlayerEntity, x: Int, y: Int, z: Int, meta: Int) {
        val selectedSlot = playerEntity.inventory.selectedItem

        if (
            selectedSlot != null &&
            selectedSlot.getEnhancement() == Enhancement.LANDSCAPER
        ) {
            this.dropStack(world, x, y, z, ItemStack(BetaMod.scorchedClayBlock, 1));
            playerEntity.increaseStat(Stats.MINE_BLOCK[this.id], 1);
        } else {
            super.afterBreak(world, playerEntity, x, y, z, meta)
        }

    }
}