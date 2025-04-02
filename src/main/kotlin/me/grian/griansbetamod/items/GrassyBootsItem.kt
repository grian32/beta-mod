package me.grian.griansbetamod.items

import me.grian.griansbetamod.BetaMod
import net.minecraft.item.ArmorItem
import net.modificationstation.stationapi.api.client.item.ArmorTextureProvider
import net.modificationstation.stationapi.api.template.item.TemplateArmorItem;
import net.modificationstation.stationapi.api.util.Identifier

// LEATHER_BOOTS = (new ArmorItem(45, 0, 0, 3)).setTexturePosition(0, 3).setTranslationKey("bootsCloth");
class GrassyBootsItem(identifier: Identifier) : TemplateArmorItem(identifier, 0, 0, 3), ArmorTextureProvider {
    override fun getTexture(p0: ArmorItem?): Identifier {
        return BetaMod.NAMESPACE.id("grassy_boots_layer")
    }
}