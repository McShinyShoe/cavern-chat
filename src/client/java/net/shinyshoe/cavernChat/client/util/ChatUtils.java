package net.shinyshoe.cavernChat.client.util;

import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.Optional;

public final class ChatUtils {
    private ChatUtils() {}

    public static boolean checkChar(Text text, char c, Integer color) {
        return text.visit((style, segment) -> {
            if (segment != null && !segment.isEmpty() && segment.indexOf(c) >= 0) {
                var charColor = style.getColor();
                assert color != null;
                if (charColor != null && charColor.getRgb() == color) {
                    return Optional.of(true);
                }
            }
            return Optional.empty();
        }, Style.EMPTY).orElse(false);
    }
}
