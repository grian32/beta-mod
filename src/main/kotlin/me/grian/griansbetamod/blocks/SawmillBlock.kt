package me.grian.griansbetamod.blocks

import me.grian.griansbetamod.Materials
import me.grian.griansbetamod.TextureListener
import net.minecraft.block.Block
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.modificationstation.stationapi.api.template.block.TemplateBlock
import net.modificationstation.stationapi.api.util.Identifier


class SawmillBlock(identifier: Identifier) : TemplateBlock(identifier, Materials.SAWMILL) {
    override fun getTextureId(blockView: BlockView?, x: Int, y: Int, z: Int, side: Int): Int = getTextureGeneral(side)

    override fun getTexture(side: Int): Int = getTextureGeneral(side)

    private fun getTextureGeneral(side: Int): Int {
        return when (side) {
            1 -> TextureListener.sawmillTop // top
            2 -> TextureListener.sawmillLeft // left
            else -> TextureListener.sawmillSide
        }
    }

    override fun onUse(world: World?, x: Int, y: Int, z: Int, player: PlayerEntity?): Boolean {
        val referenceBlockId = world!!.getBlockId(x, y, z - 1)
        val referenceBlockMeta = world.getBlockMeta(x, y, z - 1)

        var referenceBlock: Block? = null

        when {
            referenceBlockId == 53 -> referenceBlock = WOODEN_STAIRS
            referenceBlockId == 44 && referenceBlockMeta == 2 -> referenceBlock = SLAB
        }

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

                    // most it can ever be is 2 stacks cuz of the ratio
                    if (finalAmount > 64) {
                        player.inventory.main[slot] = ItemStack(SLAB, 64, 2)

                        player.inventory.addStack(ItemStack(SLAB, finalAmount - 64, 2))
                    } else {
                        player.inventory.main[slot] = ItemStack(SLAB, finalAmount, 2)
                    }

                    world.playSound(x.toDouble() + 0.5, y.toDouble() + 0.5, z.toDouble() + 0.5, "step.wood", 1.0f, 3.5f)

                    return true
                }
            }

        }

        return false
    }
}