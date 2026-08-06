package me.grian.griansbetamod.hell.limbo

import me.grian.griansbetamod.BetaMod
import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.ChestBlockEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraft.world.gen.feature.Feature
import java.util.*

class LimboHutFeature : Feature() {
    override fun generate(
        world: World,
        random: Random,
        x: Int,
        y: Int,
        z: Int
    ): Boolean {
        if (random.nextInt(50) != 0) return false

        for (dx in -1..1) {
            for (dz in -1..1) {
                for (dy in 0..2) {
                    if (!world.isAir(x + dx, y + dy, z + dz)) return false
                }

                if (world.isAir(x + dx, y - 1, z + dz)) return false
            }
        }

        world.genPillars(x, y, z)
        world.genPillars(x, y + 1, z)

        for (dx in -1..1) {
            for (dz in -1..1) {
                world.setBlock(x + dx, y + 2, z + dz, BetaMod.deadPlankSlab.id)
            }
        }

        world.genWalls(x, y, z, random.nextInt(4))
        world.destroyBlocks(x, y, z, random)

        println("genned @ $x $y $z")
        return true
    }

    private fun World.genPillars(x: Int, y: Int, z: Int) {
        setBlock(x - 1, y, z - 1, BetaMod.deadLog.id)
        setBlock(x + 1, y, z + 1, BetaMod.deadLog.id)
        setBlock(x - 1, y, z + 1, BetaMod.deadLog.id)
        setBlock(x + 1, y, z - 1, BetaMod.deadLog.id)
    }

    private val WALL_OFFSETS = arrayOf(
        1 to 0,
        0 to 1,
        -1 to 0,
        0 to -1
    )

    private fun World.genWalls(x: Int, y: Int, z: Int, doorDirection: Int) {
        for ((direction, offset) in WALL_OFFSETS.withIndex()) {
            if (direction == doorDirection) continue
            val (dx, dz) = offset
            setBlock(x + dx, y, z + dz, BetaMod.deadPlank.id)
            setBlock(x + dx, y + 1, z + dz, BetaMod.deadPlank.id)
        }

        if (random.nextBoolean()) return

        val chestOffsetX = WALL_OFFSETS[doorDirection].first + 1
        val chestOffsetZ = WALL_OFFSETS[doorDirection].second + 1

        setBlock(x + chestOffsetX, y, z + chestOffsetZ, Block.CHEST.id)
        val blockEntity = getBlockEntity(x + chestOffsetX, y, z + chestOffsetZ)
        fillChest(blockEntity as? ChestBlockEntity ?: return, random)
    }

    private fun fillChest(blockEntity: ChestBlockEntity, random: Random) {
        val compassSlot = random.nextInt(21)

        blockEntity.setStack(compassSlot, ItemStack(Item.COMPASS, 1))

        for (i in 0..21) {
            if (i == compassSlot) continue

            when (random.nextInt(20)) {
                5 -> blockEntity.setStack(i, ItemStack(Item.WOODEN_AXE, 1, 45))
                8 -> blockEntity.setStack(i, ItemStack(Item.WOODEN_PICKAXE, 1, 45))
            }
        }
    }

    private fun World.destroyBlocks(x: Int, y: Int, z: Int, random: Random) {
        var destroyedRoofBlocks = 0
        var destroyedUpperBlocks = 0
        var destroyedBottomBlock = false

        while (!destroyedBottomBlock) {
            val dy = when {
                destroyedRoofBlocks < 5 -> 2
                destroyedUpperBlocks < 2 -> 1
                else -> 0
            }
            val dx = random.nextInt(-1, 2)
            val dz = random.nextInt(-1, 2)

            if (isAir(x + dx, y + dy, z + dz)) continue

            setBlock(x + dx, y + dy, z + dz, 0)

            when (dy) {
                2 -> destroyedRoofBlocks++
                1 -> destroyedUpperBlocks++
                else -> destroyedBottomBlock = true
            }
        }
    }
}
