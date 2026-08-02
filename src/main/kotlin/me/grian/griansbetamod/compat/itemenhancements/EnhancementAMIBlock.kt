package me.grian.griansbetamod.compat.itemenhancements

import me.grian.griansbetamod.BetaMod
import me.grian.griansbetamod.itemenhancements.Enhancement
import me.grian.griansbetamod.itemenhancements.setEnhancement
import me.grian.griansbetamod.itemenhancements.setEnhancementTier
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.item.ItemStack
import net.modificationstation.stationapi.api.block.MetaNamedBlockItemProvider
import net.modificationstation.stationapi.api.template.block.TemplateBlock
import net.modificationstation.stationapi.api.template.item.ItemTemplate
import net.modificationstation.stationapi.api.template.item.TemplateBlockItem
import net.modificationstation.stationapi.api.util.Identifier

class EnhancementAMIBlock(
    identifier: Identifier,
    translationKey: String,
    private val tierCount: Int,
    private val enhancement: Enhancement,
) : TemplateBlock(identifier, Material.STONE), MetaNamedBlockItemProvider {
    init {
        setTranslationKey(BetaMod.NAMESPACE, "$translationKey.")
    }

    override fun getValidMetas(): IntArray =
        IntArray(tierCount) { index -> index + 1 }

    fun stackForTier(tier: Int): ItemStack =
        ItemStack(this, 1, tier)

    override fun getTexture(side: Int, meta: Int): Int {
        if (enhancement == Enhancement.EXTRA_LOGS) {
            return LOG.getTexture(side, 0)
        } else {
            // landscaper
            return SNOW_BLOCK.getTexture(side, 0)
        }
    }
}