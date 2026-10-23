package net.shinyshoe.cavernChat.client.message;

//? if >=26.1 {
/*import net.minecraft.client.multiplayer.chat.GuiMessage;
*///?} else {
import net.minecraft.client.GuiMessage;
//?}
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.shinyshoe.cavernChat.CavernChat;
import net.shinyshoe.cavernChat.client.config.CavernChatConfig;
import net.shinyshoe.cavernChat.client.filter.ChatFilter;
import net.shinyshoe.cavernChat.client.filter.ChatFilterRegistry;
import net.shinyshoe.cavernChat.client.filter.ChatType;
import net.shinyshoe.cavernChat.client.filter.ChatVisibility;
import net.shinyshoe.cavernChat.mixin.client.accessor.ChatHudAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller that handles chat history.
 */
public final class ChatHistory {

    private static final List<CategorizedMessage> history = new ArrayList<>();

    private ChatHistory() {
    }

    /** Sorts an incoming line and shows it if its category is currently visible. */
    public static void add(GuiMessage message) {
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
        ChatComponent chat = chat();
        if (chat == null)
            return;

        chat.rescaleChat();
    }

    private static void print(ChatType type, GuiMessage line) {
        ChatHudAccessor accessor = accessor();
        if (debugEnabled()) {
            String content = line.content().getString();
            CavernChat.LOGGER.info("'" + content + "'");
            CavernChat.LOGGER.info(content.codePoints()
                    .limit(8)
                    .mapToObj(cp -> String.format("U+%04X", cp))
                    .collect(Collectors.joining(" ")));
            CavernChat.LOGGER.info(line.toString());
        }

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
        return (ChatHudAccessor) chat();
    }

    private static ChatComponent chat() {
        Minecraft client = Minecraft.getInstance();
        if (client == null || client.gui == null)
            return null;

        //? if >=26.2 {
        /*return client.gui.hud.getChat();
        *///?} else {
        return client.gui.getChat();
        //?}
    }
}
