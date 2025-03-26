package me.grian.griansbetamod.blocks

import net.minecraft.block.material.Material
import net.modificationstation.stationapi.api.template.block.TemplateTranslucentBlock
import net.modificationstation.stationapi.api.util.Identifier
import java.util.Random

class NetherGlassBlock(identifier: Identifier) : TemplateTranslucentBlock(identifier, 0,Material.GLASS, false) {
    override fun getRenderLayer(): Int {
        return 0
    }

    override fun getDroppedItemId(blockMeta: Int, random: Random?): Int {
        return 0
    }

    override fun getDroppedItemCount(random: Random?): Int {
        return 0
    }
}