package me.grian.griansbetamod.itemenhancements

import me.grian.griansbetamod.BetaMod
import me.grian.griansbetamod.config.ConfigScreen
import me.grian.griansbetamod.util.SetDefaultBlockStateDataFixer
import net.mine_diver.unsafeevents.listener.EventListener
import net.modificationstation.stationapi.api.datafixer.DataFixers
import net.modificationstation.stationapi.api.event.datafixer.DataFixerRegisterEvent
import java.util.function.Supplier

object EnhancementDataFixerListener {
    @EventListener
    fun registerFixer(event: DataFixerRegisterEvent) {
        if (ConfigScreen.config.enhancementSystem) {
            DataFixers.registerFixer(
                BetaMod.NAMESPACE,
                Supplier { SetDefaultBlockStateDataFixer(
                    setOf(
                        "minecraft:stone",
                        "minecraft:log",
                        "minecraft:coal_ore",
                        "minecraft:iron_ore",
                        "minecraft:gold_ore",
                        "minecraft:lapis_ore",
                        "minecraft:diamond_ore",
                    ),
                    "placed",
                    true
                ) },
                1)
        }
    }
}
