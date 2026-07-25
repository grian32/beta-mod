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
    private var playSound: Boolean? = null

    constructor(shrineActivated: Boolean, playSound: Boolean): this() {
        this.shrineActivated = shrineActivated
        this.playSound = playSound
    }

    override fun read(stream: DataInputStream) {
        shrineActivated = stream.readBoolean()
        playSound = stream.readBoolean()
    }

    override fun write(stream: DataOutputStream) {
        if (shrineActivated == null || playSound == null) {
            throw IllegalStateException("shrine activated packet fields should not be null when writing packet")
        }
        stream.writeBoolean(shrineActivated!!)
        stream.writeBoolean(playSound!!)
    }

    override fun apply(networkHandler: NetworkHandler) {
        if (FabricLoader.getInstance().environmentType != EnvType.CLIENT) return

        val handler = networkHandler.toClientAccessor()
        val minecraft = handler.minecraft
        val world = minecraft.world
        val state = ShrineState.get(world)

        state.setShrineState(shrineActivated!!)

        if (playSound!!) {
            world.playSound(
                minecraft.player.x,
                minecraft.player.y,
                minecraft.player.z,
                "ambient.weather.thunder",
                10000.0F,
                0.8F
            )
        }
    }

    override fun size(): Int = 2

    override fun getType(): PacketType<ShrineActivatedPacket> = TYPE

    companion object {
        val TYPE: PacketType<ShrineActivatedPacket> =
            PacketType.builder(true, false, ::ShrineActivatedPacket).build()

        val ID = BetaMod.NAMESPACE.id("shrine_activated_packet")
    }
}
