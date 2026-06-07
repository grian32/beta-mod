package me.grian.griansbetamod.itemenhancements

import com.mojang.datafixers.DSL
import com.mojang.datafixers.DataFixer
import com.mojang.datafixers.schemas.Schema
import com.mojang.serialization.Dynamic
import net.modificationstation.stationapi.api.datafixer.TypeReferences

class OrdinalToIdFixer : DataFixer {
    override fun <T : Any?> update(
        type: DSL.TypeReference?,
        input: Dynamic<T>,
        version: Int,
        newVersion: Int
    ): Dynamic<T> {
        if (version >= newVersion) return input

        if (type == TypeReferences.CHUNK) {
            return fixChunk(input)
        } else if (type == TypeReferences.PLAYER) {
            return fixPlayer(input)
        }

        return input
    }

    private fun <T> fixPlayer(input: Dynamic<T>): Dynamic<T> {
        return input.update("Inventory") {
            it.createList(
                it.asStream().map(::fixItemStack)
            )
        }
    }

    private fun <T> fixChunk(input: Dynamic<T>): Dynamic<T> {
        return input.update("Level") { level ->
            level
                .update("Entities") { entities ->
                    entities.createList(entities.asStream().map { entity ->
                        entity
                            .update("Item") { item ->
                                fixItemStack(item)
                            }
                            .update("Items") { items ->
                                items.createList(items.asStream().map(::fixItemStack))
                            }
                })
                .update("TileEntities") { blockEntities ->
                    blockEntities.createList(
                        blockEntities.asStream().map { blockEntity ->
                            blockEntity.update("Items") { items ->
                                items.createList(items.asStream().map(::fixItemStack))
                            }
                        }
                    )
                }
            }
        }
    }

    private fun <T> fixItemStack(input: Dynamic<T>): Dynamic<T> {
        return input.update("stationapi:item_nbt") { stationNbt ->
            if (stationNbt.get("enhancement").result().isPresent) {
                return@update stationNbt.update("enhancement") { value ->
                    value.createInt(value.asInt(0) - 1)
                }
            }

            return@update stationNbt
        }
    }

    override fun getSchema(key: Int): Schema? = null
}