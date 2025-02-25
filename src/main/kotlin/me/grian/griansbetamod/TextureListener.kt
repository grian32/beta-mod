package me.grian.griansbetamod

import net.mine_diver.unsafeevents.listener.EventListener
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases
import net.modificationstation.stationapi.api.util.Namespace
import kotlin.properties.Delegates

object TextureListener {
    private val NAMESPACE: Namespace = Namespace.of("griansbetamod")

    private val LOGGER = NAMESPACE.logger

    @JvmStatic
    var redstoneBlockTexture by Delegates.notNull<Int>()

    @EventListener
    fun registerTextures(event: TextureRegisterEvent) {
        val terrain = Atlases.getTerrain()

        BetaMod.redstoneBlock.textureId = terrain.addTexture(NAMESPACE.id("block/block_redstone")).index
        BetaMod.grassyBoots.setTexture(NAMESPACE.id("item/grassy_boots"))
    }
}