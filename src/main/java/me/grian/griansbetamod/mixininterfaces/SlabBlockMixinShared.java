package me.grian.griansbetamod.mixininterfaces;

import net.modificationstation.stationapi.api.state.property.IntProperty;

public class SlabBlockMixinShared {
    public static final IntProperty WOOL_META = IntProperty.of("wool_meta", 0, 16);

    public static final int WOOD_SLAB_META = 2;
}
