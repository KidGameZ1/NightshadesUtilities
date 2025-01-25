package net.nightshade.nightshade_core.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.nightshade.nightshade_core.size.SizeHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin extends Entity {
    public PlayerMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }



    @Inject(
            method = {"getMyRidingOffset"},
            at = {@At("RETURN")},
            cancellable = true
    )
    public void getMyRidingOffset(CallbackInfoReturnable<Double> cir) {
        Player player =(Player) this.level().getEntity(getId());
        cir.setReturnValue(0.65 - (double) SizeHelper.getSkillSizeMultiplier(player));
    }
}
