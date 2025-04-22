package me.grian.griansbetamod.items

import me.grian.griansbetamod.config.ConfigScreen
import me.grian.griansbetamod.mixininterfaces.IPlayerEntityMixin
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.modificationstation.stationapi.api.template.item.TemplateItem
import net.modificationstation.stationapi.api.util.Identifier

class SpeedCrystalItem(identifier: Identifier) : TemplateItem(identifier) {
    override fun use(stack: ItemStack?, world: World?, user: PlayerEntity?): ItemStack? {
        if (ConfigScreen.config.lapisSpeedBoost) {
            --stack!!.count
            (user as IPlayerEntityMixin).`beta_mod$setSpeedBoostTicks`(400)
            return stack
        }
        return stack
    }
}