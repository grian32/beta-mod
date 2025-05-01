package me.grian.griansbetamod

import me.grian.griansbetamod.config.ConfigScreen
import net.mine_diver.unsafeevents.listener.EventListener
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases
import net.modificationstation.stationapi.api.util.Namespace

object TextureListener {
    private val NAMESPACE: Namespace = Namespace.of("griansbetamod")

    private val LOGGER = NAMESPACE.logger

    var sawmillLeft = 0
    var sawmillTop = 0
    var sawmillSide = 0

    @EventListener
    fun registerTextures(event: TextureRegisterEvent) {
        val terrain = Atlases.getTerrain()

        if (ConfigScreen.config.enableRedstoneBlock) BetaMod.redstoneBlock.textureId = terrain.addTexture(NAMESPACE.id("block/block_redstone")).index
        if (ConfigScreen.config.leatherBootsTrampleCrops) BetaMod.grassyBoots.setTexture(NAMESPACE.id("item/grassy_boots"))
        if (ConfigScreen.config.sawmillBlock) {
            sawmillLeft = terrain.addTexture(NAMESPACE.id("block/sawmill_left")).index
            sawmillTop = terrain.addTexture(NAMESPACE.id("block/sawmill_top")).index
            sawmillSide = terrain.addTexture(NAMESPACE.id("block/sawmill_side")).index
        }
        if (ConfigScreen.config.netherGlass) BetaMod.netherGlass.textureId = terrain.addTexture(NAMESPACE.id("block/nether_glass")).index
        if (ConfigScreen.config.lapisSpeedBoost) BetaMod.speedCrystal.setTexture(NAMESPACE.id("item/speed_crystal"))
        if (ConfigScreen.config.enhancementSystem) {
            BetaMod.resin.setTexture(NAMESPACE.id("item/resin"))
            BetaMod.resinBlock.textureId = terrain.addTexture(NAMESPACE.id("block/resin_block")).index
        }
    }
}