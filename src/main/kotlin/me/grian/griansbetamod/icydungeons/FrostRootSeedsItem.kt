package me.grian.griansbetamod.icydungeons

import me.grian.griansbetamod.BetaMod
import net.minecraft.block.Block
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.modificationstation.stationapi.api.template.item.TemplateItem
import net.modificationstation.stationapi.api.util.Identifier

class FrostRootSeedsItem(identifier: Identifier) : TemplateItem(identifier) {
    override fun useOnBlock(
        stack: ItemStack,
        user: PlayerEntity,
        world: World,
        x: Int,
        y: Int,
        z: Int,
        side: Int
    ): Boolean {
        // ref SeedsItem
        if (side != 1)  {
            return false
        } else {
            val blockId = world.getBlockId(x, y, z)
            if (blockId == Block.FARMLAND.id && world.isAir(x, y+1, z)) {
                world.setBlock(x, y+1, z, BetaMod.frostRootCrop.id)
                --stack.count
                return true;
            }
            return false;
        }
    }
}