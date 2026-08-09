package net.shinyshoe.cavernChat.client.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.text.Text;
import net.shinyshoe.cavernChat.client.channel.ChannelRegistry;
import net.shinyshoe.cavernChat.client.channel.ChannelTracker;
import net.shinyshoe.cavernChat.client.channel.ChatChannel;
import net.shinyshoe.cavernChat.client.config.CavernChatConfig;
import net.shinyshoe.cavernChat.client.message.DirectMessages;
import net.shinyshoe.cavernChat.mixin.client.accessor.ChatScreenAccessor;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The tag on the left of the chat input to indicate which channel are
 * currently active.
 */
public final class ChannelIndicator {

    private static final Map<String, ChatChannel> COMMAND_CHANNELS = new LinkedHashMap<>();

    private static final String REPLY_COMMAND = "/r ";
    private static final int LABEL_PADDING = 4;
    private static final int LABEL_GAP = 2;
    private static final int OPAQUE = 0xFF000000;
    private static final int DEFAULT_TEXT_COLOR = 0xFFFFFFFF;

    static {
        COMMAND_CHANNELS.put("/lc ", ChannelRegistry.LOCAL);
        COMMAND_CHANNELS.put("/pc ", ChannelRegistry.PARTY);
        COMMAND_CHANNELS.put("/tc ", ChannelRegistry.TOWN);
        COMMAND_CHANNELS.put("/nc ", ChannelRegistry.NATION);
    }

    private static Text text;
    private static Integer inputColor;

    private ChannelIndicator() {
    }

    public static void update() {
        String typed = typedText();
        if (typed != null && typed.startsWith("/")) {
            updateForCommand(typed);
            return;
        }

        ChatChannel channel = ChannelTracker.getActive();
        if (channel == null)
            channel = ChannelTracker.getFallback();

        // Global is the default, so labelling it would just be noise.
        if (channel == null || ChannelRegistry.GLOBAL.name().equals(channel.name())) {
            show(null, null);
            return;
        }

        show(Text.literal(channel.name()).withColor(channel.color()), channel.color());
    }

    private static void updateForCommand(String typed) {
        for (Map.Entry<String, ChatChannel> entry : COMMAND_CHANNELS.entrySet()) {
            if (typed.startsWith(entry.getKey())) {
                ChatChannel channel = entry.getValue();
                show(Text.literal(channel.name()), channel.color());
                return;
            }
        }

        if (typed.startsWith(REPLY_COMMAND)) {
            String lastPerson = CavernChatConfig.getInstance().lastDMPerson;
            if (lastPerson == null)
                return;
            show(Text.literal(lastPerson), DirectMessages.colorFor(lastPerson));
            return;
        }

        show(null, null);
    }

    /** How far the chat box has to shift to the right to clear the tag. */
    public static int getOffset() {
        if (text == null)
            return 0;

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        return textRenderer.getWidth(text) + LABEL_PADDING;
    }

    /** How far the chat box moves right. */
    public static int getLayoutOffset() {
        int width = getOffset();
        return width == 0 ? 0 : width + LABEL_GAP;
    }

    public static Text getText() {
        return text;
    }

    public static int getEditableColor() {
        return inputColor == null ? DEFAULT_TEXT_COLOR : OPAQUE + inputColor;
    }

    public static Integer getLabelColor() {
        return inputColor == null ? null : OPAQUE + inputColor;
    }

    private static void show(Text label, Integer color) {
        text = label;
        inputColor = color;
    }

    private static String typedText() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (!(client.currentScreen instanceof ChatScreen chat))
            return null;

        return ((ChatScreenAccessor) chat).getChatField().getText();
    }
}
