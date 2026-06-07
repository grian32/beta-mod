package me.grian.griansbetamod.config;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

public class Config {
    @ConfigEntry(name = "Speed Crystals which give a speed boost", requiresRestart = true)
    public Boolean lapisSpeedBoost = true;

    @ConfigEntry(name = "Players wearing Grassy Boots no longer trample crops.", requiresRestart = true)
    public Boolean leatherBootsTrampleCrops = true;

    @ConfigEntry(name = "Enable Redstone Blocks", requiresRestart = true)
    public Boolean enableRedstoneBlock = true;

    @ConfigEntry(name = "Allows crafting saddles into leather.", requiresRestart = true)
    public Boolean decraftSaddles = true;

    @ConfigEntry(name = "Enable Sawmill Block", requiresRestart = true)
    public Boolean sawmillBlock = true;

    @ConfigEntry(name = "Enable Nether Glass", requiresRestart = true)
    public Boolean netherGlass = true;

    @ConfigEntry(name = "Enable axes breaking several vanilla blocks faster", description = "See mod description for which blocks are affected", requiresRestart = true)
    public Boolean axeEffectiveBlocks = true;

    @ConfigEntry(name = "Enable pickaxes breaking several vanilla blocks faster", description = "See mod description for which blocks are affected", requiresRestart = true)
    public Boolean pickaxeEffectiveBlocks = true;

    @ConfigEntry(name = "Slabs now break faster with their respective tools based on material.", requiresRestart = true)
    public Boolean slabBreakSpeed = true;

    @ConfigEntry(name = "Stairs now drop themselves", requiresRestart = true)
    public Boolean stairDrop = true;

    @ConfigEntry(name = "Enable carpeted wooden stairs and slabs", requiresRestart = true)
    public Boolean carpetedStairsAndSlabs = true;

    @ConfigEntry(name = "Fence collision box matches later versions", requiresRestart = true)
    public Boolean fenceCollisionBox = true;

    @ConfigEntry(name = "Enables Item Enhancements System", requiresRestart = true)
    public Boolean enhancementSystem = true;

    @ConfigEntry(name = "Replace the charcoal texture with an updated one", requiresRestart = true)
    public Boolean charcoalTexture = true;

    @ConfigEntry(name = "Enables the lily of the lake block", requiresRestart = true)
    public Boolean lilyOfTheLake = true;

    @ConfigEntry(name = "Climate dependent saplings", requiresRestart = true)
    public Boolean climate = true;

    @ConfigEntry(name = "Modify version string", requiresRestart = true)
    public boolean modifyVersionString = true;
}
