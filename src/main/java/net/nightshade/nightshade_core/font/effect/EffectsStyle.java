package net.nightshade.nightshade_core.font.effect;

import javax.swing.text.Style;
import java.util.List;

public interface EffectsStyle {

    void addEffect(GlyphEffect effect);
    List<GlyphEffect> getEffects();

    static EffectsStyle of(Style style) {
        return (EffectsStyle) style;
    }
}