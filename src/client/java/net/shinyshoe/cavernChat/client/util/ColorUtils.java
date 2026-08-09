package net.shinyshoe.cavernChat.client.util;

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
}
