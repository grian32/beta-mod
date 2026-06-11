package me.grian.griansbetamod.itemenhancements

import com.mojang.datafixers.DataFix
import com.mojang.datafixers.TypeRewriteRule
import com.mojang.datafixers.schemas.Schema
import com.mojang.serialization.Dynamic
import net.modificationstation.stationapi.api.datafixer.TypeReferences

class RemoveLapisMinerFix(outputSchema: Schema) : DataFix(outputSchema, false) {
    override fun makeRule(): TypeRewriteRule? {
        val itemStackType = outputSchema.getType(TypeReferences.ITEM_STACK)

        return writeFixAndRead(
            "EnhancementRemoveLapisMinerFix",
            itemStackType,
            itemStackType,
            ::fixItemStack)
    }

    private fun fixItemStack(input: Dynamic<*>): Dynamic<*> {
        return input.update("stationapi:item_nbt") { stationNbt ->
            if (
                stationNbt.get("enhancement").result().isPresent &&
                stationNbt.get("enhancement").result().get().asInt(-1) == Enhancement.__LAPIS_MINER.id
                )
            {
                return@update stationNbt.update("enhancement") { value ->
                    value.createInt(-1)
                }.update("enhancementTier") { value ->
                    value.createInt(0)
                }

            }

            return@update stationNbt
        }

    }
}