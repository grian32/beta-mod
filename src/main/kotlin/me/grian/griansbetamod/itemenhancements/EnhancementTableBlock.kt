package me.grian.griansbetamod.itemenhancements

import me.grian.griansbetamod.itemenhancements.screen.EnhancementScreen
import me.grian.griansbetamod.itemenhancements.screen.EnhancementScreenHandler
import me.grian.griansbetamod.network.EnhancementTableScreenPacket
import me.grian.griansbetamod.util.toAccessor
import me.grian.griansbetamod.util.toServerPlayer
import net.fabricmc.api.EnvType
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.World
import net.modificationstation.stationapi.api.block.BlockState
import net.modificationstation.stationapi.api.item.ItemPlacementContext
import net.modificationstation.stationapi.api.network.packet.PacketHelper
import net.modificationstation.stationapi.api.state.StateManager
import net.modificationstation.stationapi.api.state.property.IntProperty
import net.modificationstation.stationapi.api.template.block.TemplateBlock
import net.modificationstation.stationapi.api.util.Identifier

class EnhancementTableBlock(identifier: Identifier) : TemplateBlock(identifier, Material.STONE) {
    @Suppress("DEPRECATION") // only applies in newer versions
    override fun onUse(world: World?, x: Int, y: Int, z: Int, player: PlayerEntity?): Boolean {
        if (FabricLoader.getInstance().environmentType == EnvType.SERVER) {
            val serverPlayer = player.toServerPlayer()
            val accessor = serverPlayer.toAccessor()

            // ref ServerPlayerEntity.openCraftingScreen
            accessor.incrementScreenHanderSyncId()
            PacketHelper.sendTo(serverPlayer, EnhancementTableScreenPacket(x, y, z, accessor.screenHandlerSyncId))
            serverPlayer.currentScreenHandler = EnhancementScreenHandler(serverPlayer.inventory, serverPlayer.world, x, y, z)
            serverPlayer.currentScreenHandler.syncId = accessor.screenHandlerSyncId
            serverPlayer.currentScreenHandler.addListener(serverPlayer)

            return true
        }

        val minecraft = FabricLoader.getInstance().gameInstance as Minecraft;

        minecraft.setScreen(EnhancementScreen(player!!.inventory, world!!, x, y, z))

        return true
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        builder?.add(DIRECTION)
        super.appendProperties(builder)
    }

    override fun getPlacementState(context: ItemPlacementContext?): BlockState =
        defaultState.with(DIRECTION, context?.horizontalPlayerFacing?.horizontal!!)

    companion object {
        @JvmStatic
        private val DIRECTION: IntProperty = IntProperty.of("direction", 0, 3)
    }
}