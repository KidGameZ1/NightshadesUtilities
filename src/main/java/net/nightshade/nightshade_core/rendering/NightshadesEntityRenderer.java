package net.nightshade.nightshade_core.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class NightshadesEntityRenderer extends MobRenderer implements NightshadesIgnoreCancel {
    private final ResourceLocation PLAYER_SKIN;

    public NightshadesEntityRenderer(EntityRendererProvider.Context context, ResourceLocation skin, EntityModel model) {
        this(context, skin);
        this.model = model;
    }

    @Nullable
    protected RenderType getRenderType(LivingEntity p_115322_, boolean p_115323_, boolean p_115324_, boolean p_115325_) {
        ResourceLocation resourcelocation = this.getTextureLocation((Entity)p_115322_);
        if (p_115324_) {
            return RenderType.itemEntityTranslucentCull(resourcelocation);
        } else if (p_115323_) {
            return RenderType.entityTranslucentCull(resourcelocation);
        } else {
            return p_115325_ ? RenderType.outline(resourcelocation) : null;
        }
    }

    public boolean shouldRender(Mob livingEntityIn, Frustum camera, double camX, double camY, double camZ) {
        if (super.shouldRender(livingEntityIn, camera, camX, camY, camZ)) {
            return true;
        } else {
            Entity entity = livingEntityIn.getLeashHolder();
            return false;
        }
    }

    public NightshadesEntityRenderer(EntityRendererProvider.Context context, ResourceLocation skin) {
        super(context, new PigModel(context.bakeLayer(ModelLayers.PIG)), 0.5F);
        this.PLAYER_SKIN = skin;
    }

    public ResourceLocation getTextureLocation(Entity entity) {
        return this.PLAYER_SKIN;
    }

    public void render(Mob entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        try {
            super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        } catch (Exception var8) {
            matrixStackIn.popPose();
        }

    }

    public ResourceLocation getTextureLocation(Mob entity) {
        return this.PLAYER_SKIN;
    }
}
