package me.grian.griansbetamod.compat

import me.grian.griansbetamod.BetaMod
import net.glasslauncher.mods.alwaysmoreitems.api.*
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.modificationstation.stationapi.api.util.Identifier

class BetaModAMIPlugin : ModPluginProvider {
    override fun getName(): String {
        return "Grian's Beta Expansion"
    }

    override fun getId(): Identifier {
        return BetaMod.NAMESPACE.id("griansbetamod")
    }

    override fun onAMIHelpersAvailable(amiHelpers: AMIHelpers) {
        amiHelpers.itemBlacklist.addItemToBlacklist(
            ItemStack(BetaMod.litIcyRedstoneOre)
        )
    }

    override fun onItemRegistryAvailable(itemRegistry: ItemRegistry?) {

    }

    override fun register(modRegistry: ModRegistry?) {

    }

    override fun onRecipeRegistryAvailable(recipeRegistry: RecipeRegistry?) {

    }

    override fun deserializeRecipe(nbtCompound: NbtCompound): SyncableRecipe? {
        return null;
    }
}