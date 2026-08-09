package net.shinyshoe.cavernChat.client.channel;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.shinyshoe.cavernChat.client.config.CavernChatConfig;
import net.shinyshoe.cavernChat.client.util.TextUtils;

/**
 * Keeps track of which channel the player is currently talking in.
 * The server never tells us directly where so we determine it ourself.
 */
public final class ChannelTracker {

    private static final String JOIN_MARKER = "WELCOME TO THE CAVERN";
    private static final String SWITCH_PREFIX = "You have switched";
    private static final String ADMIN_PREFIX = " - admin";

    private static final int CHANNEL_CHAR_INDEX = 3;
    private static final int SWITCH_CHAR_INDEX = 21;

    private static ChatChannel active;
    private static ChatChannel fallback;

    private ChannelTracker() {
    }

    public static ChatChannel getActive() {
        return active;
    }

    public static void setActive(ChatChannel channel) {
        active = channel;
    }

    public static ChatChannel getFallback() {
        return fallback;
    }

    public static void setFallback(ChatChannel channel) {
        fallback = channel;
    }

    /** Gets the current channel when player first join. */
    public static void readFrom(Text message) {
        String line = message.getString();

        if (line.contains(JOIN_MARKER))
            requestCurrentChannel();

        if (line.startsWith(ADMIN_PREFIX))
            unlockAdminFeatures();

        char letter = 0;
        if (TextUtils.hasColoredChar(message, '✎', Formatting.GOLD.getColorValue())) {
            letter = charAt(line, CHANNEL_CHAR_INDEX);
        } else if (line.startsWith(SWITCH_PREFIX)) {
            letter = charAt(line, SWITCH_CHAR_INDEX);
        }

        if (letter == 0)
            return;

        ChatChannel channel = ChannelRegistry.byCommandChar(letter);
        if (channel != null)
            setActive(channel);
    }

    private static void unlockAdminFeatures() {
        CavernChatConfig config = CavernChatConfig.getInstance();
        if (config.adminFeatures)
            return;

        config.adminFeatures = true;
        CavernChatConfig.save();
    }

    private static char charAt(String line, int index) {
        return index < line.length() ? line.charAt(index) : 0;
    }

    /** Ask server current and available channel. */
    private static void requestCurrentChannel() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null)
            return;

        client.player.networkHandler.sendChatCommand("channel");
    }
}
