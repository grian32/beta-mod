package me.grian.griansbetamod.config;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

public class Config {
    @ConfigEntry(name = "Lapis gives Speed Boost", description = "Requires a restart to take effect & feature only currently works client-side")
    public Boolean lapisSpeedBoost = true;

    @ConfigEntry(name = "Leather Boots no longer trample crops.", description = "Requires a restart to take effect")
    public Boolean leatherBootsTrampleCrops = true;
}
