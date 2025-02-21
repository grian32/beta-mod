package me.grian.griansbetamod.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.mod.InitEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Null;
import net.modificationstation.stationapi.api.util.Namespace;
import org.apache.logging.log4j.Logger;

public class InitListener {

    @Entrypoint.Namespace
    public static Namespace NAMESPACE = Null.get();

    @Entrypoint.Logger
    public static Logger LOGGER = Null.get();

    @EventListener
    public static void serverInit(InitEvent event) {
        LOGGER.info(NAMESPACE.toString());
    }
}