package me.grian.griansbetamod.mixinutils

import me.grian.griansbetamod.util.outOf
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