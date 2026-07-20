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
}