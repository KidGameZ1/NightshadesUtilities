package net.nightshade.nightshade_core.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.kosmx.playerAnim.api.TransformType;
import dev.kosmx.playerAnim.core.util.Vec3f;
import dev.kosmx.playerAnim.impl.IAnimatedPlayer;
import dev.kosmx.playerAnim.impl.animation.AnimationApplier;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class NightshadesPlayerAnimatedRenderer extends LivingEntityRenderer<AbstractClientPlayer, EntityModel<AbstractClientPlayer>> implements NightshadesIgnoreCancel {
    private final ResourceLocation PLAYER_SKIN;

    public NightshadesPlayerAnimatedRenderer(EntityRendererProvider.Context context, ResourceLocation skin, EntityModel model) {
        super(context, new PigModel(context.bakeLayer(ModelLayers.PIG)), 0.5F);
        this.PLAYER_SKIN = skin;
        this.model = model;
    }

    @Nullable
    protected RenderType getRenderType(AbstractClientPlayer p_115322_, boolean p_115323_, boolean p_115324_, boolean p_115325_) {
        ResourceLocation resourcelocation = this.getTextureLocation(p_115322_);
        return RenderType.entityTranslucentCull(resourcelocation);
    }

    public ResourceLocation getTextureLocation(AbstractClientPlayer entity) {
        return this.PLAYER_SKIN;
    }

    public void render(AbstractClientPlayer entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        try {
            super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        } catch (Exception var8) {
            matrixStackIn.popPose();
        }

    }

    public Vec3 getRenderOffset(AbstractClientPlayer p_117785_, float p_117786_) {
        return p_117785_.isCrouching() ? new Vec3(0.0, -0.125, 0.0) : super.getRenderOffset(p_117785_, p_117786_);
    }

    protected void setupRotations(AbstractClientPlayer p_117802_, PoseStack p_117803_, float p_117804_, float p_117805_, float p_117806_) {
        float f = p_117802_.getSwimAmount(p_117806_);
        float f3;
        float f2;
        if (p_117802_.isFallFlying()) {
            super.setupRotations(p_117802_, p_117803_, p_117804_, p_117805_, p_117806_);
            f3 = (float)p_117802_.getFallFlyingTicks() + p_117806_;
            f2 = Mth.clamp(f3 * f3 / 100.0F, 0.0F, 1.0F);
            if (!p_117802_.isAutoSpinAttack()) {
                p_117803_.mulPose(Axis.XP.rotationDegrees(f2 * (-90.0F - p_117802_.getXRot())));
            }

            Vec3 vec3 = p_117802_.getViewVector(p_117806_);
            Vec3 vec31 = p_117802_.getDeltaMovementLerped(p_117806_);
            double d0 = vec31.horizontalDistanceSqr();
            double d1 = vec3.horizontalDistanceSqr();
            if (d0 > 0.0 && d1 > 0.0) {
                double d2 = (vec31.x * vec3.x + vec31.z * vec3.z) / Math.sqrt(d0 * d1);
                double d3 = vec31.x * vec3.z - vec31.z * vec3.x;
                p_117803_.mulPose(Axis.YP.rotation((float)(Math.signum(d3) * Math.acos(d2))));
            }
        } else if (f > 0.0F) {
            super.setupRotations(p_117802_, p_117803_, p_117804_, p_117805_, p_117806_);
            f3 = !p_117802_.isInWater() && !p_117802_.isInFluidType((fluidType, height) -> {
                return p_117802_.canSwimInFluidType(fluidType);
            }) ? -90.0F : -90.0F - p_117802_.getXRot();
            f2 = Mth.lerp(f, 0.0F, f3);
            p_117803_.mulPose(Axis.XP.rotationDegrees(f2));
            if (p_117802_.isVisuallySwimming()) {
                p_117803_.translate(0.0F, -1.0F, 0.3F);
            }
        } else {
            super.setupRotations(p_117802_, p_117803_, p_117804_, p_117805_, p_117806_);
        }

        if (ModList.get().isLoaded("playeranimator")) {
            AnimationApplier animationPlayer = ((IAnimatedPlayer)p_117802_).playerAnimator_getAnimation();
            animationPlayer.setTickDelta(p_117806_);
            if (animationPlayer.isActive()) {
                Vec3f vec3d = animationPlayer.get3DTransform("body", TransformType.POSITION, Vec3f.ZERO);
                p_117803_.translate((double)(Float)vec3d.getX(), (double)(Float)vec3d.getY() + 0.7, (double)(Float)vec3d.getZ());
                Vec3f vec3f = animationPlayer.get3DTransform("body", TransformType.ROTATION, Vec3f.ZERO);
                p_117803_.mulPose(Axis.ZP.rotation((Float)vec3f.getZ()));
                p_117803_.mulPose(Axis.YP.rotation((Float)vec3f.getY()));
                p_117803_.mulPose(Axis.XP.rotation((Float)vec3f.getX()));
                p_117803_.translate(0.0, -0.7, 0.0);
            }
        }

    }
}
