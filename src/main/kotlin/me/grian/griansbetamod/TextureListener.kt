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
        if (ConfigScreen.config.icyStone) {
            BetaMod.icyStone.textureId = terrain.addTexture(NAMESPACE.id("block/icy_stone")).index
            BetaMod.icyCobblestone.textureId = terrain.addTexture(NAMESPACE.id("block/icy_cobblestone")).index
            BetaMod.icyCoalOre.textureId = terrain.addTexture(NAMESPACE.id("block/icy_coal_ore")).index
            BetaMod.icyIronOre.textureId = terrain.addTexture(NAMESPACE.id("block/icy_iron_ore")).index
            BetaMod.icyGoldOre.textureId = terrain.addTexture(NAMESPACE.id("block/icy_gold_ore")).index
            BetaMod.icyDiamondOre.textureId = terrain.addTexture(NAMESPACE.id("block/icy_diamond_ore")).index
            BetaMod.icyLapisLazuliOre.textureId = terrain.addTexture(NAMESPACE.id("block/icy_lapis_lazuli_ore")).index
        }
        if (ConfigScreen.config.sawmillBlock) {
            sawmillLeft = terrain.addTexture(NAMESPACE.id("block/sawmill_left")).index
            sawmillTop = terrain.addTexture(NAMESPACE.id("block/sawmill_top")).index
            sawmillSide = terrain.addTexture(NAMESPACE.id("block/sawmill_side")).index
        }
        if (ConfigScreen.config.netherGlass) BetaMod.netherGlass.textureId = terrain.addTexture(NAMESPACE.id("block/nether_glass")).index
        if (ConfigScreen.config.lapisSpeedBoost) BetaMod.speedCrystal.setTexture(NAMESPACE.id("item/speed_crystal"))
    }
}