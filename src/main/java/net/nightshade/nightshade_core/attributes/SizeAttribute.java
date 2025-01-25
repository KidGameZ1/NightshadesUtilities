package net.nightshade.nightshade_core.attributes;

import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class SizeAttribute extends RangedAttribute implements ISizeAttribute {

    public SizeAttribute(String pDescriptionId, double pDefaultValue, double pMin, double pMax) {
        super(pDescriptionId, pDefaultValue, pMin, pMax);
        this.defaultValue = pDefaultValue;
    }

    @Override
    public void setDefaultValue(double var1) {
        this.defaultValue = var1;
    }

    @Override
    public double getDefaultValue() {
        return defaultValue;
    }
}
