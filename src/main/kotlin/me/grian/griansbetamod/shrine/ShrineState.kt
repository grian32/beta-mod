package me.grian.griansbetamod.shrine

import net.minecraft.nbt.NbtCompound
import net.minecraft.world.PersistentState
import net.minecraft.world.World

class ShrineState(id: String): PersistentState(id) {
    var shrineActivated: Boolean = false
        private set

    fun activateShrine() {
        if (shrineActivated) return

        shrineActivated = true
        markDirty()
    }

    fun setShrineState(v: Boolean) {
        shrineActivated = v
        markDirty()
    }

    override fun readNbt(nbt: NbtCompound) {
        shrineActivated = nbt.getBoolean("ShrineActivated")
    }

    override fun writeNbt(nbt: NbtCompound) {
        nbt.putBoolean("ShrineActivated", shrineActivated)
    }

    companion object {
        private const val ID = "griansbetamod_moon_state"

        @JvmStatic
        fun get(world: World): ShrineState {
            val manager = world.persistentStateManager
            val loaded = manager.getOrCreate(
                ShrineState::class.java,
                ID
            ) as ShrineState?

            if (loaded != null) {
                return loaded
            }

            return ShrineState(ID).also {
                manager.set(ID, it)
            }
        }
    }
}