package me.grian.griansbetamod.network

import me.grian.griansbetamod.BetaMod
import me.grian.griansbetamod.itemenhancements.Enhancement
import me.grian.griansbetamod.moonwell.MoonwellBlockEntity
import me.grian.griansbetamod.util.toClientAccessor
import net.fabricmc.api.EnvType
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.item.ItemStack
import net.minecraft.network.NetworkHandler
import net.minecraft.network.packet.Packet
import net.modificationstation.stationapi.api.network.packet.ManagedPacket
import net.modificationstation.stationapi.api.network.packet.PacketType
import java.io.DataInputStream
import java.io.DataOutputStream

class MoonwellVisualPacket() : Packet(), ManagedPacket<MoonwellVisualPacket> {
    private var x: Int = -1
    private var y: Int = -1
    private var z: Int = -1
    private var itemId: Int = -1

    constructor(itemId: Int, x: Int, y: Int, z: Int): this() {
        this.itemId = itemId
        this.x = x
        this.y = y
        this.z = z
    }

    override fun read(stream: DataInputStream) {
        x = stream.readInt()
        y = stream.readInt()
        z = stream.readInt()
        itemId = stream.readInt()
    }

    override fun write(stream: DataOutputStream) {
        stream.writeInt(x)
        stream.writeInt(y)
        stream.writeInt(z)
        stream.writeInt(itemId)
    }

    override fun apply(networkHandler: NetworkHandler) {
        if (FabricLoader.getInstance().environmentType != EnvType.CLIENT) return

        val handler = networkHandler.toClientAccessor()
        val minecraft = handler.minecraft
        val world = minecraft.world
        if (!world.isRemote) return

        val blockEntity = world.getBlockEntity(x, y, z) as? MoonwellBlockEntity ?: return
        if (itemId != -1) {
            blockEntity.setItemStack(ItemStack(itemId, 1, 0), false)
        } else {
            blockEntity.setItemStack(null, false)
        }

    }

    override fun size(): Int = 16

    override fun getType(): PacketType<MoonwellVisualPacket> {
        return TYPE
    }

    companion object {
        val TYPE: PacketType<MoonwellVisualPacket> =
            PacketType.builder(true, false, ::MoonwellVisualPacket).build()

        val ID = BetaMod.NAMESPACE.id("moonwell_visual_packet")
    }
}