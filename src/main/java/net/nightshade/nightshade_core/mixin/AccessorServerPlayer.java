package net.nightshade.nightshade_core.mixin;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({ServerPlayer.class})
public interface AccessorServerPlayer {

    @Accessor
    void setRespawnDimension(ResourceKey<Level> dimension);
}
