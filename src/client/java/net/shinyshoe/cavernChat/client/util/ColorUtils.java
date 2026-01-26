package net.shinyshoe.cavernChat.client.util;

import net.shinyshoe.cavernChat.client.CavernChatConfig;
import net.shinyshoe.cavernChat.client.DirectMessageColor;

import java.util.ArrayList;
import java.util.List;

public class ColorUtils {
    public static float[] rgbToHsv(int rgb) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        float[] hsv = new float[3];
        java.awt.Color.RGBtoHSB(r, g, b, hsv);

        hsv[0] *= 360f;
        return hsv;
    }
}
