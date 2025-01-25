package net.nightshade.nightshade_core.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.nightshade.nightshade_core.size.SizeHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {

    @Inject(method = "scale*", at = @At("HEAD"))
    public void scale(LivingEntity pLivingEntity, PoseStack pPoseStack, float pPartialTickTime, CallbackInfo ci){
        if (pLivingEntity instanceof Player player){
        }
        float size = SizeHelper.getSize(pLivingEntity);
        pPoseStack.scale(size, size, size);

    }

}
