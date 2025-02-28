package me.grian.griansbetamod

import net.mine_diver.unsafeevents.listener.EventListener
import net.minecraft.block.Block
import net.minecraft.item.AxeItem
import net.minecraft.item.Item
import net.minecraft.item.ToolMaterial
import net.modificationstation.stationapi.api.event.entity.player.IsPlayerUsingEffectiveToolEvent
import net.modificationstation.stationapi.api.event.entity.player.PlayerStrengthOnBlockEvent
import java.util.function.BooleanSupplier

object MiningListener {
    private var lastBlockId = 0
    private var lastBlockMeta = 0

    @EventListener
    fun isUsingEffectiveTool(event: IsPlayerUsingEffectiveToolEvent) {
        val blockPos = event.blockPos
        val blockId = event.blockView.getBlockId(blockPos.x, blockPos.y, blockPos.z)

        val meta = event.blockView.getBlockMeta(blockPos.x, blockPos.y, blockPos.z)

        if (
            meta == 2 &&
            (blockId == Block.SLAB.id || blockId == Block.DOUBLE_SLAB.id)
        ) {
            event.resultProvider = BooleanSupplier { true }
        } else if (
            meta == 0 &&
            blockId == 0 &&
            lastBlockMeta == 2 &&
            (lastBlockId == Block.SLAB.id || lastBlockId == Block.DOUBLE_SLAB.id)
        ) {
            // https://github.com/DanyGames2014/UniTweaks/blob/e2ea7de88c24deefe5dd52e7fa27225029ef5465/src/main/java/net/danygames2014/unitweaks/bugfixes/slabminingfix/MiningListener.java#L41
            // I assume since it's not registered as an effective tool on the last tick then it doesn't drop, this
            // fixed it.
            event.resultProvider = BooleanSupplier { true }
        }

        lastBlockMeta = meta
        lastBlockId = blockId

    }

    @EventListener
    fun playerStrengthOnBlock(event: PlayerStrengthOnBlockEvent) {
        if (event.player.inventory.selectedItem == null) return

        val tool = event.player.inventory.selectedItem.item

        val blockPos = event.blockPos
        val blockId = event.blockView.getBlockId(blockPos.x, blockPos.y, blockPos.z)

        val meta = event.blockView.getBlockMeta(blockPos.x, blockPos.y, blockPos.z)

        // tool material since everything related to mining speed is private or protected :SS
        var toolMaterialMultiplier = 1.0f

        if (tool is AxeItem) {
            toolMaterialMultiplier = when (tool.id) {
                Item.WOODEN_AXE.id -> ToolMaterial.WOOD.miningSpeedMultiplier
                Item.STONE_AXE.id -> ToolMaterial.STONE.miningSpeedMultiplier
                Item.IRON_AXE.id -> ToolMaterial.IRON.miningSpeedMultiplier
                Item.GOLDEN_AXE.id -> ToolMaterial.GOLD.miningSpeedMultiplier
                Item.DIAMOND_AXE.id -> ToolMaterial.DIAMOND.miningSpeedMultiplier
                else -> 1.0f
            }
        }

        if (
            meta == 2 &&
            (blockId == Block.SLAB.id || blockId == Block.DOUBLE_SLAB.id)
        ) {
            event.resultProvider = PlayerStrengthOnBlockEvent.ResultProvider { toolMaterialMultiplier }
        }
    }
}