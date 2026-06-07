package me.grian.griansbetamod

import BetaModSchema
import com.mojang.datafixers.DataFixer
import com.mojang.datafixers.DataFixerBuilder
import me.grian.griansbetamod.itemenhancements.OrdinalToIdFix
import me.grian.griansbetamod.util.SetDefaultBlockStateFix

internal const val CURRENT_DF_VERSION = 2

internal fun createBetaModDataFixer(): DataFixer {
    val placedBlocks = setOf(
        "minecraft:stone",
        "minecraft:log",
        "minecraft:coal_ore",
        "minecraft:iron_ore",
        "minecraft:gold_ore",
        "minecraft:lapis_ore",
        "minecraft:diamond_ore",
    )
    val woolMetaBlocks = setOf(
        "minecraft:oak_stairs",
        "minecraft:cobblestone_stairs",
        "minecraft:slab",
    )

    val builder = DataFixerBuilder(CURRENT_DF_VERSION)
    builder.addSchema(0, ::BetaModSchema)

    val version1 = builder.addSchema(1, ::BetaModSchema)
    builder.addFixer(SetDefaultBlockStateFix(
        version1,
        placedBlocks,
        "placed",
        true
    ))
    builder.addFixer(SetDefaultBlockStateFix(
        version1,
        woolMetaBlocks,
        "wool_meta",
        16
    ))

    val version2 = builder.addSchema(2, ::BetaModSchema)
    builder.addFixer(OrdinalToIdFix(version2))

    return builder.build().fixer()
}
