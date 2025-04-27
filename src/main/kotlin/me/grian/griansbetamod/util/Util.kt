package me.grian.griansbetamod.util

import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent
import net.modificationstation.stationapi.api.util.Identifier
import kotlin.random.Random

fun isEventTypeShapeless(eventId: Identifier) =
    RecipeRegisterEvent.Vanilla.fromType(eventId) == RecipeRegisterEvent.Vanilla.CRAFTING_SHAPELESS

fun isEventTypeShaped(eventId: Identifier) =
    RecipeRegisterEvent.Vanilla.fromType(eventId) == RecipeRegisterEvent.Vanilla.CRAFTING_SHAPED

fun isEventTypeSmelting(eventId: Identifier) =
    RecipeRegisterEvent.Vanilla.fromType(eventId) == RecipeRegisterEvent.Vanilla.SMELTING

infix fun Random.oneIn(x: Int) = this.nextInt(x) == 0
infix fun java.util.Random.oneIn(x: Int) = this.nextInt(x) == 0
fun oneIn(x: Int, random: java.util.Random) = random.nextInt(x) == 0