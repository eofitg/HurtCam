package net.eofitg.hurtcam.mixin;

import net.eofitg.hurtcam.HurtCam;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@SideOnly(Side.CLIENT)
@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    @Redirect(method = "hurtCameraEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V"))
    public void injectNoHurtCam(float angle, float x, float y, float z) {
        if (HurtCam.config.isEnabled()) {
            angle = angle / 14 * HurtCam.config.getMultiplier();
        }
        GlStateManager.rotate(angle, x, y, z);
    }

}
