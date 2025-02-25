package me.grian.griansbetamod

import me.grian.griansbetamod.config.ConfigScreen
import net.mine_diver.unsafeevents.listener.EventListener
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases
import net.modificationstation.stationapi.api.util.Namespace

object TextureListener {
    private val NAMESPACE: Namespace = Namespace.of("griansbetamod")

    private val LOGGER = NAMESPACE.logger

    @EventListener
    fun registerTextures(event: TextureRegisterEvent) {
        val terrain = Atlases.getTerrain()

        if (ConfigScreen.config.enableRedstoneBlock) BetaMod.redstoneBlock.textureId = terrain.addTexture(NAMESPACE.id("block/block_redstone")).index
        if (ConfigScreen.config.leatherBootsTrampleCrops) BetaMod.grassyBoots.setTexture(NAMESPACE.id("item/grassy_boots"))
    }
}