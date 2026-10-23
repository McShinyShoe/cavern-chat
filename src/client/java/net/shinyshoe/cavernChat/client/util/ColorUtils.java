package net.shinyshoe.cavernChat.client.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextColor;

import java.awt.Color;

public final class ColorUtils {
    private ColorUtils() {
    }

    public static float[] rgbToHsv(int rgb) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        float[] hsv = new float[3];
        Color.RGBtoHSB(r, g, b, hsv);

        hsv[0] *= 360f;
        return hsv;
    }

    /** ChatFormatting lost getColor() in 26.2 :I TextColor still knows it on every version. */
    public static int rgbOf(ChatFormatting formatting) {
        return TextColor.fromLegacyFormat(formatting).getValue();
    }
}
