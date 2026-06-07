package me.grian.griansbetamod.itemenhancements

import me.grian.griansbetamod.itemenhancements.Enhancement.entries
import net.minecraft.item.ItemStack

fun ItemStack.getEnhancement(): Enhancement =
    if (stationNbt.contains("enhancement"))  {
        Enhancement.getFromId(stationNbt.getInt("enhancement"))!!
    } else {
        Enhancement.NONE
    }

fun ItemStack.setEnhancement(enhancement: Enhancement): ItemStack {
    stationNbt.putInt("enhancement", enhancement.id)
    return this
}

fun ItemStack.getEnhancementTier(): Int = stationNbt.getInt("enhancementTier")

fun ItemStack.setEnhancementTier(value: Int): ItemStack {
    stationNbt.putInt("enhancementTier", value)
    return this
}