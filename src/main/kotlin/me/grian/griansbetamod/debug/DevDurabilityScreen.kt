package me.grian.griansbetamod.debug

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.item.ItemStack
import org.lwjgl.input.Keyboard

@Environment(EnvType.CLIENT)
class DevDurabilityScreen(
    private val stack: ItemStack
) : Screen() {
    private lateinit var durabilityField: TextFieldWidget
    private var status: String? = null

    override fun init() {
        Keyboard.enableRepeatEvents(true)

        val fieldX = width / 2 - FIELD_WIDTH / 2
        val fieldY = height / 2 - 16
        durabilityField = TextFieldWidget(
            this,
            textRenderer,
            fieldX,
            fieldY,
            FIELD_WIDTH,
            20,
            remainingDurability().toString()
        ).also {
            it.setMaxLength(10)
            it.setFocused(true)
        }

        buttons.add(
            ButtonWidget(
                APPLY_BUTTON_ID,
                width / 2 - 102,
                fieldY + 34,
                100,
                20,
                "Apply"
            )
        )
        buttons.add(
            ButtonWidget(
                DONE_BUTTON_ID,
                width / 2 + 2,
                fieldY + 34,
                100,
                20,
                "Done"
            )
        )
    }

    override fun removed() {
        Keyboard.enableRepeatEvents(false)
    }

    override fun tick() {
        durabilityField.tick()
    }

    override fun keyPressed(character: Char, keyCode: Int) {
        when (keyCode) {
            Keyboard.KEY_ESCAPE -> {
                super.keyPressed(character, keyCode)
                return
            }

            Keyboard.KEY_RETURN, Keyboard.KEY_NUMPADENTER -> {
                applyDurability()
                return
            }
        }

        durabilityField.keyPressed(character, keyCode)
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, button: Int) {
        super.mouseClicked(mouseX, mouseY, button)
        durabilityField.mouseClicked(mouseX, mouseY, button)
    }

    override fun buttonClicked(button: ButtonWidget) {
        when (button.id) {
            APPLY_BUTTON_ID -> applyDurability()
            DONE_BUTTON_ID -> close()
        }
    }

    override fun render(mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground()

        val fieldY = height / 2 - 16
        val remaining = remainingDurability()
        val max = stack.maxDamage

        drawCenteredTextWithShadow(
            textRenderer,
            "Held Item Durability",
            width / 2,
            fieldY - 48,
            0xFFFFFF
        )
        drawCenteredTextWithShadow(
            textRenderer,
            "Current: $remaining / $max (damage ${stack.damage})",
            width / 2,
            fieldY - 30,
            0xE0E0E0
        )
        drawCenteredTextWithShadow(
            textRenderer,
            "Set remaining durability:",
            width / 2,
            fieldY - 12,
            0xA0A0A0
        )

        durabilityField.render()
        status?.let {
            drawCenteredTextWithShadow(textRenderer, it, width / 2, fieldY + 62, 0xFFFFA0)
        }

        super.render(mouseX, mouseY, delta)
    }

    private fun applyDurability() {
        val requestedDurability = durabilityField.text.toIntOrNull()
        if (requestedDurability == null) {
            status = "Enter a whole number"
            return
        }

        val durability = requestedDurability.coerceIn(0, stack.maxDamage)
        stack.damage = stack.maxDamage - durability
        durabilityField.text = durability.toString()
        status = "Durability set to $durability"
    }

    private fun remainingDurability(): Int {
        return (stack.maxDamage - stack.damage).coerceIn(0, stack.maxDamage)
    }

    private fun close() {
        minecraft.setScreen(null)
        minecraft.lockMouse()
    }

    companion object {
        private const val APPLY_BUTTON_ID = 0
        private const val DONE_BUTTON_ID = 1
        private const val FIELD_WIDTH = 160
    }
}
