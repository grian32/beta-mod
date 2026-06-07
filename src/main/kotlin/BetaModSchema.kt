import com.mojang.datafixers.DSL
import com.mojang.datafixers.schemas.Schema
import com.mojang.datafixers.types.templates.TypeTemplate
import net.modificationstation.stationapi.api.datafixer.TypeReferences
import net.modificationstation.stationapi.api.vanillafix.datafixer.schema.IdentifierNormalizingSchema
import net.modificationstation.stationapi.api.vanillafix.datafixer.schema.McRegionSchemaB1_7_3
import java.util.function.Supplier
import javax.xml.validation.Validator
import javax.xml.validation.ValidatorHandler

class BetaModSchema(versionKey: Int, parent: Schema?) : McRegionSchemaB1_7_3(versionKey, parent) {
    override fun registerTypes(
        schema: Schema,
        entityTypes: Map<String?, Supplier<TypeTemplate?>?>?,
        blockEntityTypes: Map<String?, Supplier<TypeTemplate?>?>?
    ) {
        super.registerTypes(schema, entityTypes, blockEntityTypes)

        schema.registerType(false, TypeReferences.ITEM_NAME) {
            DSL.constType(IdentifierNormalizingSchema.getIdentifierType())
        }

        schema.registerType(true, TypeReferences.ITEM_STACK) {
            DSL.optionalFields("stationapi:id", TypeReferences.ITEM_NAME.`in`(schema))
        }

        schema.registerType(
            false,
            TypeReferences.BLOCK_STATE,
            DSL::remainder
        )

        schema.registerType(
            false,
            TypeReferences.CHUNK
        ) {
            DSL.fields(
                "Level",
                DSL.optionalFields(
                    "Entities",
                    DSL.list(TypeReferences.ENTITY.`in`(schema)),
                    "TileEntities",
                    DSL.list(TypeReferences.BLOCK_ENTITY.`in`(schema)),
                    "stationapi:sections",
                    DSL.list(
                        DSL.optionalFields(
                            "block_states",
                            DSL.optionalFields(
                                "palette",
                                DSL.list(TypeReferences.BLOCK_STATE.`in`(schema))
                            )
                        )
                    )
                )
            )
        }
    }
}