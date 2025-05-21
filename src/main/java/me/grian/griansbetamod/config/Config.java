package me.grian.griansbetamod.config;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

public class Config {
    @ConfigEntry(name = "Speed Crystals which give a speed boost", description = "Requires a restart to take effect & feature only currently works client-side")
    public Boolean lapisSpeedBoost = true;

    @ConfigEntry(name = "Players wearing Grassy Boots no longer trample crops.", description = "Requires a restart to take effect, grassy boots are also included in this config.")
    public Boolean leatherBootsTrampleCrops = true;

    @ConfigEntry(name = "Enable Redstone Blocks", description = "Requires a restart to take effect.")
    public Boolean enableRedstoneBlock = true;

    @ConfigEntry(name = "Allows crafting saddles into leather.", description = "Requires a restart to take effect.")
    public Boolean decraftSaddles = true;

    @ConfigEntry(name = "Taiga biomes now generate icy stone", description = " WARNING: WIP, DO NOT USE. Requires a restart to take effect.")
    public Boolean icyStone = false;

    @ConfigEntry(name = "Enable Sawmill Block", description = "Requires a restart to take effect.")
    public Boolean sawmillBlock = true;

    @ConfigEntry(name = "Enable Nether Glass", description = "Requires a restart to take effect.")
    public Boolean netherGlass = true;

    @ConfigEntry(name = "Enable axes breaking several vanilla blocks faster", description = "Requires a restart to take effect. See mod description for which blocks are affected")
    public Boolean axeEffectiveBlocks = true;

    @ConfigEntry(name = "Enable pickaxes breaking several vanilla blocks faster", description = "Requires a restart to take effect. See mod description for which blocks are affected")
    public Boolean pickaxeEffectiveBlocks = true;

    @ConfigEntry(name = "Slabs now break faster with their respective tools based on material.", description = "Requires a restart to take effect.")
    public Boolean slabBreakSpeed = true;

    @ConfigEntry(name = "Stairs now drop themselves", description = "Requires a restart to take effect.")
    public Boolean stairDrop = true;

    @ConfigEntry(name = "Fence collision box matches later versions", description = "Requires a restart to take effect.")
    public Boolean fenceCollisionBox = true;

    @ConfigEntry(name = "Enables Item Enhancements System", description = "Requires a restart to take effect.")
    public Boolean enhancementSystem = true;

    @ConfigEntry(name = "Replace the charcoal texture with an updated one", description = "Requires a restart to take effect.")
    public Boolean charcoalTexture = true;

    @ConfigEntry(name = "Enables the lily of the lake block", description = "Requires a restart to take effect.")
    public Boolean lilyOfTheLake = true;
}
