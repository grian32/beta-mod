package me.grian.griansbetamod.mixinutils

import me.grian.griansbetamod.itemenhancements.Enhancement
import me.grian.griansbetamod.itemenhancements.getEnhancement
import me.grian.griansbetamod.itemenhancements.getEnhancementTier
import me.grian.griansbetamod.util.outOf
import net.minecraft.item.AxeItem
import net.minecraft.item.ItemStack
import java.util.*

fun getExtraLogs(tier: Int, random: Random): Int =
    when (tier) {
        1 -> (20.outOf(100, random)).toInt()
        2 -> (45.outOf(100, random)).toInt()
        3 -> (75.outOf(100, random)).toInt()
        4 -> 1
        else -> 0
    }

private fun Boolean.toInt() = if (this) 1 else 0

fun resinDropped(tier: Int, random: Random): Boolean =
    when (tier) {
        1 -> 20.outOf(100, random)
        2 -> 35.outOf(100, random)
        3 -> 60.outOf(100, random)
        else -> false
    }

fun shouldSaveDurability(stack: ItemStack?, random: Random): Boolean {
    if (stack == null || stack.item !is AxeItem || stack.getEnhancement() != Enhancement.AXE_UNBREAKING) return false
    val tier = stack.getEnhancementTier()

    return when (tier) {
        1 -> 10.outOf(100, random)
        2 -> 20.outOf(100, random)
        3 -> 30.outOf(100, random)
        4 -> 40.outOf(100, random)
        else -> false
    }
}
