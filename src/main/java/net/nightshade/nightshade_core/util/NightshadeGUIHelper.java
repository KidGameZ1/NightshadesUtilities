package net.nightshade.nightshade_core.util;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public class NightshadeGUIHelper {

    public static void renderScaledShadowText(GuiGraphics poseStack, Font font, FormattedText text, int x, int y, float width, float height, int color, float spacePerLine, float scalingSteps) {
        float scaling = 0.8f;

        while(true) {
            List<FormattedCharSequence> formattedCharSequences = font.split(text, Math.round(width / scaling));
            float var10000 = (float)formattedCharSequences.size();
            Objects.requireNonNull(font);
            if (!(var10000 * (9.0F + spacePerLine) * scaling > height)) {
                renderScaledShadowText(poseStack, font, scaling, font.split(text, Math.round(width / scaling)), x, y, color, spacePerLine);
                return;
            }

            scaling -= scalingSteps;
        }
    }

    public static void renderScaledShadowText(GuiGraphics guiGraphics, Font font, float scaling, List<FormattedCharSequence> text, int x, int y, int color, float spacePerLine) {
        guiGraphics.pose().scale(scaling,scaling,scaling);

        for(FormattedCharSequence charSequence : text) {
            guiGraphics.drawString(font, charSequence, x, y, color, false);
            Objects.requireNonNull(font);
            y += (int) ((9.0F + spacePerLine) * scaling);
        }

        guiGraphics.pose().scale(1.0F / scaling, 1.0F / scaling, 1.0F / scaling);
    }


    public static void renderCenteredXText(Font font, GuiGraphics guiGraphics, Component text, int x, int y, int width, Color color, boolean shadow) {
        int centered = x + (width - font.width(text)) / 2;
        guiGraphics.drawString(font, text.getString(), (float)centered, (float)y, color.getRGB(), shadow);

    }
}
