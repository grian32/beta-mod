package me.grian.griansbetamod.events.init

import net.mine_diver.unsafeevents.listener.EventListener
import net.modificationstation.stationapi.api.event.mod.InitEvent
import net.modificationstation.stationapi.api.util.Namespace
import org.apache.logging.log4j.Logger

object InitListener {
    val NAMESPACE: Namespace = Namespace.of("griansbetamod")

    val LOGGER: Logger = NAMESPACE.logger

    @EventListener
    fun serverInit(event: InitEvent?) {
        LOGGER.info(NAMESPACE.toString())
    }
}