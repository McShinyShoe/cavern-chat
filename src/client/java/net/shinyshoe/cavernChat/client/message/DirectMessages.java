package net.shinyshoe.cavernChat.client.message;

//? if >=26.1 {
/*import net.minecraft.client.multiplayer.chat.GuiMessage;
*///?} else {
import net.minecraft.client.GuiMessage;
//?}
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.shinyshoe.cavernChat.client.config.CavernChatConfig;
import net.shinyshoe.cavernChat.client.config.DirectMessageColor;
import net.shinyshoe.cavernChat.client.util.ColorUtils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller for DirectMessages, used for Colorfuler Direct Message feaature
 */
public final class DirectMessages {

    private static final Pattern DM_PATTERN = Pattern
            .compile("✉ \\[MSG\\] (you|[A-Za-z0-9_*]+) → (you|[A-Za-z0-9_*]+)");

    /** The yellow the server uses for the player names inside a DM. */
    private static final int SERVER_NAME_COLOR = 0xEEC65D;

    private static final float SATURATION = 0.60f;
    private static final float VALUE = 0.85f;
    private static final float FIRST_HUE = 0f;
    private static final float FIRST_SATURATION = 0.65f;

    private DirectMessages() {
    }

    /**
     * Recolor the partner's name in their assigned colour and notes who it was.
     */
    public static GuiMessage recolor(GuiMessage line) {
        String selfName = Minecraft.getInstance().getUser().getName();
        String player = otherParty(line.content().getString(), selfName);

        CavernChatConfig.getInstance().lastDMPerson = player;

        int dmColor = colorFor(player);

        MutableComponent recolored = Component.empty();
        AtomicBoolean inName = new AtomicBoolean(false);

        line.content().copy().visit((style, content) -> {
            Style newStyle = style;
            TextColor color = style.getColor();

            if (!inName.get() && color != null && color.getValue() == SERVER_NAME_COLOR) {
                inName.set(true);
            }

            if (inName.get()) {
                if (color != null && color.getValue() != SERVER_NAME_COLOR) {
                    inName.set(false);
                } else {
                    newStyle = style.withColor(TextColor.fromRgb(dmColor));
                }
            }

            recolored.append(Component.literal(content).setStyle(newStyle));
            return Optional.empty();
        }, Style.EMPTY);

        //? if >=26.1 {
        /*return new GuiMessage(line.addedTime(), recolored, line.signature(), line.source(), line.tag());
        *///?} else {
        return new GuiMessage(line.addedTime(), recolored, line.signature(), line.tag());
        //?}
    }

    /**
     * The colour for a partner, assigning a unique one the first time we see them.
     */
    public static int colorFor(String name) {
        List<DirectMessageColor> assigned = CavernChatConfig
                .getInstance().colorfulDirectMessages.colorfulDirectMessageColors;

        if (assigned.isEmpty())
            return assign(name, Color.HSBtoRGB(FIRST_HUE, FIRST_SATURATION, VALUE) & 0xFFFFFF);

        List<Float> hues = new ArrayList<>();
        for (DirectMessageColor dmColor : assigned) {
            if (Objects.equals(dmColor.player, name))
                return dmColor.color;
            hues.add(ColorUtils.rgbToHsv(dmColor.color)[0]);
        }

        return assign(name, Color.HSBtoRGB(widestHueGap(hues) / 360f, SATURATION, VALUE) & 0xFFFFFF);
    }

    /**
     * Finds the midpoint of the largest unused stretch of the hue wheel.
     * TLDR, finds the most diffrent color that is diffrent than any picked color.
     */
    private static float widestHueGap(List<Float> hues) {
        hues.sort(Float::compare);

        float bestGap = -1;
        float bestHue = 0;

        for (int i = 0; i < hues.size(); i++) {
            float current = hues.get(i);
            float next = hues.get((i + 1) % hues.size());

            float gap = (i == hues.size() - 1)
                    ? (360f - current + next)
                    : (next - current);

            if (gap > bestGap) {
                bestGap = gap;
                bestHue = (current + gap / 2f) % 360f;
            }
        }

        return bestHue;
    }

    private static int assign(String name, int color) {
        CavernChatConfig.getInstance().colorfulDirectMessages.colorfulDirectMessageColors
                .add(new DirectMessageColor(name, color));
        return color;
    }

    /** Pulls the other person out of a DM line, or {@code null} if it isn't one. */
    public static String otherParty(String line, String playerName) {
        Matcher matcher = DM_PATTERN.matcher(line);
        if (!matcher.find())
            return null;

        String left = matcher.group(1);
        String right = matcher.group(2);

        String other;
        if ("you".equalsIgnoreCase(left)) {
            other = right;
        } else if ("you".equalsIgnoreCase(right)) {
            other = left;
        } else if (left.equalsIgnoreCase(playerName)) {
            other = right;
        } else if (right.equalsIgnoreCase(playerName)) {
            other = left;
        } else {
            other = left;
        }

        return other.equalsIgnoreCase(playerName) ? "self" : other;
    }
}
