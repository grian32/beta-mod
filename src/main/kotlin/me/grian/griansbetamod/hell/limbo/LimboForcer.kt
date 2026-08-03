package me.grian.griansbetamod.hell.limbo

import net.minecraft.entity.Entity
import net.minecraft.world.World
import net.minecraft.world.dimension.PortalForcer

class LimboForcer : PortalForcer() {
    override fun moveToPortal(world: World, entity: Entity) {
        entity.setPositionAndAnglesKeepPrevAngles(
            entity.x,
            2.0,
            entity.z,
            entity.yaw,
            entity.pitch
        )
    }
}