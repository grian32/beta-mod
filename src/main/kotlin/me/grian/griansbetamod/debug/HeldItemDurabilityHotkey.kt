package me.grian.griansbetamod.debug

import net.fabricmc.loader.api.FabricLoader
import net.mine_diver.unsafeevents.listener.EventListener
import net.minecraft.client.Minecraft
import net.minecraft.client.option.KeyBinding
import net.modificationstation.stationapi.api.client.event.keyboard.KeyStateChangedEvent
import net.modificationstation.stationapi.api.client.event.option.KeyBindingRegisterEvent
import org.lwjgl.input.Keyboard

object HeldItemDurabilityHotkey {
    private val keyBinding = KeyBinding(
        "key.griansbetamod.print_held_durability",
        Keyboard.KEY_F7
    )

    @EventListener
    fun registerKeyBinding(event: KeyBindingRegisterEvent) {
        if (FabricLoader.getInstance().isDevelopmentEnvironment) {
            event.register(keyBinding)
        }
    }

    @Suppress("DEPRECATION")
    @EventListener
    fun printHeldDurability(event: KeyStateChangedEvent) {
        val loader = FabricLoader.getInstance()
        if (!loader.isDevelopmentEnvironment) return
        if (event.environment != KeyStateChangedEvent.Environment.IN_GAME) return
        if (!Keyboard.getEventKeyState() || Keyboard.getEventKey() != keyBinding.code) return

        val minecraft = loader.gameInstance as? Minecraft ?: return
        val stack = minecraft.player?.inventory?.selectedItem

        if (stack == null || stack.maxDamage <= 0) return
        minecraft.setScreen(DevDurabilityScreen(stack))
    }
}
