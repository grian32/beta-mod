package me.grian.griansbetamod.mixin.stackcrafting;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipeManager;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.modificationstation.stationapi.impl.recipe.StationShapedRecipe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static me.grian.griansbetamod.mixinutils.StackCraftingKt.*;

@Mixin(CraftingResultSlot.class)
public class CraftingResultSlotMixin {

    @Shadow
    @Final
    private Inventory input;

    // TODO: figure out better inject, have to shift so it takes me out the if
    @Inject(
            method = "onTakeItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;increaseStat(Lnet/minecraft/stat/Stat;I)V",
                    shift = At.Shift.BY,
                    by = 2
            ),
            cancellable = true
    )
    public void onTakeItem(ItemStack par1, CallbackInfo ci) {
        var recipes = CraftingRecipeManager.getInstance().getRecipes();
        StationShapedRecipe foundRecipe = findRecipe(recipes, input);

        if (foundRecipe == null) {
            return;
        }

        List<ItemStack> foundRecipeGrid = stationRecipeToList(foundRecipe);

        foundRecipeGrid = normalizeRecipe(this.input, foundRecipeGrid);

        for (int i = 0; i < this.input.size(); i++) {
            ItemStack inputStack = this.input.getStack(i);
            ItemStack recipeStack = foundRecipeGrid.get(i);

            if (inputStack != null && recipeStack != null) {
                this.input.removeStack(i, recipeStack.count);
                if (inputStack.getItem().hasCraftingReturnItem()) {
                    this.input.setStack(i, new ItemStack(inputStack.getItem().getCraftingReturnItem()));
                }
            }
        }

        // cancels vanilla behaviour, will return early if no station shaped recipe is found so it's ok.
        ci.cancel();
    }
}
