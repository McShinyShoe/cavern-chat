package net.shinyshoe.cavernChat.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.shinyshoe.cavernChat.CavernChat;
import net.shinyshoe.cavernChat.client.util.ColorUtils;
import net.shinyshoe.cavernChat.mixin.client.ChatHudAccessor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ChatVisibilityController {
    static private final List<CategorizedChat> categorizedChatList = new ArrayList<>();
    private ChatVisibilityController() {}

    public static void chatClear() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.inGameHud == null) return;

        ChatHud chatHud = client.inGameHud.getChatHud();
        ChatHudAccessor acc = (ChatHudAccessor) chatHud;

        acc.getVisibleMessages().clear();
    }

    public static void chatRebuild() {
        CavernChat.LOGGER.info("Rebuilding chat...");

        chatClear();
        for(CategorizedChat categorizedChat : categorizedChatList) {
            if(ChatType.enabled.get(categorizedChat.type)) {
                printChat(categorizedChat.type, categorizedChat.message);
            }
        }
    }

    public static void chatAdd(ChatHudLine message) {
        Text content = message.content();

        for (ChatFilter filter : ChatFilter.FILTERS.values()) {
            if(!filter.getStatus()) continue;
            if(filter.checkText(content)) {
                categorizedChatList.add(new CategorizedChat(filter.type(), message));
                printChat(filter.type(), message);
            }
        }
    }

    public static int getDmColor(String name) {
        List<DirectMessageColor> colorList = CavernChatConfig.getInstance().colorfulDirectMessages.colorfulDirectMessageColors;
        if (colorList.isEmpty()) {
            int color = Color.HSBtoRGB(0f, 0.65f, 0.85f) & 0xFFFFFF;
            CavernChatConfig.getInstance().colorfulDirectMessages.colorfulDirectMessageColors.add(new DirectMessageColor(name, color));
            return color;
        }

        List<Float> hues = new ArrayList<>();
        for (DirectMessageColor dmColor : colorList) {
            if(Objects.equals(dmColor.player, name)) return dmColor.color;
            float[] hsv = ColorUtils.rgbToHsv(dmColor.color);
            hues.add(hsv[0]);
        }

        hues.sort(Float::compare);

        float bestGap = -1;
        float bestHue = 0;

        for (int i = 0; i < hues.size(); i++) {
            float h1 = hues.get(i);
            float h2 = hues.get((i + 1) % hues.size());

            float gap = (i == hues.size() - 1)
                    ? (360f - h1 + h2)
                    : (h2 - h1);

            if (gap > bestGap) {
                bestGap = gap;
                bestHue = (h1 + gap / 2f) % 360f;
            }
        }

        float saturation = 0.60f;
        float value      = 0.85f;

        int color = Color.HSBtoRGB(bestHue / 360f, saturation, value) & 0xFFFFFF;
        CavernChatConfig.getInstance().colorfulDirectMessages.colorfulDirectMessageColors.add(new DirectMessageColor(name, color));
        return color;
    }

    private static final Pattern DM_PATTERN = Pattern.compile("✉ \\[MSG\\] (you|\\w+) → (you|\\w+)");
    public static void printChat(ChatType type, ChatHudLine line) {
        MinecraftClient client = MinecraftClient.getInstance();
        ChatHud chatHud = client.inGameHud.getChatHud();
        ChatHudAccessor acc = (ChatHudAccessor) chatHud;
        switch (type) {
            case MESSAGE_DM -> {
                if(!CavernChatConfig.getInstance().colorfulDirectMessages.colorfulDirectMessageEnabled) {
                    acc.invokeAddVisibleMessage(line);
                    return;
                }

                String str = line.content().getString();
                Matcher m = DM_PATTERN.matcher(str);
                if (!m.find()) {
                    acc.invokeAddVisibleMessage(line);
                    return;
                }

                String left  = m.group(1);
                String right = m.group(2);

                String player = "";
                if ("you".equals(left))  player = right;
                if ("you".equals(right)) player = left;

                int dmColor = getDmColor(player);

                MutableText newText = Text.empty();
                AtomicBoolean active = new AtomicBoolean(false);

                line.content().copy().visit((style, content) -> {
                    Style newStyle = style;

                    TextColor color = style.getColor();

                    if (!active.get() && color != null && color.getRgb() == 0xEEC65D) {
                        active.set(true);
                    }

                    if (active.get()) {
                        if (color != null && color.getRgb() != 0xEEC65D) {
                            active.set(false);
                        } else {
                            newStyle = style.withColor(TextColor.fromRgb(dmColor));
                        }
                    }

                    newText.append(Text.literal(content).setStyle(newStyle));
                    return Optional.empty();
                }, Style.EMPTY);

                CavernChat.LOGGER.info(line.toString());

                ChatHudLine newLine = new ChatHudLine(line.creationTick(), newText, line.signature(), line.indicator());
                acc.invokeAddVisibleMessage(newLine);
            }
            default -> acc.invokeAddVisibleMessage(line);
        }
    }

    public static void chatReset() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.inGameHud == null) return;

        ChatHud chatHud = client.inGameHud.getChatHud();
        chatHud.reset();
    }
}