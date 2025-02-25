package me.grian.griansbetamod.config;

import net.glasslauncher.mods.gcapi3.api.ConfigRoot;

public class ConfigScreen {
    @ConfigRoot(value = "config", visibleName = "Grian's Beta Mod Config")
    public static final Config config = new Config();

}
