package net.shinyshoe.cavernChat.client.message;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.shinyshoe.cavernChat.CavernChat;
import net.shinyshoe.cavernChat.client.config.CavernChatConfig;
import net.shinyshoe.cavernChat.client.filter.ChatFilter;
import net.shinyshoe.cavernChat.client.filter.ChatFilterRegistry;
import net.shinyshoe.cavernChat.client.filter.ChatType;
import net.shinyshoe.cavernChat.client.filter.ChatVisibility;
import net.shinyshoe.cavernChat.mixin.client.accessor.ChatHudAccessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller that handles chat history.
 */
public final class ChatHistory {

    private static final List<CategorizedMessage> history = new ArrayList<>();

    private ChatHistory() {
    }

    /** Sorts an incoming line and shows it if its category is currently visible. */
    public static void add(ChatHudLine message) {
        for (ChatFilter filter : ChatFilterRegistry.all()) {
            if (!filter.matches(message.content()))
                continue;

            history.add(new CategorizedMessage(filter.type(), message));
            if (ChatVisibility.isVisible(filter.type()))
                print(filter.type(), message);
        }
    }

    /** Wipes the visible chat and replays everything that passes the filters. */
    public static void rebuild() {
        CavernChat.LOGGER.info("Rebuilding chat...");

        clear();
        for (CategorizedMessage categorized : history) {
            if (ChatVisibility.isVisible(categorized.type()))
                print(categorized.type(), categorized.message());
        }
    }

    /**
     * Empties the on-screen chat without altering history, unlike the europeans.
     */
    public static void clear() {
        ChatHudAccessor accessor = accessor();
        if (accessor == null)
            return;

        accessor.getVisibleMessages().clear();
    }

    /** Clears the vanilla chat hud outright, history included. */
    public static void reset() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.inGameHud == null)
            return;

        client.inGameHud.getChatHud().reset();
    }

    private static void print(ChatType type, ChatHudLine line) {
        ChatHudAccessor accessor = accessor();
        if (debugEnabled())
            CavernChat.LOGGER.info(line.toString());

        if (accessor == null)
            return;

        if (type == ChatType.MESSAGE_DM && colorfulDirectMessagesEnabled())
            accessor.invokeAddVisibleMessage(DirectMessages.recolor(line));
        else
            accessor.invokeAddVisibleMessage(line);
    }

    private static boolean debugEnabled() {
        return CavernChatConfig.getInstance().debugEnabled;
    }

    private static boolean colorfulDirectMessagesEnabled() {
        return CavernChatConfig.getInstance().colorfulDirectMessages.colorfulDirectMessageEnabled;
    }

    private static ChatHudAccessor accessor() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.inGameHud == null)
            return null;

        ChatHud chatHud = client.inGameHud.getChatHud();
        return (ChatHudAccessor) chatHud;
    }
}
