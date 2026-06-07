package me.grian.griansbetamod.util

import com.mojang.datafixers.DataFix
import com.mojang.datafixers.TypeRewriteRule
import com.mojang.datafixers.schemas.Schema
import com.mojang.serialization.Dynamic
import net.modificationstation.stationapi.api.datafixer.TypeReferences

class SetDefaultBlockStateFix<PropT>(originalSchema: Schema, val blocks: Set<String>, val property: String, val value: PropT) : DataFix(originalSchema, false ){
    override fun makeRule(): TypeRewriteRule? {
        val blockStateType = outputSchema.getType(TypeReferences.BLOCK_STATE)

        return writeFixAndRead(
            "SetDefaultBlockState:$property",
            blockStateType,
            blockStateType,
            ::fixBlockState
            )
    }

    private fun fixBlockState(state: Dynamic<*>): Dynamic<*> =
        fixBlockStateTyped(state)

    private fun <T> fixBlockStateTyped(state: Dynamic<T>): Dynamic<T> {
        if (state.get("Name").asString("") !in blocks) return state

        val properties: Dynamic<T> =
            state.get("Properties").result().orElseGet { state.emptyMap() }
        val placed = properties.get(property).result()
        if (placed.isPresent) return state

        return state.set("Properties", properties.set(property, state.createString(value.toString())))
    }
}
