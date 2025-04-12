package me.grian.griansbetamod.blocks.icystone

import me.grian.griansbetamod.BetaMod
import net.minecraft.block.material.Material
import net.modificationstation.stationapi.api.template.block.TemplateBlock
import net.modificationstation.stationapi.api.util.Identifier
import java.util.Random

class IcyStoneBlock(
    identifier: Identifier,
) : TemplateBlock(identifier, Material.STONE) {
    override fun getDroppedItemId(
        blockMeta: Int,
        random: Random?,
    ): Int = BetaMod.icyCobblestone.id
}
