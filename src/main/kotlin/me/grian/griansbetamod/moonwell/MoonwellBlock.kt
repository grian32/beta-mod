package me.grian.griansbetamod.moonwell

import me.grian.griansbetamod.itemenhancements.getEnhancement
import me.grian.griansbetamod.itemenhancements.getEnhancementTier
import me.grian.griansbetamod.network.MoonwellVisualPacket
import net.fabricmc.api.EnvType
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.material.Material
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.World
import net.modificationstation.stationapi.api.template.block.TemplateBlock
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity
import net.modificationstation.stationapi.api.util.Identifier

class MoonwellBlock(identifier: Identifier) : TemplateBlockWithEntity(identifier, Material.STONE) {
    override fun isOpaque() = false
    override fun isFullCube() = false

    override fun createBlockEntity(): BlockEntity {
        return MoonwellBlockEntity()
    }

    override fun onUse(world: World, x: Int, y: Int, z: Int, player: PlayerEntity): Boolean {
        if (world.isRemote) return true

        val blockEntity = world.getBlockEntity(x, y, z) as MoonwellBlockEntity
        if (blockEntity.getStack() == null) {
            if (player.inventory.selectedItem == null) return false

            if (blockEntity.setItemStack(player.inventory.selectedItem, true)) {
                player.inventory.setStack(player.inventory.selectedSlot, null)
                blockEntity.markDirty()
                world.blockUpdateEvent(x, y, z)
                return true
            }
        } else if (blockEntity.getStack() != null && player.inventory.selectedItem == null) {
            val stack = blockEntity.getStack()
            blockEntity.setItemStack(null, true)
            player.inventory.setStack(player.inventory.selectedSlot, stack)
            blockEntity.markDirty()
            world.blockUpdateEvent(x, y, z)
            return true
        }

        return false
    }

    override fun onBreak(world: World, x: Int, y: Int, z: Int) {
        if (world.isRemote) return
        val blockEntity = world.getBlockEntity(x, y, z) as MoonwellBlockEntity
        if (blockEntity.getStack() != null) {
            dropStack(world, x, y, z, blockEntity.getStack())
        }
        super.onBreak(world, x, y, z)
    }
}