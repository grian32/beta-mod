package me.grian.griansbetamod.moonwell

import net.minecraft.block.entity.BlockEntity
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.entity.EntityRenderDispatcher
import net.minecraft.entity.ItemEntity

class MoonwellBlockEntityRenderer : BlockEntityRenderer() {
    override fun render(
        blockEntity: BlockEntity,
        x: Double,
        y: Double,
        z: Double,
        tickDelta: Float
    ) {
        val moonwell = blockEntity as MoonwellBlockEntity
        val stack = moonwell.getStack() ?: return

        val displayedItem = ItemEntity(
            moonwell.world,
            moonwell.x + 0.5,
            moonwell.y + 0.35,
            moonwell.z + 0.5,
            stack
        )

        displayedItem.itemAge = moonwell.world.time.toInt()
        displayedItem.initialRotationAngle = 0.0F

        EntityRenderDispatcher.INSTANCE.render(
            displayedItem,
            x + 0.5,
            y + 0.35,
            z + 0.5,
            0.0F,
            tickDelta
        )
    }
}