package me.grian.griansbetamod.compat

import me.grian.griansbetamod.BetaMod
import me.grian.griansbetamod.compat.itemenhancements.EnhancementRecipeCategory
import me.grian.griansbetamod.compat.itemenhancements.EnhancementRecipeHandler
import me.grian.griansbetamod.itemenhancements.Enhancement
import me.grian.griansbetamod.itemenhancements.recipe.EnhancementRecipeManager
import net.glasslauncher.mods.alwaysmoreitems.api.*
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.modificationstation.stationapi.api.util.Identifier

class BetaModAMIPlugin : ModPluginProvider {
    override fun getName(): String {
        return "Project Beta Expanded"
    }

    override fun getId(): Identifier {
        return BetaMod.NAMESPACE.id("griansbetamod")
    }

    override fun onAMIHelpersAvailable(amiHelpers: AMIHelpers) {
        // this is a strange way to do it but it causes issues sometimes due to uninitialized depending on if its ran in dev/prod etc :S
        if (BetaMod.blocksInitialized) {
            amiHelpers.itemBlacklist.addItemToBlacklist(
                ItemStack(BetaMod.frostRootCrop)
            )
            amiHelpers.itemBlacklist.addItemToBlacklist(
                ItemStack(BetaMod.shrineCenter)
            )
            amiHelpers.itemBlacklist.addItemToBlacklist(
                ItemStack(BetaMod.goldStone)
            )
        }
    }

    override fun onItemRegistryAvailable(itemRegistry: ItemRegistry) {
    }

    override fun register(modRegistry: ModRegistry) {
        modRegistry.addRecipeCategories(EnhancementRecipeCategory())
        modRegistry.addRecipeHandlers(EnhancementRecipeHandler())
        modRegistry.addRecipes(EnhancementRecipeManager.recipes.filter {
            it.enhancement != Enhancement.NONE && it.enhancement != Enhancement.__LAPIS_MINER
        })
    }

    override fun onRecipeRegistryAvailable(recipeRegistry: RecipeRegistry) {
    }

    override fun deserializeRecipe(nbtCompound: NbtCompound): SyncableRecipe? {
        return null;
    }
}
