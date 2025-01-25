package net.nightshade.nightshade_core.mixin;

import net.minecraft.client.renderer.entity.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({EntityRenderer.class})
public interface AccessorEntityRenderer {
    @Accessor
    float getShadowRadius();

    @Accessor
    void setShadowRadius(float var1);
}
