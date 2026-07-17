package me.grian.griansbetamod.api.tooltip

import net.modificationstation.stationapi.api.util.Formatting

class TooltipBuilder {
    private var out: String = ""

    fun black(str: String) {
        out += Formatting.BLACK.toString() + str + " "
    }

    fun darkBlue(str: String) {
        out += Formatting.DARK_BLUE.toString() + str + " "
    }

    fun darkGreen(str: String) {
        out += Formatting.DARK_GREEN.toString() + str + " "
    }

    fun darkAqua(str: String) {
        out += Formatting.DARK_AQUA.toString() + str + " "
    }

    fun darkRed(str: String) {
        out += Formatting.DARK_RED.toString() + str + " "
    }

    fun darkPurple(str: String) {
        out += Formatting.DARK_PURPLE.toString() + str + " "
    }

    fun gold(str: String) {
        out += Formatting.GOLD.toString() + str + " "
    }

    fun gray(str: String) {
        out += Formatting.GRAY.toString() + str + " "
    }

    fun aqua(str: String) {
        out += Formatting.AQUA.toString() + str + " "
    }

    fun darkGray(str: String) {
        out += Formatting.DARK_GRAY.toString() + str + " "
    }

    fun blue(str: String) {
        out += Formatting.BLUE.toString() + str + " "
    }

    fun green(str: String) {
        out += Formatting.GREEN.toString() + str + " "
    }

    fun red(str: String) {
        out += Formatting.RED.toString() + str + " "
    }

    fun lightPurple(str: String) {
        out += Formatting.LIGHT_PURPLE.toString() + str + " "
    }

    fun yellow(str: String) {
        out += Formatting.YELLOW.toString() + str + " "
    }

    fun white(str: String) {
        out += Formatting.WHITE.toString() + str + " "
    }


    override fun toString(): String {
        return out
    }
}
