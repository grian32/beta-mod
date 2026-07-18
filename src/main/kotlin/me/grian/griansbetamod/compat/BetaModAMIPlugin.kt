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
        // this is a strange way to do it but it causes issues sometimes due to uninitialized depending on if its ran in dev/prod etc :S
        if (BetaMod.frostRootCropInit) {
            amiHelpers.itemBlacklist.addItemToBlacklist(
                ItemStack(BetaMod.frostRootCrop)
            )
        }
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