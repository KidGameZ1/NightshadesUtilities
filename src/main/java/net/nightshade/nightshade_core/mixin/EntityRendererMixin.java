package net.nightshade.nightshade_core.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.nightshade.nightshade_core.size.SizeHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin implements AccessorEntityRenderer{
    @Shadow
    protected float shadowRadius;

    @Override
    public float getShadowRadius() {
        return this.shadowRadius;
    }

    @Override
    public void setShadowRadius(float var1) {
        shadowRadius = var1;
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(Entity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, CallbackInfo ci){
        if (pEntity instanceof Player){
            float size = SizeHelper.getSkillSizeMultiplier((LivingEntity) pEntity);
            setShadowRadius(size/1.7f);
        }
    }
}