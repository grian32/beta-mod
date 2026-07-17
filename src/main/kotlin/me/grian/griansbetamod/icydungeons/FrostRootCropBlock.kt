package me.grian.griansbetamod.icydungeons

import me.grian.griansbetamod.BetaMod
import me.grian.griansbetamod.TextureListener
import net.minecraft.entity.ItemEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraft.world.biome.Biome
import net.modificationstation.stationapi.api.block.BlockState
import net.modificationstation.stationapi.api.template.block.TemplatePlantBlock
import net.modificationstation.stationapi.api.util.Identifier
import java.util.*

// tex will get a variable in texturelistener then used here,
class FrostRootCropBlock(identifier: Identifier) : TemplatePlantBlock(identifier, 0) {
    init {
        setTickRandomly(true)
        setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F)
    }

    override fun canPlantOnTop(id: Int): Boolean {
        return id == FARMLAND.id
    }

    override fun onTick(world: World, x: Int, y: Int, z: Int, random: Random) {
        super.onTick(world, x, y, z, random)

        val biome = world.method_1781().getBiome(x, z)
        if (biome != Biome.TAIGA && biome != Biome.TUNDRA) return

        if (world.getLightLevel(x, y+1, z) >= 9) {
            val meta = world.getBlockMeta(x, y, z)
            if (meta < 3) {
                val moisture = this.getAvailableMoisture(world, x, y, z)
                if (random.nextInt((100.0F/moisture).toInt()) == 0) {
                    world.setBlockMeta(x, y, z, meta+1)
                }
            }
        }
    }

    // copied 1:1 from cropblock
    fun getAvailableMoisture(world: World, x: Int, y: Int, z: Int): Float {
        var var5 = 1.0f
        val var6 = world.getBlockId(x, y, z - 1)
        val var7 = world.getBlockId(x, y, z + 1)
        val var8 = world.getBlockId(x - 1, y, z)
        val var9 = world.getBlockId(x + 1, y, z)
        val var10 = world.getBlockId(x - 1, y, z - 1)
        val var11 = world.getBlockId(x + 1, y, z - 1)
        val var12 = world.getBlockId(x + 1, y, z + 1)
        val var13 = world.getBlockId(x - 1, y, z + 1)
        val var14 = var8 == BetaMod.frostRootCrop.id || var9 == BetaMod.frostRootCrop.id
        val var15 = var6 == BetaMod.frostRootCrop.id || var7 == BetaMod.frostRootCrop.id
        val var16 = var10 == BetaMod.frostRootCrop.id || var11 == BetaMod.frostRootCrop.id || var12 == BetaMod.frostRootCrop.id || var13 == BetaMod.frostRootCrop.id

        for (var17 in x - 1..x + 1) {
            for (var18 in z - 1..z + 1) {
                val var19 = world.getBlockId(var17, y - 1, var18)
                var var20 = 0.0f
                if (var19 == FARMLAND.id) {
                    var20 = 1.0f
                    if (world.getBlockMeta(var17, y - 1, var18) > 0) {
                        var20 = 3.0f
                    }
                }

                if (var17 != x || var18 != z) {
                    var20 /= 4.0f
                }

                var5 += var20
            }
        }

        if (var16 || var14 && var15) {
            var5 /= 2.0f
        }

        return var5
    }

    override fun getTexture(side: Int, meta: Int): Int {
        var usedMeta = meta
        if (meta < 0) {
            usedMeta = 3
        }
        return TextureListener.frostRootCropTextures[usedMeta]
    }

    override fun getRenderType(): Int {
        return 6
    }

    override fun dropStacks(world: World, x: Int, y: Int, z: Int, meta: Int, luck: Float) {
        // also copied 1:1 from cropblock
        super.dropStacks(world, x, y, z, meta, luck)
        if (!world.isRemote) {
            for (var7 in 0..2) {
                if (world.random.nextInt(15) <= meta) {
                    val var8 = 0.7f
                    val var9 = world.random.nextFloat() * var8 + (1.0f - var8) * 0.5f
                    val var10 = world.random.nextFloat() * var8 + (1.0f - var8) * 0.5f
                    val var11 = world.random.nextFloat() * var8 + (1.0f - var8) * 0.5f
                    val var12 = ItemEntity(
                        world,
                        (x.toFloat() + var9).toDouble(),
                        (y.toFloat() + var10).toDouble(),
                        (z.toFloat() + var11).toDouble(),
                        ItemStack(
                            BetaMod.frostRootSeeds
                        )
                    )
                    var12.pickupDelay = 10
                    world.spawnEntity(var12)
                }
            }
        }
    }

    override fun getDroppedItemId(blockMeta: Int, random: Random?): Int {
        return if (blockMeta == 3) {
            BetaMod.frostRoot.id
        } else {
            -1
        }
    }

    override fun getDroppedItemCount(random: Random): Int {
        val rand = random.nextInt(3)
        return if (rand != 0) {
            rand
        } else {
            1
        }
    }

    override fun onBonemealUse(world: World, x: Int, y: Int, z: Int, state: BlockState): Boolean {
        val meta = world.getBlockMeta(x, y, z)
        if (meta >= 3) return false
        val biome = world.method_1781().getBiome(x, z)
        if (biome != Biome.TAIGA && biome != Biome.TUNDRA) return false

        if (!world.isRemote) {
            world.setBlockMeta(x, y, z, meta+1)
        }

        return true
    }
}