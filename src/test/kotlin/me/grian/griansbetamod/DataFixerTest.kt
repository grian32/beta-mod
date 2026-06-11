package me.grian.griansbetamod

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mojang.datafixers.DSL
import com.mojang.serialization.Dynamic
import com.mojang.serialization.JsonOps
import net.modificationstation.stationapi.api.datafixer.TypeReferences
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class DataFixerTest {

    private val fixer = createBetaModDataFixer()

    @Test
    fun convertsEnhancementOrdinalInPlayerInventory() {
        val player = jsonObject(
            "Inventory" to jsonArray(
                enhancedStack(4),
                plainStack()
            )
        )

        val inventory = update(TypeReferences.PLAYER, player, 1)
            .getAsJsonArray("Inventory")

        assertEquals(-1, enhancement(inventory[0].asJsonObject))
        assertEquals(0, enhancementTier(inventory[0].asJsonObject))
        assertNotEquals(
            inventory[1].asJsonObject
                .getAsJsonObject("stationapi:item_nbt")
                ?.has("enhancement"), true
        )
    }

    @Test
    fun removesLapisMinerEnhancementFromVersionTwoData() {
        val player = jsonObject(
            "Inventory" to jsonArray(enhancedStack(3))
        )

        val stack = update(TypeReferences.PLAYER, player, 2)
            .getAsJsonArray("Inventory")[0]
            .asJsonObject

        assertEquals(-1, enhancement(stack))
        assertEquals(0, enhancementTier(stack))
    }

    @Test
    fun convertsDroppedItemAndBlockStatePaletteInChunk() {
        val chunk = jsonObject(
            "Level" to jsonObject(
                "Entities" to jsonArray(
                    jsonObject(
                        "id" to "Item",
                        "Item" to enhancedStack(2)
                    )
                ),
                "TileEntities" to jsonArray(
                    jsonObject(
                        "id" to "Chest",
                        "Items" to jsonArray(enhancedStack(6))
                    )
                ),
                "stationapi:sections" to jsonArray(
                    jsonObject(
                        "block_states" to jsonObject(
                            "palette" to jsonArray(
                                jsonObject("Name" to "minecraft:stone"),
                                jsonObject("Name" to "minecraft:oak_stairs"),
                                jsonObject("Name" to "minecraft:dirt")
                            )
                        )
                    )
                )
            )
        )

        val level = update(TypeReferences.CHUNK, chunk, 0)
            .getAsJsonObject("Level")
        val droppedStack = level.getAsJsonArray("Entities")[0]
            .asJsonObject
            .getAsJsonObject("Item")
        val chestStack = level.getAsJsonArray("TileEntities")[0]
            .asJsonObject
            .getAsJsonArray("Items")[0]
            .asJsonObject
        val palette = level.getAsJsonArray("stationapi:sections")[0]
            .asJsonObject
            .getAsJsonObject("block_states")
            .getAsJsonArray("palette")
        val fixedStone = palette[0].asJsonObject
        val fixedStairs = palette[1].asJsonObject
        val fixedDirt = palette[2].asJsonObject

        assertEquals(1, enhancement(droppedStack))
        assertEquals(5, enhancement(chestStack))
        assertEquals("true", fixedStone.getAsJsonObject("Properties")["placed"].asString)
        assertEquals("16", fixedStairs.getAsJsonObject("Properties")["wool_meta"].asString)
        assertTrue(fixedDirt.getAsJsonObject("Properties") == null)
    }

    private fun enhancedStack(ordinal: Int) = plainStack().apply {
        add(
            "stationapi:item_nbt",
            jsonObject(
                "enhancement" to ordinal,
                "enhancementTier" to 2
            )
        )
    }

    private fun plainStack() = jsonObject(
        "stationapi:id" to "minecraft:diamond_pickaxe",
        "Count" to 1,
        "Damage" to 0
    )

    private fun enhancement(stack: JsonObject): Int =
        stack.getAsJsonObject("stationapi:item_nbt")["enhancement"].asInt

    private fun enhancementTier(stack: JsonObject): Int =
        stack.getAsJsonObject("stationapi:item_nbt")["enhancementTier"].asInt

    private fun update(
        type: DSL.TypeReference,
        input: JsonObject,
        version: Int
    ): JsonObject =
        fixer.update(
            type,
            Dynamic(JsonOps.INSTANCE, input),
            version,
            CURRENT_DF_VERSION
        ).value.asJsonObject

    private fun jsonArray(vararg values: JsonElement): JsonArray =
        JsonArray().apply { values.forEach(::add) }

    private fun jsonObject(vararg entries: Pair<String, Any>): JsonObject =
        JsonObject().apply {
            entries.forEach { (key, value) ->
                when (value) {
                    is JsonElement -> add(key, value)
                    is String -> addProperty(key, value)
                    is Number -> addProperty(key, value)
                    is Boolean -> addProperty(key, value)
                    else -> error("Unsupported JSON value: ${value::class}")
                }
            }
        }
}
