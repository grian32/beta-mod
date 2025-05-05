package me.grian.griansbetamod.mixinutils

import me.grian.griansbetamod.itemenhancements.Enhancement
import me.grian.griansbetamod.itemenhancements.getEnhancement
import me.grian.griansbetamod.itemenhancements.getEnhancementTier
import me.grian.griansbetamod.util.outOf
import net.minecraft.item.AxeItem
import net.minecraft.item.ItemStack
import java.util.*

// uses smallest possible fractions to save on memory for range to check against.
fun getExtraLogs(tier: Int, random: Random): Int =
    when (tier) {
        1 -> (1.outOf(5, random)).toInt() // 20%
        2 -> (9.outOf(20, random)).toInt() // 45%
        3 -> (3.outOf(4, random)).toInt() // 75%
        4 -> 1
        else -> 0
    }

private fun Boolean.toInt() = if (this) 1 else 0

fun resinDropped(tier: Int, random: Random): Boolean =
    when (tier) {
        1 -> 1.outOf(5, random) // 20%
        2 -> 21.outOf(60, random) // 35%
        3 -> 3.outOf(5, random) // 60%
        else -> false
    }

fun shouldSaveDurability(stack: ItemStack?, random: Random): Boolean {
    if (stack == null || stack.item !is AxeItem || stack.getEnhancement() != Enhancement.AXE_UNBREAKING) return false
    val tier = stack.getEnhancementTier()

    return when (tier) {
        1 -> 1.outOf(10, random)
        2 -> 2.outOf(10, random)
        3 -> 3.outOf(10, random)
        4 -> 4.outOf(10, random)
        else -> false
    }
}

fun lapisDropped(tier: Int, random: Random): Boolean =
    when (tier) {
        1 -> 1.outOf(50, random) // 2%
        2 -> 3.outOf(100, random) // 3%
        3 -> 1.outOf(25, random) // 4%
        else -> false
    }

fun shouldSaveOre(tier: Int, random: Random): Boolean =
    when (tier) {
        1 -> 1.outOf(100, random) // 1%
        2 -> 3.outOf(100, random) // 3%
        3 -> 1.outOf(20, random) // 5%
        else -> false
    }