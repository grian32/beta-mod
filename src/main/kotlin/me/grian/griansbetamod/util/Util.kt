package me.grian.griansbetamod.util

import net.minecraft.block.Block
import net.minecraft.world.World
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent
import net.modificationstation.stationapi.api.util.Identifier

fun isEventTypeShapeless(eventId: Identifier) =
    RecipeRegisterEvent.Vanilla.fromType(eventId) == RecipeRegisterEvent.Vanilla.CRAFTING_SHAPELESS

fun isEventTypeShaped(eventId: Identifier) =
    RecipeRegisterEvent.Vanilla.fromType(eventId) == RecipeRegisterEvent.Vanilla.CRAFTING_SHAPED

fun isEventTypeSmelting(eventId: Identifier) =
    RecipeRegisterEvent.Vanilla.fromType(eventId) == RecipeRegisterEvent.Vanilla.SMELTING

fun hasAdjacent(world: World, x: Int, y: Int, z: Int, blocks: Array<Block>): Boolean {
    fun matches(x: Int, y: Int, z: Int): Boolean {
        val blockId = world.getBlockId(x, y, z)
        return blocks.any { it.id == blockId }
    }

    return matches(x - 1, y, z) ||
        matches(x + 1, y, z) ||
        matches(x, y - 1, z) ||
        matches(x, y + 1, z) ||
        matches(x, y, z - 1) ||
        matches(x, y, z + 1)
}
