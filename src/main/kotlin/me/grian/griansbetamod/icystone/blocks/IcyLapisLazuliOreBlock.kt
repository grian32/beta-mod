package me.grian.griansbetamod.icystone.blocks

import net.minecraft.block.material.Material
import net.minecraft.item.Item
import net.modificationstation.stationapi.api.template.block.TemplateBlock
import net.modificationstation.stationapi.api.util.Identifier
import java.util.Random

class IcyLapisLazuliOreBlock(identifier: Identifier) : TemplateBlock(identifier, Material.STONE) {
    override fun getDroppedItemId(blockMeta: Int, random: Random?) = Item.DYE.id

    override fun getDroppedItemMeta(blockMeta: Int) = LAPIS_META

    override fun getDroppedItemCount(random: Random?) = 4 + random!!.nextInt(5)

    companion object {
        const val LAPIS_META = 4
    }
}