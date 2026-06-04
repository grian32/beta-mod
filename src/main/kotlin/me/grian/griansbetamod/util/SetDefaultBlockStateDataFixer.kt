package me.grian.griansbetamod.util

import com.mojang.datafixers.DSL
import com.mojang.datafixers.DataFixer
import com.mojang.datafixers.schemas.Schema
import com.mojang.serialization.Dynamic
import net.modificationstation.stationapi.api.datafixer.TypeReferences

class SetDefaultBlockStateDataFixer<PropT>(val blocks: Set<String>, val property: String, val value: PropT) : DataFixer {
    override fun <T : Any?> update(
        type: DSL.TypeReference,
        input: Dynamic<T>,
        version: Int,
        newVersion: Int
    ): Dynamic<T> {
        if (type != TypeReferences.CHUNK || version >= newVersion) return input

        return input.update("Level") { level ->
            level.update(SECTIONS_KEY) { sections ->
                sections.createList(sections.asStream().map { section ->
                    section.update("block_states") { blockStates ->
                        blockStates.update("palette") { palette ->
                            palette.createList(palette.asStream().map(::fixBlockState))
                        }
                    }
                })
            }
        }
    }

    override fun getSchema(key: Int): Schema? = null

    private fun <T> fixBlockState(state: Dynamic<T>): Dynamic<T> {
        if (state.get("Name").asString("") !in blocks) return state

        val properties = state.get("Properties").result().orElseGet(state::emptyMap)
        val placed = properties.get("placed").result()
        if (placed.isPresent && placed.get().asString().result().isPresent) return state

        return state.set("Properties", properties.set(property, state.createString(value.toString())))
    }

    companion object {

        private const val SECTIONS_KEY = "stationapi:sections"
    }
}