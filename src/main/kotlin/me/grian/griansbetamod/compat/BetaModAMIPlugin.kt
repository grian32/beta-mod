package me.grian.griansbetamod.compat

import me.grian.griansbetamod.BetaMod
import net.glasslauncher.mods.alwaysmoreitems.api.*
import net.minecraft.nbt.NbtCompound
import net.modificationstation.stationapi.api.util.Identifier

// NOTE: currently unused but will be needed in the future when i add a enhancement table viewer etc
class BetaModAMIPlugin : ModPluginProvider {
    override fun getName(): String {
        return "Grian's Beta Expansion"
    }

    override fun getId(): Identifier {
        return BetaMod.NAMESPACE.id("griansbetamod")
    }

    override fun onAMIHelpersAvailable(amiHelpers: AMIHelpers) {
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