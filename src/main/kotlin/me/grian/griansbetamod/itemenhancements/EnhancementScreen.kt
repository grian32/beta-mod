package me.grian.griansbetamod.itemenhancements

import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.world.World
import org.lwjgl.opengl.GL11

class EnhancementScreen (
    inventory: PlayerInventory, world: World, x: Int, y: Int, z: Int
) : HandledScreen(EnhancementScreenHandler(inventory, world, x, y, z)) {
    override fun removed() {
        super.removed()
        container.onClosed(minecraft.player)
    }

    override fun drawForeground() {
        textRenderer.draw("Enhancements", 55, 6, TEXT_COLOR)
        textRenderer.draw("Inventory", 8, backgroundHeight - 96 + 2, TEXT_COLOR)
    }

    override fun drawBackground(tickDelta: Float) {
        val texture = minecraft.textureManager.getTextureId("/assets/griansbetamod/stationapi/textures/gui/enhancement.png")

        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f)

        minecraft.textureManager.bindTexture(texture)

        val width = (width - backgroundWidth) / 2
        val height = (height - backgroundHeight) / 2
        drawTexture(width, height, 0, 0, backgroundWidth, backgroundHeight)
    }

    companion object {
        private const val TEXT_COLOR = 4210752;
    }
}