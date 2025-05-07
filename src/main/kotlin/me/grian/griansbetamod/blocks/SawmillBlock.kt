package me.grian.griansbetamod.blocks

import me.grian.griansbetamod.Materials
import net.minecraft.block.Block
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.modificationstation.stationapi.api.block.BlockState
import net.modificationstation.stationapi.api.item.ItemPlacementContext
import net.modificationstation.stationapi.api.state.StateManager
import net.modificationstation.stationapi.api.state.property.IntProperty
import net.modificationstation.stationapi.api.template.block.TemplateBlock
import net.modificationstation.stationapi.api.util.Identifier
import net.modificationstation.stationapi.api.util.math.Vec3i


class SawmillBlock(identifier: Identifier) : TemplateBlock(identifier, Materials.SAWMILL) {
    override fun onUse(world: World?, x: Int, y: Int, z: Int, player: PlayerEntity?): Boolean {
        val bs = world!!.getBlockState(x, y, z)

        val referenceBlockCoordinate = Vec3i(x, y, z).add(getCoordinateModifier(bs.get(DIRECTION)))

        val referenceBlockId = world.getBlockId(referenceBlockCoordinate.x, referenceBlockCoordinate.y, referenceBlockCoordinate.z)
        val referenceBlockMeta = world.getBlockMeta(referenceBlockCoordinate.x, referenceBlockCoordinate.y, referenceBlockCoordinate.z)

        var referenceBlock: Block? = null

        when {
            referenceBlockId == Block.WOODEN_STAIRS.id -> referenceBlock = WOODEN_STAIRS
            referenceBlockId == Block.SLAB.id && referenceBlockMeta == 2 -> referenceBlock = SLAB
        }

        if (referenceBlock == null) return false

        val slot = player!!.inventory.selectedSlot
        val stack = player.inventory.main[slot]

        if (stack != null && stack.itemId == PLANKS.id) {
            val stackCount = stack.count

            when (referenceBlock) {
                WOODEN_STAIRS -> {
                    player.inventory.main[slot] = ItemStack(WOODEN_STAIRS, stackCount)

                    world.playSound(x.toDouble() + 0.5, y.toDouble() + 0.5, z.toDouble() + 0.5, "step.wood", 1.0f, 3.5f)

                    return true
                }
                SLAB -> {
                    val finalAmount = stackCount * 2

                    player.inventory.main[slot] = ItemStack(SLAB, minOf(finalAmount, 64), 2)

                    if (finalAmount > 64) {
                        player.inventory.addStack(ItemStack(SLAB, finalAmount - 64, 2))
                    }

                    world.playSound(x.toDouble() + 0.5, y.toDouble() + 0.5, z.toDouble() + 0.5, "step.wood", 1.0f, 3.5f)

                    return true
                }
            }

        }

        return false
    }

    private fun getCoordinateModifier(direction: Int): Vec3i {
        /**
         * dir 0 = +x
         * dir 1 = +z
         * dir 2 = -x
         * dir 3 = -z
         */
        return when (direction) {
            0 -> Vec3i(1, 0, 0)
            1 -> Vec3i(0, 0, 1)
            2 -> Vec3i(-1, 0, 0)
            3 -> Vec3i(0, 0, -1)
            else -> Vec3i(0, 0, 0)
        }
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        builder?.add(DIRECTION)
        super.appendProperties(builder)
    }

    override fun getPlacementState(context: ItemPlacementContext?): BlockState =
        defaultState.with(DIRECTION, context?.horizontalPlayerFacing?.horizontal!!)

    companion object {
        @JvmStatic
        private val DIRECTION: IntProperty = IntProperty.of("direction", 0, 3)
    }
}