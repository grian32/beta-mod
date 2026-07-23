package me.grian.griansbetamod.network

import me.grian.griansbetamod.BetaMod
import me.grian.griansbetamod.shrine.ShrineState
import me.grian.griansbetamod.util.toClientAccessor
import net.fabricmc.api.EnvType
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.network.NetworkHandler
import net.minecraft.network.packet.Packet
import net.modificationstation.stationapi.api.network.packet.ManagedPacket
import net.modificationstation.stationapi.api.network.packet.PacketType
import java.io.DataInputStream
import java.io.DataOutputStream

class ShrineActivatedPacket(): Packet(), ManagedPacket<ShrineActivatedPacket> {
    private var shrineActivated: Boolean? = null

    constructor(shrineActivated: Boolean): this() {
        this.shrineActivated = shrineActivated;
    }

    override fun read(stream: DataInputStream) {
        shrineActivated = stream.readBoolean()
    }

    override fun write(stream: DataOutputStream) {
        if (shrineActivated == null) {
            throw IllegalStateException("shrine activated should not be null when writing packet")
        }
        stream.writeBoolean(shrineActivated!!)
    }

    override fun apply(networkHandler: NetworkHandler) {
        if (FabricLoader.getInstance().environmentType != EnvType.CLIENT) return

        val handler = networkHandler.toClientAccessor()
        val minecraft = handler.minecraft
        val world = minecraft.world
        val state = ShrineState.get(world)

        state.setShrineState(shrineActivated!!)
    }

    override fun size(): Int = 1

    override fun getType(): PacketType<ShrineActivatedPacket> = TYPE

    companion object {
        val TYPE: PacketType<ShrineActivatedPacket> =
            PacketType.builder(true, false, ::ShrineActivatedPacket).build()

        val ID = BetaMod.NAMESPACE.id("shrine_activated_packet")
    }
}
