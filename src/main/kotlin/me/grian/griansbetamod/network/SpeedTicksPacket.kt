package me.grian.griansbetamod.network

import me.grian.griansbetamod.BetaMod
import me.grian.griansbetamod.mixininterfaces.IPlayerEntityMixin
import me.grian.griansbetamod.util.toClientAccessor
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.network.NetworkHandler
import net.minecraft.network.packet.Packet
import net.modificationstation.stationapi.api.network.packet.ManagedPacket
import net.modificationstation.stationapi.api.network.packet.PacketType
import java.io.DataInputStream
import java.io.DataOutputStream

class SpeedTicksPacket(): Packet(), ManagedPacket<SpeedTicksPacket> {
    private var speedTicks: Int = -1;

    constructor(speedTicks: Int) : this() {
        this.speedTicks = speedTicks
    }

    override fun read(stream: DataInputStream?) {
        println("here")
        speedTicks = stream?.readInt() ?: -1
    }

    override fun write(stream: DataOutputStream?) {
        stream?.writeInt(speedTicks)
    }

    @Environment(EnvType.CLIENT)
    override fun apply(networkHandler: NetworkHandler?) {
        if (FabricLoader.getInstance().environmentType != EnvType.CLIENT) return

        val handler = networkHandler.toClientAccessor()
        val player = handler.minecraft.player as IPlayerEntityMixin

        player.`beta_mod$setSpeedBoostTicks`(speedTicks)
    }

    override fun size(): Int = 4


    override fun getType(): PacketType<SpeedTicksPacket> = TYPE


    companion object {
        val TYPE: PacketType<SpeedTicksPacket> = PacketType.builder(true, false, ::SpeedTicksPacket).build()
        val ID = BetaMod.NAMESPACE.id("speed_ticks")
    }
}