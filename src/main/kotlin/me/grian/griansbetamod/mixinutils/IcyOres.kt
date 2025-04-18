package me.grian.griansbetamod.mixinutils

import me.grian.griansbetamod.BetaMod
import net.minecraft.block.Block
import net.minecraft.world.biome.Biome

/**
 * surprisingly enough this is required due to java not recognizing the blocks that mc initializes as constant in its
 * switch statements :(
 */
fun convertOresToIcy(id: Int, biome: Biome): Int {
    if (!(biome === Biome.TAIGA || biome === Biome.TUNDRA)) return id

    return when (id) {
        Block.COAL_ORE.id -> BetaMod.icyCoalOre.id
        Block.IRON_ORE.id -> BetaMod.icyIronOre.id
        Block.GOLD_ORE.id -> BetaMod.icyGoldOre.id
        Block.REDSTONE_ORE.id -> BetaMod.icyRedstoneOre.id
        Block.DIAMOND_ORE.id -> BetaMod.icyDiamondOre.id
        Block.LAPIS_ORE.id -> BetaMod.icyLapisLazuliOre.id
        else -> id
    }
}