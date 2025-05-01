package me.grian.griansbetamod.itemenhancements

import me.grian.griansbetamod.itemenhancements.Enhancement.entries
import net.minecraft.item.ItemStack

fun ItemStack.getEnhancement(): Enhancement =
    entries[stationNbt.getInt("enhancement")]

fun ItemStack.setEnhancement(enhancement: Enhancement): ItemStack {
    stationNbt.putInt("enhancement", enhancement.ordinal)
    return this
}

fun ItemStack.getEnhancementTier(): Int = stationNbt.getInt("enhancementTier")

fun ItemStack.setEnhancementTier(value: Int): ItemStack {
    stationNbt.putInt("enhancementTier", value)
    return this
}