package net.nightshade.nightshade_core.size;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
    modid = "nightshade_core",
    bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class SizeHandler {
    public SizeHandler() {
    }

    @SubscribeEvent
    public static void resizeOnLogin(EntityJoinLevelEvent e) {
        if (!e.getLevel().isClientSide()) {
            Entity var2 = e.getEntity();
            if (var2 instanceof Player) {
                Player player = (Player)var2;
                player.refreshDimensions();
            }
        }
    }


    @SubscribeEvent
    public static void resizeOnPlayerTick(TickEvent.PlayerTickEvent e) {
        if (e.phase == TickEvent.Phase.END){
            Entity var2 = e.player;
            if (var2 instanceof Player) {
                Player player = (Player)var2;
                player.refreshDimensions();
            }

        }

    }

    @SubscribeEvent
    public static void resizeOnSizeChange(EntityEvent.Size e) {
        Entity shouldChange = e.getEntity();
        if (shouldChange instanceof LivingEntity player) {
            boolean var8 = false;
            EntityDimensions size = e.getNewSize();
            float eyeHeight = e.getNewEyeHeight();

//            float height = TensuraEffectsCapability.getHeight(player);
//            if (height != 1.0F && height != 0.0F) {
//                size = size.scale(1.0F, height);
//                eyeHeight *= height;
//                var8 = true;
//            }
//
            float attSize = SizeHelper.getSize(player);

            if (attSize != 1f){
                size = size.scale(attSize);
                eyeHeight *= attSize;
                var8 = true;
            }
//
            if (var8) {
                e.setNewSize(size);
                e.setNewEyeHeight(eyeHeight);
            }

        }
    }
}