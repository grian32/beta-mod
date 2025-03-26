package me.grian.griansbetamod.config;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

public class Config {
    @ConfigEntry(name = "Lapis gives Speed Boost", description = "Requires a restart to take effect & feature only currently works client-side")
    public Boolean lapisSpeedBoost = true;

    @ConfigEntry(name = "Players wearing Grassy Boots no longer trample crops.", description = "Requires a restart to take effect, grassy boots are also included in this config.")
    public Boolean leatherBootsTrampleCrops = true;

    @ConfigEntry(name = "Enable Redstone Blocks", description = "Requires a restart to take effect.")
    public Boolean enableRedstoneBlock = true;

    @ConfigEntry(name = "Allows crafting saddles into leather.", description = "Requires a restart to take effect.")
    public Boolean decraftSaddles = true;

    @ConfigEntry(name = "Taiga biomes now generate icy stone", description = "Requires a restart to take effect. WARNING: IF THIS OPTION IS CHANGED AND A EXISTING WORLD IS LOADED IT WILL ONLY AFFECT NEWLY GENERATED CHUNKS.")
    public Boolean icyStone = true;

    @ConfigEntry(name = "Enable Sawmill Block", description = "Requires a restart to take effect.")
    public Boolean sawmillBlock = true;
}
