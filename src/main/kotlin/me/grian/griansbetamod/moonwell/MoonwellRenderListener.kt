package me.grian.griansbetamod.moonwell

import net.mine_diver.unsafeevents.listener.EventListener
import net.modificationstation.stationapi.api.client.event.block.entity.BlockEntityRendererRegisterEvent

object MoonwellRenderListener {
    // TODO: test mp
    @EventListener
    fun registerRender(event: BlockEntityRendererRegisterEvent) {
        event.register(
            MoonwellBlockEntity::class.java,
            MoonwellBlockEntityRenderer()
        )
    }
}