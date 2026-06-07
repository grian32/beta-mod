package me.grian.griansbetamod.itemenhancements

import com.mojang.datafixers.DataFix
import com.mojang.datafixers.TypeRewriteRule
import com.mojang.datafixers.schemas.Schema
import com.mojang.serialization.Dynamic
import net.modificationstation.stationapi.api.datafixer.TypeReferences

class OrdinalToIdFix(outputSchema: Schema) : DataFix(outputSchema, false) {
    override fun makeRule(): TypeRewriteRule? {
        val itemStackType = outputSchema.getType(TypeReferences.ITEM_STACK)

        return writeFixAndRead(
            "EnhancementOrdinalToIdFix",
            itemStackType,
            itemStackType,
            ::fixItemStack)
    }

    private fun fixItemStack(input: Dynamic<*>): Dynamic<*> {
        return input.update("stationapi:item_nbt") { stationNbt ->
            if (stationNbt.get("enhancement").result().isPresent) {
                return@update stationNbt.update("enhancement") { value ->
                    value.createInt(value.asInt(0) - 1)
                }
            }

            return@update stationNbt
        }
    }
}