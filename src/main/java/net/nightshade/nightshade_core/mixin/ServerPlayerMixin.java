package net.nightshade.nightshade_core.mixin;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin implements AccessorServerPlayer{
    @Override
    public void setRespawnDimension(ResourceKey<Level> dimension) {

    }
}
