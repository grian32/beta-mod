package me.grian.griansbetamod.mixin.stackcrafting;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipeManager;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.modificationstation.stationapi.impl.recipe.StationShapedRecipe;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;

@Debug(export = true)
@Mixin(CraftingResultSlot.class)
public class CraftingResultSlotMixin {

    @Shadow
    @Final
    private Inventory input;

    @Inject(
            method = "onTakeItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;increaseStat(Lnet/minecraft/stat/Stat;I)V",
                    shift = At.Shift.BY,
                    by = 2,
                    ordinal = 7
            )
    )
    public void onTakeItem(ItemStack par1, CallbackInfo ci) {
        var recipes = CraftingRecipeManager.getInstance().getRecipes();
        StationShapedRecipe foundRecipe = null;
        for (Object recipe : recipes) {
            if (recipe instanceof StationShapedRecipe && ((StationShapedRecipe) recipe).matches((CraftingInventory) input)) {
                foundRecipe = (StationShapedRecipe) recipe;
                break;
            }
        }

        if (foundRecipe == null) {
            return;
        }

        /**
         * TODO: normalize inputs for 2x2 recipes, cuz f.e the recipe for 2x2 will be diff from the placement in the input & loop below will fail :(
         */

        List<ItemStack> foundRecipeGrid = Arrays.stream(foundRecipe.getGrid()).toList().stream().map(either -> {
            if (either == null) return null;
            // if its not null then itl always be this due to how it's mapped out
            @SuppressWarnings("OptionalGetWithoutIsPresent") ItemStack stack = either.right().get();
            return stack;
        }).toList();

        for (int var2 = 0; var2 <= this.input.size() - 1; var2++) {
            ItemStack inputStack = this.input.getStack(var2);
            ItemStack recipeStack = foundRecipeGrid.get(var2);

            if (inputStack != null && recipeStack != null) {
                this.input.removeStack(var2, recipeStack.count);
                if (inputStack.getItem().hasCraftingReturnItem()) {
                    this.input.setStack(var2, new ItemStack(inputStack.getItem().getCraftingReturnItem()));
                }
            }
        }
    }
}
