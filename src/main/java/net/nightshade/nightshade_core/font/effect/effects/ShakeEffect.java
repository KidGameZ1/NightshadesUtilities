package net.nightshade.nightshade_core.font.effect.effects;

import net.minecraft.Util;
import net.minecraft.util.Mth;
import net.nightshade.nightshade_core.font.effect.EffectSettings;
import net.nightshade.nightshade_core.font.effect.GlyphEffect;

public class ShakeEffect extends GlyphEffect {
    @Override
    public void apply(EffectSettings settings) {
        settings.x += Mth.sin(Util.getMillis() * 0.01F + settings.index) * 2;
    }

    @Override
    public char getKey() {
        return 's';
    }
}