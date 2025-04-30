package me.grian.griansbetamod.itemenhancements

import net.modificationstation.stationapi.api.util.Formatting

enum class Enhancement(
    val guiName: String,
    val color: Formatting,
    val id: Int,
) {
    NONE(guiName = "", color = Formatting.WHITE, id = -1),
    EXTRA_LOGS(guiName = "Extra Logs", color = Formatting.GOLD, id = 0),
    ROTBANE(guiName = "Rotbane", color = Formatting.DARK_GREEN, id = 1);

    companion object {
        fun getFromId(id: Int) = entries.find { it.id == id }
    }
}