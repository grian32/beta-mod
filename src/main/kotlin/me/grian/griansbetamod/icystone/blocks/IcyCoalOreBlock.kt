package me.grian.griansbetamod.icystone.blocks

import net.minecraft.block.material.Material
import net.minecraft.item.Item
import net.modificationstation.stationapi.api.template.block.TemplateBlock
import net.modificationstation.stationapi.api.util.Identifier
import java.util.Random

class IcyCoalOreBlock(
    identifier: Identifier,
) : TemplateBlock(identifier, Material.STONE) {
    override fun getDroppedItemId(blockMeta: Int, random: Random?) = Item.COAL.id

}
