package net.nightshade.nightshade_core.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class NightshadesPlayerRenderer extends LivingEntityRenderer implements NightshadesIgnoreCancel {
    private final ResourceLocation PLAYER_SKIN;

    public NightshadesPlayerRenderer(EntityRendererProvider.Context context, ResourceLocation skin, EntityModel model) {
        this(context, false, skin);
        this.model = model;
    }

    @Nullable
    protected RenderType getRenderType(LivingEntity p_115322_, boolean p_115323_, boolean p_115324_, boolean p_115325_) {
        ResourceLocation resourcelocation = this.getTextureLocation((Entity)p_115322_);
        return RenderType.entityTranslucentCull(resourcelocation);
    }

    public NightshadesPlayerRenderer(EntityRendererProvider.Context context, boolean useSmallArms, ResourceLocation skin) {
        super(context, new PigModel(context.bakeLayer(ModelLayers.PIG)), 0.5F);
        this.PLAYER_SKIN = skin;
    }

    public ResourceLocation getTextureLocation(Entity entity) {
        return this.PLAYER_SKIN;
    }

    public void render(AbstractClientPlayer entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        try {
            super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        } catch (Exception var8) {
            matrixStackIn.popPose();
        }

    }

    public ResourceLocation getTextureLocation(AbstractClientPlayer entity) {
        return this.PLAYER_SKIN;
    }
}
