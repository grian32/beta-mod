package me.grian.griansbetamod.itemenhancements

import com.mojang.datafixers.DSL
import com.mojang.datafixers.DataFixer
import com.mojang.datafixers.schemas.Schema
import com.mojang.serialization.Dynamic
import me.grian.griansbetamod.BetaMod
import me.grian.griansbetamod.config.ConfigScreen
import net.mine_diver.unsafeevents.listener.EventListener
import net.modificationstation.stationapi.api.datafixer.DataFixers
import net.modificationstation.stationapi.api.datafixer.TypeReferences
import net.modificationstation.stationapi.api.event.datafixer.DataFixerRegisterEvent
import java.util.function.Supplier

class EnhancementDataFixer : DataFixer {
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
        if (state.get("Name").asString("") !in BLOCKS_WITH_PLACED_PROPERTY) return state

        val properties = state.get("Properties").result().orElseGet(state::emptyMap)
        val placed = properties.get("placed").result()
        if (placed.isPresent && placed.get().asString().result().isPresent) return state

        return state.set("Properties", properties.set("placed", state.createString("true")))
    }

    companion object {
        const val CURRENT_VERSION = 1

        private const val SECTIONS_KEY = "stationapi:sections"

        private val BLOCKS_WITH_PLACED_PROPERTY = setOf(
            "minecraft:stone",
            "minecraft:log",
            "minecraft:coal_ore",
            "minecraft:iron_ore",
            "minecraft:gold_ore",
            "minecraft:lapis_ore",
            "minecraft:diamond_ore",
        )

    }
}

object EnhancementDataFixerListener {
    @EventListener
    fun registerFixer(event: DataFixerRegisterEvent) {
        if (ConfigScreen.config.enhancementSystem) {
            DataFixers.registerFixer(BetaMod.NAMESPACE, Supplier<DataFixer> { EnhancementDataFixer() }, EnhancementDataFixer.CURRENT_VERSION)
        }
    }
}
