package me.grian.griansbetamod.util

import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent
import net.modificationstation.stationapi.api.util.Identifier

fun isEventTypeShapeless(eventId: Identifier) =
    RecipeRegisterEvent.Vanilla.fromType(eventId) == RecipeRegisterEvent.Vanilla.CRAFTING_SHAPELESS

fun isEventTypeShaped(eventId: Identifier) =
    RecipeRegisterEvent.Vanilla.fromType(eventId) == RecipeRegisterEvent.Vanilla.CRAFTING_SHAPED

fun isEventTypeSmelting(eventId: Identifier) =
    RecipeRegisterEvent.Vanilla.fromType(eventId) == RecipeRegisterEvent.Vanilla.SMELTING

// specifies full qualifier since diff randoms
fun Int.outOf(x: Int, random: java.util.Random) = random.nextInt(x) in 0..<this
fun Int.outOf(x: Int, random: kotlin.random.Random) = random.nextInt(x) in 0..<this