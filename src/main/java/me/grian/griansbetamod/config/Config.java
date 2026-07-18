package me.grian.griansbetamod.config;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

public class Config {
    @ConfigEntry(name = "Modify version string", requiresRestart = true)
    public Boolean modifyVersionString = true;
}
