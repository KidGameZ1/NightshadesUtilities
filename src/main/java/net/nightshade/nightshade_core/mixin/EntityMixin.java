package net.nightshade.nightshade_core.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.nightshade.nightshade_core.attributes.NightshadeAttributeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Entity.class})
public abstract class EntityMixin {
    public EntityMixin() {
    }

    @Shadow
    public abstract InteractionResult interact(Player var1, InteractionHand var2);

    @Shadow private Level level;

    @Shadow private int id;

    @Shadow public abstract boolean isFree(double p_20230_, double p_20231_, double p_20232_);

    @Inject(
            method = {"getBoundingBoxForPose"},
            at = {@At("RETURN")},
            cancellable = true
    )
    public void getBoundingBoxForPose(Pose pPose, CallbackInfoReturnable<AABB> cir) {
        Entity entity = this.level.getEntity(id);
        if (entity instanceof Player player) {
            EntityDimensions size = entity.getDimensions(pPose);
            boolean shouldChange = false;

            if (player.getAttribute(NightshadeAttributeRegistry.SIZE.get()).getValue() != 1){
                size = size.scale((float) player.getAttribute(NightshadeAttributeRegistry.SIZE.get()).getValue());
                shouldChange = true;
            }

            if (!shouldChange) {
                return;
            }

            float f = size.width / 2.0F;
            Vec3 vec3 = new Vec3(entity.getX() - (double)f, entity.getY(), entity.getZ() - (double)f);
            Vec3 vec31 = new Vec3(entity.getX() + (double)f, entity.getY() + (double)size.height, entity.getZ() + (double)f);
            cir.setReturnValue(new AABB(vec3, vec31));
        }

    }


//    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
//    private void move(MoverType pType, Vec3 pPos, CallbackInfo ci){
//        Entity entity = this.level.getEntity(id);
//        if (entity instanceof Player player) {
//            if (pType.equals(SELF)) {
//                if(player instanceof LocalPlayer localPlayer && localPlayer.isMoving()){
//                    if(ClanHelper.getClan(player) != null){
//                        if (player.isSprinting()){
//                            player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(ClanHelper.getClan(player).getSprintSpeed());
//                        }
//                        else{
//                            player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(ClanHelper.getClan(player).getMovementSpeed());
//
//                        }
//
//                    }
//                }
//            }
//        }
//    }
}
