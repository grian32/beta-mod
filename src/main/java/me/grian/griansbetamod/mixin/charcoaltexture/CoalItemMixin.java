package me.grian.griansbetamod.mixin.charcoaltexture;
import me.grian.griansbetamod.TextureListener;
import net.minecraft.item.CoalItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
    
@Mixin(CoalItem.class)
public class CoalItemMixin extends Item {

    public CoalItemMixin(int id) {
        super(id);
    }

    @Override
    public int getTextureId(int damage) {
        if (damage == 1) return TextureListener.INSTANCE.getCharcoalTexture();

        return super.getTextureId(damage);
    }
}