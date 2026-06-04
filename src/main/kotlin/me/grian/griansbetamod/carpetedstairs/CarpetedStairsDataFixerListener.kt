package me.grian.griansbetamod.carpetedstairs

import me.grian.griansbetamod.BetaMod
import me.grian.griansbetamod.config.ConfigScreen
import me.grian.griansbetamod.util.SetDefaultBlockStateDataFixer
import net.mine_diver.unsafeevents.listener.EventListener
import net.modificationstation.stationapi.api.datafixer.DataFixers
import net.modificationstation.stationapi.api.event.datafixer.DataFixerRegisterEvent
import java.util.function.Supplier

class CarpetedStairsDataFixerListener {
    @EventListener
    fun register(event: DataFixerRegisterEvent) {
        if (ConfigScreen.config.carpetedStairsAndSlabs) {
            DataFixers.registerFixer(
                BetaMod.NAMESPACE,
                Supplier { SetDefaultBlockStateDataFixer(
                    setOf(
                        // added cobble stairs too as both the mixins add the blockstate to all types even if unused for simplicity
                        "minecraft:oak_stairs",
                        "minecraft:cobblestone_stairs",
                        "minecraft:slab",
                    ),
                    "wool_meta",
                   16
                ) },
                1)
        }
    }
}