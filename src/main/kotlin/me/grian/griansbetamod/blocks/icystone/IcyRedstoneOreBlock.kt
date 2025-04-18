package me.grian.griansbetamod.blocks.icystone

import me.grian.griansbetamod.BetaMod
import me.grian.griansbetamod.TextureListener
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.modificationstation.stationapi.api.template.block.TemplateRedstoneOreBlock
import net.modificationstation.stationapi.api.util.Identifier
import java.util.*

// this whole thing is just such a stinking mess
class IcyRedstoneOreBlock(identifier: Identifier, lit: Boolean) : TemplateRedstoneOreBlock(identifier, 0, lit) {
    override fun onBlockBreakStart(world: World, x: Int, y: Int, z: Int, player: PlayerEntity?) {
        this.light(world, x, y, z)
        super.onBlockBreakStart(world, x, y, z, player)
    }

    override fun onSteppedOn(world: World, x: Int, y: Int, z: Int, entity: Entity?) {
        this.light(world, x, y, z)
        super.onSteppedOn(world, x, y, z, entity)
    }

    override fun onUse(world: World, x: Int, y: Int, z: Int, player: PlayerEntity?): Boolean {
        this.light(world, x, y, z)
        return super.onUse(world, x, y, z, player)
    }

    private fun light(world: World, x: Int, y: Int, z: Int) {
        this.spawnParticles(world, x, y, z)
        if (this.id == BetaMod.icyRedstoneOre.id) {
            world.setBlock(x, y, z, BetaMod.litIcyRedstoneOre.id)
        }
    }

    override fun onTick(world: World, x: Int, y: Int, z: Int, random: Random?) {
        if (this.id == BetaMod.litIcyRedstoneOre.id) {
            world.setBlock(x, y, z, BetaMod.icyRedstoneOre.id)
        }
    }

    private fun spawnParticles(world: World, x: Int, y: Int, z: Int) {
        val redstoneOreBlockClass = this::class.java.superclass.superclass
        val method = redstoneOreBlockClass.getDeclaredMethod(
            "spawnParticles",
            World::class.java,
            Int::class.java,
            Int::class.java,
            Int::class.java
        )
        method.isAccessible = true
        method.invoke(this, world, x, y, z)
    }
}