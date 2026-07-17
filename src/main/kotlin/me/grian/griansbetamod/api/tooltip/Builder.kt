package me.grian.griansbetamod.api.tooltip

inline fun buildTooltip(init: TooltipBuilder.() -> Unit): String {
    val tooltip = TooltipBuilder()
    tooltip.init()
    return tooltip.toString()
}
