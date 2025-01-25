package net.nightshade.nightshade_core.util;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.nightshade.nightshade_core.mixin.AccessorServerPlayer;

public class ServerPlayerHelper {
    public static void setRespawnDimension(Player player, ResourceKey<Level> dimension){
        if (player instanceof ServerPlayer serverPlayer){
            if (serverPlayer instanceof AccessorServerPlayer accessorServerPlayer){
                accessorServerPlayer.setRespawnDimension(dimension);
            }
        }
    }
}
