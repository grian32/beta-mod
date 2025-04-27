package me.grian.griansbetamod.blocks

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.util.math.Box
import net.minecraft.world.World
import net.modificationstation.stationapi.api.block.BlockState
import net.modificationstation.stationapi.api.item.ItemPlacementContext
import net.modificationstation.stationapi.api.state.StateManager
import net.modificationstation.stationapi.api.state.property.BooleanProperty
import net.modificationstation.stationapi.api.template.block.TemplateBlock
import net.modificationstation.stationapi.api.util.Identifier
import net.modificationstation.stationapi.api.util.math.Direction


class PileOfLogsBlock(identifier: Identifier) : TemplateBlock(identifier, Material.WOOD) {
    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        builder?.add(ROTATED)
        super.appendProperties(builder)
    }

    override fun getPlacementState(context: ItemPlacementContext?): BlockState =
        when (context?.horizontalPlayerFacing) {
            Direction.EAST, Direction.WEST -> defaultState.with(ROTATED, false)
            Direction.NORTH, Direction.SOUTH -> defaultState.with(ROTATED, true)
            else -> defaultState.with(ROTATED, false) // null, down, up, shouldnt happen with this property
        }

    override fun isFullCube(): Boolean = false
    override fun isOpaque(): Boolean = false

    override fun getCollisionShape(world: World?, x: Int, y: Int, z: Int): Box = getBox(x, y, z)
    override fun getBoundingBox(world: World?, x: Int, y: Int, z: Int): Box = getBox(x, y, z)

    private fun getBox(x: Int, y: Int, z: Int): Box =
        Box.createCached(
            x.toDouble(),
            y.toDouble(),
            z.toDouble(),
            x.toDouble() + 1,
            y.toDouble() + 0.75,
            z.toDouble() + 1
        )

    companion object {
        // needs to be equal to: private static final
        @JvmStatic
        private val ROTATED: BooleanProperty = BooleanProperty.of("rotated")
    }
}