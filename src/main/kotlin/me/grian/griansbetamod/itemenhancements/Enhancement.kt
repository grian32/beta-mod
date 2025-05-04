package me.grian.griansbetamod.itemenhancements

import net.modificationstation.stationapi.api.util.Formatting

enum class Enhancement(
    val guiName: String,
    val color: Formatting,
    val id: Int,
    val hasTiers: Boolean = true
) {
    // axe
    NONE(guiName = "", color = Formatting.WHITE, id = -1),
    EXTRA_LOGS(guiName = "Extra Logs", color = Formatting.GOLD, id = 0),
    RESIN(guiName = "Resin Harvest", color = Formatting.YELLOW, id = 1),
    AXE_UNBREAKING(guiName = "Reinforced", color = Formatting.GRAY, id = 2),

    // pick
    LAPIS_MINER(guiName = "Lapis Miner", color = Formatting.BLUE, id = 3),
    STEADY_HAND(guiName = "Steady Hand", color = Formatting.AQUA, id = 4),

    // hoe
    REPLANTER(guiName = "Replanter", color = Formatting.GREEN, id = 5, false);

    companion object {
        fun getFromId(id: Int) = entries.find { it.id == id }
    }
}