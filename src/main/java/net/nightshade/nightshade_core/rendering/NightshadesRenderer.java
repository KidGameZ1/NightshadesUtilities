package net.nightshade.nightshade_core.rendering;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent;

import java.util.concurrent.atomic.AtomicReference;

public class NightshadesRenderer implements NightshadesIgnoreCancel {
    public static void renderOnPlayerSkin(RenderLivingEvent _evt, EntityRendererProvider.Context context,String modid,String textureName){
        if (_evt.getRenderer() instanceof PlayerRenderer && !(_evt.getRenderer() instanceof NightshadesIgnoreCancel)) {
            ResourceLocation _texture = new ResourceLocation(modid+":textures/entities/default.png");
            if (ResourceLocation.tryParse(modid+":textures/entities/"+textureName) != null) {
                _texture = new ResourceLocation(modid+":textures/entities/"+textureName+".png");
            }
            new NightshadesSkinRenderer(context, false, _texture).render((AbstractClientPlayer) _evt.getEntity(), _evt.getEntity().getYRot(), _evt.getPartialTick(), _evt.getPoseStack(), _evt.getMultiBufferSource(),
                    _evt.getPackedLight());
        }
    }

    public static void renderOnPlayerSkinWithGlow(RenderLivingEvent _evt, EntityRendererProvider.Context context,String modid,String textureName){
        if (_evt.getRenderer() instanceof PlayerRenderer && !(_evt.getRenderer() instanceof NightshadesIgnoreCancel)) {
            ResourceLocation _texture = new ResourceLocation(modid+":textures/entities/default.png");
            if (ResourceLocation.tryParse(modid+":textures/entities/"+textureName) != null) {
                _texture = new ResourceLocation(modid+":textures/entities/"+textureName+".png");
            }
            new NightshadesSkinRenderer(context, false, _texture).render((AbstractClientPlayer) _evt.getEntity(), _evt.getEntity().getYRot(), _evt.getPartialTick(), _evt.getPoseStack(), _evt.getMultiBufferSource(),
                    0x0000CD);
        }
    }

    public static void hidePlayerModel(RenderLivingEvent _evt){
        if (_evt.getRenderer() instanceof LivingEntityRenderer && !(_evt.getRenderer() instanceof NightshadesIgnoreCancel)) {
            if (_evt instanceof RenderLivingEvent.Pre _pre) {
                _pre.setCanceled(true);
            }
        }
    }

    public static void renderAnimatedTextureOnPlayerSkin(RenderLivingEvent _evt, EntityRendererProvider.Context context,String modid,String textureName, int frames, int ticksBetween){
        if (_evt.getRenderer() instanceof PlayerRenderer && !(_evt.getRenderer() instanceof NightshadesIgnoreCancel)) {
            AtomicReference<ResourceLocation> _texture = new AtomicReference<>(new ResourceLocation(modid + ":textures/entities/default.png"));
            for (int index0 = 0; index0 < (frames); index0++){
                if (index0 == 0){
                    if (ResourceLocation.tryParse(modid+":textures/entities/"+textureName+"_1") != null) {
                        _texture.set(new ResourceLocation(modid + ":textures/entities/" + textureName + "_1" + ".png"));
                    }

                }else {
                    int ticks = ticksBetween;
                    while (ticks > 0) {
                        if (ResourceLocation.tryParse(modid + ":textures/entities/" + textureName + "_"+ index0) != null) {
                            _texture.set(new ResourceLocation(modid + ":textures/entities/" + textureName + "_" + index0 + ".png"));
                        }
                        ticks = ticks - 1;
                    }

                }
                new NightshadesSkinRenderer(context, false, _texture.get()).render((AbstractClientPlayer) _evt.getEntity(), _evt.getEntity().getYRot(), _evt.getPartialTick(), _evt.getPoseStack(), _evt.getMultiBufferSource(),
                        _evt.getPackedLight());
            }


        }
    }
}
