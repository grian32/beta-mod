package me.grian.griansbetamod.itemenhancements

import me.grian.griansbetamod.BetaMod
import me.grian.griansbetamod.api.craftingrecipes.addEnhancementRecipe
import me.grian.griansbetamod.config.ConfigScreen
import net.fabricmc.api.ModInitializer
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

object EnhancementRecipeListener : ModInitializer {
    override fun onInitialize() {
        if (ConfigScreen.config.enhancementSystem) {
            fun extraLogs(recipeIngredients: ItemStack, tier: Int) =
                addEnhancementRecipe {
                    toolType = ToolType.AXE
                    ingredients = recipeIngredients
                    enhancement = Enhancement.EXTRA_LOGS
                    enhancementTier = tier
                }

            extraLogs(ItemStack(Block.LOG, 48, 0), 1)
            extraLogs(ItemStack(BetaMod.pileOfLogs, 16), 2)
            extraLogs(ItemStack(BetaMod.pileOfLogs, 32), 3)
            extraLogs(ItemStack(BetaMod.pileOfLogs, 64), 4)

            fun resin(recipeIngredients: ItemStack, tier: Int) =
                addEnhancementRecipe {
                    toolType = ToolType.AXE
                    ingredients = recipeIngredients
                    enhancement = Enhancement.RESIN
                    enhancementTier = tier
                }

            resin(ItemStack(Block.LOG, 64, 1), 1)
            resin(ItemStack(BetaMod.resin, 48), 2)
            resin(ItemStack(BetaMod.resinBlock, 32), 3)

            fun reinforced(recipeIngredients: ItemStack, tier: Int) =
                addEnhancementRecipe {
                    toolType = ToolType.AXE
                    ingredients = recipeIngredients
                    enhancement = Enhancement.AXE_UNBREAKING
                    enhancementTier = tier
                }

            reinforced(ItemStack(Item.IRON_INGOT, 24), 1)
            reinforced(ItemStack(Item.IRON_INGOT, 64), 2)
            reinforced(ItemStack(Block.IRON_BLOCK, 10), 3)
            reinforced(ItemStack(Block.IRON_BLOCK, 20), 4)

            fun lapisMiner(recipeIngredients: ItemStack, tier: Int) =
                addEnhancementRecipe {
                    toolType = ToolType.PICKAXE
                    ingredients = recipeIngredients
                    enhancement = Enhancement.LAPIS_MINER
                    enhancementTier = tier
                }

            lapisMiner(ItemStack(Block.STONE, 64), 1)
            lapisMiner(ItemStack(Item.DYE, 64, 4), 2) // DMG 4 = LAPIS THANK YOU NOTCH
            lapisMiner(ItemStack(Block.LAPIS_BLOCK, 64), 3)

            fun steadyHand(recipeIngredients: ItemStack, tier: Int) =
                addEnhancementRecipe {
                    toolType = ToolType.PICKAXE
                    ingredients = recipeIngredients
                    enhancement = Enhancement.STEADY_HAND
                    enhancementTier = tier
                }

            steadyHand(ItemStack(Item.IRON_INGOT, 32), 1)
            steadyHand(ItemStack(Item.GOLD_INGOT, 64), 2)
            steadyHand(ItemStack(Item.DIAMOND, 24), 3)

            addEnhancementRecipe {
                toolType = ToolType.HOE
                ingredients = ItemStack(Item.BREAD, 48)
                enhancement = Enhancement.REPLANTER
                enhancementTier = 1
            }
        }
    }
}