package me.grian.griansbetamod

import com.google.common.annotations.Beta
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

    var charcoalTexture = 0

    var frostRootCropTextures = Array(4) { 0 }

    @EventListener
    fun registerTextures(event: TextureRegisterEvent) {
        val terrain = Atlases.getTerrain()
        val items = Atlases.getGuiItems()

        if (ConfigScreen.config.enableRedstoneBlock) BetaMod.redstoneBlock.textureId = terrain.addTexture(NAMESPACE.id("block/block_redstone")).index
        if (ConfigScreen.config.leatherBootsTrampleCrops) BetaMod.grassyBoots.setTexture(NAMESPACE.id("item/grassy_boots"))
        if (ConfigScreen.config.sawmillBlock) {
            sawmillLeft = terrain.addTexture(NAMESPACE.id("block/sawmill_left")).index
            sawmillTop = terrain.addTexture(NAMESPACE.id("block/sawmill_top")).index
            sawmillSide = terrain.addTexture(NAMESPACE.id("block/sawmill_side")).index
        }
        if (ConfigScreen.config.netherGlass) BetaMod.netherGlass.textureId = terrain.addTexture(NAMESPACE.id("block/nether_glass")).index
        if (ConfigScreen.config.climate) {
            val bluePeonyId = terrain.addTexture(NAMESPACE.id("block/cyan_rose")).index
            BetaMod.bluePeony.textureId = bluePeonyId
            BetaMod.bluePeony.asItem().setTextureId(bluePeonyId)
        }

        if (ConfigScreen.config.enhancementSystem) {
            BetaMod.resin.setTexture(NAMESPACE.id("item/resin"))
            BetaMod.resinBlock.textureId = terrain.addTexture(NAMESPACE.id("block/resin_block")).index
            BetaMod.totemOfHealth.setTexture(NAMESPACE.id("item/totem_of_health"))
        }
        if (ConfigScreen.config.charcoalTexture) charcoalTexture = items.addTexture(NAMESPACE.id("item/charcoal")).index
        if (ConfigScreen.config.icyDungeons) {
            BetaMod.icyStone.textureId = terrain.addTexture(NAMESPACE.id("block/icy_stone")).index
            BetaMod.icyCobblestone.textureId = terrain.addTexture(NAMESPACE.id("block/icy_cobblestone")).index
            BetaMod.frostRoot.setTexture(NAMESPACE.id("item/frost_root"))
            BetaMod.cookedFrostRoot.setTexture(NAMESPACE.id("item/cooked_frost_root"))
            BetaMod.frostRootSeeds.setTexture(NAMESPACE.id("item/frost_root_seeds"))
            frostRootCropTextures[0] = terrain.addTexture(NAMESPACE.id("block/frost_root_stage0")).index
            frostRootCropTextures[1] = terrain.addTexture(NAMESPACE.id("block/frost_root_stage1")).index
            frostRootCropTextures[2] = terrain.addTexture(NAMESPACE.id("block/frost_root_stage2")).index
            frostRootCropTextures[3] = terrain.addTexture(NAMESPACE.id("block/frost_root_stage3")).index
        }
    }
}