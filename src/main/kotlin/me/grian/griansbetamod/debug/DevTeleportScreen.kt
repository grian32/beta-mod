package me.grian.griansbetamod.debug

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.entity.player.PlayerEntity
import org.lwjgl.input.Keyboard

@Environment(EnvType.CLIENT)
class DevTeleportScreen(
    private val player: PlayerEntity
) : Screen() {
    private lateinit var xField: TextFieldWidget
    private lateinit var yField: TextFieldWidget
    private lateinit var zField: TextFieldWidget
    private lateinit var fields: List<TextFieldWidget>
    private var focusedField = 0
    private var error: String? = null

    override fun init() {
        Keyboard.enableRepeatEvents(true)

        val fieldX = width / 2 - FIELD_WIDTH / 2
        val firstFieldY = height / 2 - 52

        xField = coordinateField(fieldX, firstFieldY, player.x)
        yField = coordinateField(fieldX, firstFieldY + FIELD_SPACING, player.y)
        zField = coordinateField(fieldX, firstFieldY + FIELD_SPACING * 2, player.z)
        fields = listOf(xField, yField, zField)
        updateFocus()

        buttons.add(
            ButtonWidget(
                TELEPORT_BUTTON_ID,
                width / 2 - 102,
                firstFieldY + 72,
                100,
                20,
                "Teleport"
            )
        )
        buttons.add(
            ButtonWidget(
                CANCEL_BUTTON_ID,
                width / 2 + 2,
                firstFieldY + 72,
                100,
                20,
                "Cancel"
            )
        )
    }

    override fun removed() {
        Keyboard.enableRepeatEvents(false)
    }

    override fun tick() {
        fields.forEach(TextFieldWidget::tick)
    }

    override fun keyPressed(character: Char, keyCode: Int) {
        when (keyCode) {
            Keyboard.KEY_ESCAPE -> {
                super.keyPressed(character, keyCode)
                return
            }

            Keyboard.KEY_RETURN, Keyboard.KEY_NUMPADENTER -> {
                teleport()
                return
            }

            Keyboard.KEY_TAB -> {
                focusedField = (focusedField + 1) % fields.size
                updateFocus()
                return
            }
        }

        fields[focusedField].keyPressed(character, keyCode)
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, button: Int) {
        super.mouseClicked(mouseX, mouseY, button)
        fields.forEachIndexed { index, field ->
            field.mouseClicked(mouseX, mouseY, button)
            if (field.focused) focusedField = index
        }
        updateFocus()
    }

    override fun buttonClicked(button: ButtonWidget) {
        when (button.id) {
            TELEPORT_BUTTON_ID -> teleport()
            CANCEL_BUTTON_ID -> close()
        }
    }

    override fun render(mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground()

        val fieldX = width / 2 - FIELD_WIDTH / 2
        val firstFieldY = height / 2 - 52

        drawCenteredTextWithShadow(textRenderer, "Developer Teleport", width / 2, firstFieldY - 28, 0xFFFFFF)
        drawTextWithShadow(textRenderer, "X", fieldX - 14, firstFieldY + 6, 0xE0E0E0)
        drawTextWithShadow(textRenderer, "Y", fieldX - 14, firstFieldY + FIELD_SPACING + 6, 0xE0E0E0)
        drawTextWithShadow(textRenderer, "Z", fieldX - 14, firstFieldY + FIELD_SPACING * 2 + 6, 0xE0E0E0)

        fields.forEach(TextFieldWidget::render)
        error?.let {
            drawCenteredTextWithShadow(textRenderer, it, width / 2, firstFieldY + 98, 0xFF5555)
        }

        super.render(mouseX, mouseY, delta)
    }

    private fun coordinateField(x: Int, y: Int, coordinate: Double): TextFieldWidget {
        return TextFieldWidget(this, textRenderer, x, y, FIELD_WIDTH, 20, coordinate.toString()).also {
            it.setMaxLength(24)
        }
    }

    private fun updateFocus() {
        fields.forEachIndexed { index, field ->
            field.setFocused(index == focusedField)
        }
    }

    private fun teleport() {
        val x = xField.text.toDoubleOrNull()
        val y = yField.text.toDoubleOrNull()
        val z = zField.text.toDoubleOrNull()

        if (x == null || y == null || z == null || !x.isFinite() || !y.isFinite() || !z.isFinite()) {
            error = "Enter valid X, Y and Z coordinates"
            return
        }

        player.setPositionAndAngles(x, y, z, player.yaw, player.pitch)
        player.velocityX = 0.0
        player.velocityY = 0.0
        player.velocityZ = 0.0
        close()
    }

    private fun close() {
        minecraft.setScreen(null)
        minecraft.lockMouse()
    }

    companion object {
        private const val TELEPORT_BUTTON_ID = 0
        private const val CANCEL_BUTTON_ID = 1
        private const val FIELD_WIDTH = 160
        private const val FIELD_SPACING = 24
    }
}
