package me.grian.griansbetamod.compat.itemenhancements

import me.grian.griansbetamod.BetaMod
import me.grian.griansbetamod.itemenhancements.Enhancement
import me.grian.griansbetamod.itemenhancements.setEnhancement
import me.grian.griansbetamod.itemenhancements.setEnhancementTier
import net.glasslauncher.mods.alwaysmoreitems.api.SubItemProvider
import net.minecraft.item.ItemStack
import net.modificationstation.stationapi.api.template.item.TemplateItem
import net.modificationstation.stationapi.api.util.Identifier

class EnhancementAMIItem(
    identifier: Identifier,
    translationKey: String,
    private val tierCount: Int,
    private val enhancement: Enhancement
) : TemplateItem(identifier) {
    init {
        setHasSubtypes(true)
        setTranslationKey(BetaMod.NAMESPACE, translationKey)
    }

    @SubItemProvider
    fun amiSubItems(): List<ItemStack> =
        (1..tierCount).map(::stackForTier)

    fun stackForTier(tier: Int): ItemStack =
        ItemStack(this, 1, tier)

    override fun getTranslationKey(stack: ItemStack): String =
        "${super.getTranslationKey(stack)}.${stack.damage}"
}
