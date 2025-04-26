package me.grian.griansbetamod.itemenhancements

enum class Enhancement(
    val guiName: String,
    val id: Int
) {
    NONE(guiName = "", id = -1),
    EXTRA_LOGS(guiName = "Extra Logs", id = 0);

    companion object {
        fun getFromId(id: Int) = entries.find { it.id == id }
    }
}