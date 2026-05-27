package me.grian.griansbetamod

import net.fabricmc.loader.api.FabricLoader
import net.minecraft.block.MapColor
import net.minecraft.block.material.Material
import java.lang.reflect.Field

object Materials {
    val SAWMILL = Material(MapColor.BROWN).disableHandHarvesting()

    private fun Material.disableHandHarvesting(): Material {
        var field: Field
        if (FabricLoader.getInstance().isDevelopmentEnvironment) {
            field = Material::class.java.getDeclaredField("handHarvestable")
        } else {
            field = Material::class.java.getDeclaredField("field_977")
        }
        field.isAccessible = true
        field.set(this, false)
        return this
    }
}

