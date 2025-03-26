package me.grian.griansbetamod

import me.grian.griansbetamod.Materials.SAWMILL
import net.minecraft.block.MapColor
import net.minecraft.block.material.Material

object Materials {
    val SAWMILL = Material(MapColor.BROWN).disableHandHarvesting()

    private fun Material.disableHandHarvesting(): Material {
        val field = Material::class.java.getDeclaredField("handHarvestable")
        field.isAccessible = true
        field.set(this, false)
        return this
    }
}

