package net.nightshade.nightshade_core.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.nightshade.nightshade_core.NightshadeCoreMod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class RenderEvent{

    @SubscribeEvent
    public static void NightshadesRenderEvent(RenderLivingEvent event) {
        render(event, event.getEntity());
    }

    public static void render(@Nullable Event event, Entity entity) {
        if (entity == null)
            return;
        RenderLivingEvent _evt = (RenderLivingEvent) event;
        Minecraft mc = Minecraft.getInstance();
        EntityRenderDispatcher dis = Minecraft.getInstance().getEntityRenderDispatcher();
        EntityRendererProvider.Context context = new EntityRendererProvider.Context(dis, mc.getItemRenderer(), mc.getBlockRenderer(), dis.getItemInHandRenderer(), mc.getResourceManager(), mc.getEntityModels(), mc.font);
        Entity _evtEntity = _evt.getEntity();
        PlayerRenderer _pr = null;
        PoseStack poseStack = _evt.getPoseStack();
        if (_evt.getRenderer() instanceof PlayerRenderer && !(_evt.getRenderer() instanceof NightshadesIgnoreCancel)) {
            ResourceLocation _texture = new ResourceLocation(NightshadeCoreMod.MODID +":textures/entities/empty.png");
            NightshadesSkinRenderer emptyRenderer = new NightshadesSkinRenderer(context,
                    (_evtEntity instanceof AbstractClientPlayer && ((AbstractClientPlayer) _evtEntity).getModelName().equals("slim")), _texture);
            _pr = emptyRenderer;
            emptyRenderer.clearLayers();
            emptyRenderer.render((AbstractClientPlayer) _evt.getEntity(), _evt.getEntity().getYRot(), _evt.getPartialTick(), _evt.getPoseStack(), _evt.getMultiBufferSource(), _evt.getPackedLight());
            if (!entity.isInvisible()){
                addRender(_evt,context,poseStack,_pr);
            }
            addAlwaysRender(_evt, context, poseStack, _pr);
        }
    }

    /* To create a new render event copy this class and add to which ever layer you want */

    public static void addAlwaysRender(RenderLivingEvent _evt, EntityRendererProvider.Context context, PoseStack poseStack, PlayerRenderer playerRenderer) {
    }

    public static void addRender(RenderLivingEvent _evt, EntityRendererProvider.Context context, PoseStack poseStack, PlayerRenderer playerRenderer) {
    }
}
