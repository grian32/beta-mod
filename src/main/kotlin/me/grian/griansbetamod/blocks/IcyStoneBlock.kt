package me.grian.griansbetamod.blocks

import me.grian.griansbetamod.BetaMod
import net.minecraft.block.material.Material
import net.modificationstation.stationapi.api.template.block.TemplateBlock
import net.modificationstation.stationapi.api.util.Identifier
import java.util.*

class IcyStoneBlock(identifier: Identifier) : TemplateBlock(identifier, Material.STONE) {
    override fun getDroppedItemId(blockMeta: Int, random: Random?): Int {
        return BetaMod.icyCobblestone.id
    }
}