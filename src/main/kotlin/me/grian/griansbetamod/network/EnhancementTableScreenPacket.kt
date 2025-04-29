package me.grian.griansbetamod.network

import me.grian.griansbetamod.BetaMod
import me.grian.griansbetamod.itemenhancements.screen.EnhancementScreen
import me.grian.griansbetamod.util.toClientAccessor
import net.fabricmc.api.EnvType
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.network.NetworkHandler
import net.minecraft.network.packet.Packet
import net.modificationstation.stationapi.api.network.packet.ManagedPacket
import net.modificationstation.stationapi.api.network.packet.PacketType
import java.io.DataInputStream
import java.io.DataOutputStream

class EnhancementTableScreenPacket(): Packet(), ManagedPacket<EnhancementTableScreenPacket> {
    private var x = -1
    private var y = -1
    private var z = -1
    private var syncId = -1;

    constructor(x: Int, y: Int, z: Int, syncId: Int): this() {
        this.x = x
        this.y = y
        this.z = z
        this.syncId = syncId
    }

    override fun read(stream: DataInputStream?) {
        x = stream.readIntNotNull()
        y = stream.readIntNotNull()
        z = stream.readIntNotNull()
        syncId = stream.readIntNotNull()
    }

    override fun write(stream: DataOutputStream?) {
        stream?.writeInt(x)
        stream?.writeInt(y)
        stream?.writeInt(z)
        stream?.writeInt(syncId)
    }

    override fun apply(networkHandler: NetworkHandler?) {
        if (FabricLoader.getInstance().environmentType != EnvType.CLIENT) return

        val handler = networkHandler.toClientAccessor()
        val minecraft = handler.minecraft
        val player = minecraft.player

        // ref ClientNetworkHandler.openScreen()
        minecraft.setScreen(EnhancementScreen(player.inventory, player.world, x, y, z))
        player.currentScreenHandler.syncId = syncId
    }

    override fun size(): Int = 12 // 3 x 4 bytes (ints)

    override fun getType(): PacketType<EnhancementTableScreenPacket> = TYPE

    companion object {
        val TYPE: PacketType<EnhancementTableScreenPacket> =
            PacketType.builder(true, false, ::EnhancementTableScreenPacket).build()
        val ID = BetaMod.NAMESPACE.id("enhancement_table_screen")
    }

    private fun DataInputStream?.readIntNotNull() = this?.readInt() ?: -1
}