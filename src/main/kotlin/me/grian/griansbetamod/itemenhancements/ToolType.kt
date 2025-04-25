package me.grian.griansbetamod.itemenhancements

import net.minecraft.item.AxeItem
import net.minecraft.item.HoeItem
import net.minecraft.item.Item
import net.minecraft.item.PickaxeItem
import net.minecraft.item.ShovelItem
import net.minecraft.item.SwordItem
import net.minecraft.item.ToolItem
import kotlin.reflect.KClass

enum class ToolType(val clazz: Class<*>) {
    SWORD(SwordItem::class.java),
    PICKAXE(PickaxeItem::class.java),
    AXE(AxeItem::class.java),
    SHOVEL(ShovelItem::class.java),
    HOE(HoeItem::class.java);
}