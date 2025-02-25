package me.grian.griansbetamod.blocks

import me.grian.griansbetamod.TextureListener
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.block.material.Material
import net.minecraft.world.BlockView
import net.modificationstation.stationapi.api.template.block.TemplateBlock
import net.modificationstation.stationapi.api.util.Identifier

class RedstoneBlock(identifier: Identifier) : TemplateBlock(identifier, Material.METAL) {
    /**
     * All the textures are the same, so I just return the same thing.
     */
    @Environment(EnvType.CLIENT)
    override fun getTextureId(blockView: BlockView?, x: Int, y: Int, z: Int, side: Int): Int {
        // TODO: possible bad practice, getTexture/init block didn't work though due to get before init
        this.textureId = TextureListener.redstoneBlockTexture
        return TextureListener.redstoneBlockTexture
    }

//    @Environment(EnvType.CLIENT)
//    override fun getTexture(side: Int): Int {
//        return this.textureId
//    }
//
//    @Environment(EnvType.CLIENT)
//    override fun getTexture(side: Int, meta: Int): Int {
//        return this.textureId
//    }
}