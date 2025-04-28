package me.grian.griansbetamod.mixin.stackcrafting;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipeManager;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.modificationstation.stationapi.impl.recipe.StationShapedRecipe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;
import java.util.stream.Collectors;

@Mixin(CraftingResultSlot.class)
public class CraftingResultSlotMixin {

    @Shadow
    @Final
    private Inventory input;

    // TODO: figure out better inject, have to shift so it takes me out the if :pray:
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
        StationShapedRecipe foundRecipe = null;
        // tried with streams but was dodgy.
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
         * TODO: normalize recipe to have the same layout as input for 2x2 recipes, cuz f.e the recipe for 2x2 will be diff from the placement in the input & loop below will fail :(
         */
        // is 2x3 -> is 2x2
        //        -> is 1x3
        //        -> is 1x2

        List<ItemStack> foundRecipeGrid = Arrays.stream(foundRecipe.getGrid())
                // if its null & u call right then it throws
                .map(it -> it != null ? it.right().orElseThrow() : null)
                .toList();

        if (is2x2(foundRecipeGrid)) {
            foundRecipeGrid = normalize2x2Recipe(this.input, foundRecipeGrid);
        }

        for (int i = 0; i <= this.input.size() - 1; i++) {
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

    @Unique
    private boolean is2x2(List<ItemStack> lst) {
        return lst.size() == 6 &&
            (
                (lst.get(0) == null && lst.get(3) == null) ||
                (lst.get(2) == null && lst.get(5) == null)
            );
    }

    @Unique
    private List<ItemStack> normalize2x2Recipe(Inventory input, List<ItemStack> recipe) {
        ArrayList<ItemStack> normalizedRecipe = new ArrayList<>(Collections.nCopies(input.size(), null));
        Deque<ItemStack> recipeItems = recipe.stream().filter(Objects::nonNull).collect(Collectors.toCollection(ArrayDeque::new));

        for (int i = 0; i <= input.size() - 1; i++) {
            if (input.getStack(i) == null) continue;

            if (input.getStack(i) != null) {
                normalizedRecipe.set(i,     recipeItems.pop());
            }
        }

        return normalizedRecipe;
    }
}
