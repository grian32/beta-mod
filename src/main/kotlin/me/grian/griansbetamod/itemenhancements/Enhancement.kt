package me.grian.griansbetamod.itemenhancements

import net.modificationstation.stationapi.api.util.Formatting

enum class Enhancement(
    val guiName: String,
    val color: Formatting,
    val id: Int,
    val hasTiers: Boolean = true
) {
    NONE(guiName = "", color = Formatting.WHITE, id = -1),
    EXTRA_LOGS(guiName = "Extra Logs", color = Formatting.GOLD, id = 0);

    companion object {
        fun getFromId(id: Int) = entries.find { it.id == id }
    }
}