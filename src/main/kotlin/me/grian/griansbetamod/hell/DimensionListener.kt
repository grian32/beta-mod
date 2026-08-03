package me.grian.griansbetamod.hell

import me.grian.griansbetamod.BetaMod
import me.grian.griansbetamod.hell.limbo.LimboDimension
import net.mine_diver.unsafeevents.listener.EventListener
import net.minecraft.world.biome.Biome
import net.modificationstation.stationapi.api.client.event.color.item.ItemColorsRegisterEvent
import net.modificationstation.stationapi.api.event.registry.DimensionRegistryEvent
import net.modificationstation.stationapi.api.event.world.biome.BiomeRegisterEvent
import net.modificationstation.stationapi.api.registry.DimensionContainer
import net.modificationstation.stationapi.api.worldgen.biome.BiomeBuilder

object DimensionListener {
    val LIMBO_ID = BetaMod.NAMESPACE.id("hell_limbo")

    lateinit var limboBiome: Biome

    @EventListener
    fun register(event: DimensionRegistryEvent) {
        event.registry.register(LIMBO_ID,
            DimensionContainer(
                { legacyId -> LimboDimension(legacyId) }
            )
        )
    }

    @EventListener
    fun biomeRegister(event: BiomeRegisterEvent) {
        limboBiome = BiomeBuilder
            .start("Limbo")
            .precipitation(false)
            .fogColor(0x919191)
            .grassColor(0x919191)
            .leavesColor(0x919191)
            .build()
    }

    @EventListener
    fun register(event: ItemColorsRegisterEvent) {
        event.itemColors.register({_, tintIndex ->
            if (tintIndex == 1) 0x919191 else 0xFFFFFF
        }, BetaMod.deadGrass)
    }
}