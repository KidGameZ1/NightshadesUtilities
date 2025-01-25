package net.nightshade.nightshade_core.mixin;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.nightshade.nightshade_core.attributes.ISizeAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Attribute.class)
public class AttributeMixin implements ISizeAttribute {
    @Shadow
    public double defaultValue;

    @Override
    public void setDefaultValue(double var1) {
        this.defaultValue = var1;
    }

    @Override
    public double getDefaultValue() {
        return this.defaultValue;
    }
}
