package net.nightshade.nightshade_core.font.effect;

public abstract class GlyphEffect {

    public GlyphEffect() {
    }

    public abstract void apply(EffectSettings settings);

    public abstract char getKey();
}