package net.shinyshoe.cavernChat.client;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.shinyshoe.cavernChat.client.util.ChatUtils;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

public record ChatFilter(ChatType type, Predicate<Text> filter) {

    public static final Map<ChatType, ChatFilter> FILTERS = new LinkedHashMap<>();
    public static final Map<ChatType, Supplier<FilterMode>> CONFIGURABLE_FILTERS = new LinkedHashMap<>();
    public static final Set<ChatType> DYNAMIC_FILTERS = new HashSet<>();

    static {
        FILTERS.put(ChatType.MESSAGE_GLOBAL, new ChatFilter(ChatType.MESSAGE_GLOBAL, (Text text) -> ChatUtils.checkChar(text, '»', Formatting.GRAY.getColorValue()) &&
                !ChatUtils.checkChar(text, '[', Formatting.GREEN.getColorValue()) &&
                !ChatUtils.checkChar(text, '[', Formatting.GOLD.getColorValue()) &&
                !ChatUtils.checkChar(text, '[', 0xFFD700) &&
                !ChatUtils.checkChar(text, '[', 0x64F581)));
        FILTERS.put(ChatType.MESSAGE_TOWN, new ChatFilter(ChatType.MESSAGE_TOWN, (Text text) -> ChatUtils.checkChar(text, '[', Formatting.GREEN.getColorValue()) && ChatUtils.checkChar(text, '»', Formatting.GRAY.getColorValue())));
        FILTERS.put(ChatType.MESSAGE_NATION, new ChatFilter(ChatType.MESSAGE_NATION, (Text text) -> ChatUtils.checkChar(text, '[', Formatting.GOLD.getColorValue()) && ChatUtils.checkChar(text, '»', Formatting.GRAY.getColorValue())));
        FILTERS.put(ChatType.MESSAGE_LOCAL, new ChatFilter(ChatType.MESSAGE_LOCAL, (Text text) -> ChatUtils.checkChar(text, '[', 0xFFD700) && ChatUtils.checkChar(text, '»', Formatting.GRAY.getColorValue())));
        FILTERS.put(ChatType.MESSAGE_PARTY, new ChatFilter(ChatType.MESSAGE_PARTY, (Text text) -> ChatUtils.checkChar(text, '[', 0x64F581) && ChatUtils.checkChar(text, '»', Formatting.GRAY.getColorValue())));
        FILTERS.put(ChatType.MESSAGE_DM, new ChatFilter(ChatType.MESSAGE_DM, (Text text) -> text.getString().startsWith("✉")));

        FILTERS.put(ChatType.PLAYER_JOIN, new ChatFilter(ChatType.PLAYER_JOIN, (Text text) -> text.getString().startsWith("[+]")));
        FILTERS.put(ChatType.PLAYER_LEAVE, new ChatFilter(ChatType.PLAYER_LEAVE, (Text text) -> text.getString().startsWith("[-]")));
        FILTERS.put(ChatType.NEW_PLAYER, new ChatFilter(ChatType.NEW_PLAYER, (Text text) -> text.getString().startsWith("Welcome")));

        FILTERS.put(ChatType.VOTE, new ChatFilter(ChatType.VOTE, (Text text) -> text.getString().startsWith("[/VOTE]")));
        FILTERS.put(ChatType.MOTD, new ChatFilter(ChatType.MOTD, (Text text) -> text.getString().startsWith("\nDISCORD SERVER") || text.getString().startsWith("\nSERVER STORE") || text.getString().startsWith("\nSUGGEST IDEAS") || text.getString().startsWith("\nSERVER WIKI")));
        FILTERS.put(ChatType.CRATE_OPEN, new ChatFilter(ChatType.CRATE_OPEN, (Text text) -> text.getString().contains("\nPurchase keys:")));

        FILTERS.put(ChatType.LOTTERY, new ChatFilter(ChatType.LOTTERY, (Text text) -> text.getString().startsWith("LOTTERY")));
        FILTERS.put(ChatType.COINFLIP, new ChatFilter(ChatType.COINFLIP, (Text text) -> text.getString().contains("ᴄᴏɪɴꜰʟɪᴘ")));

        FILTERS.put(ChatType.MOFOOD_CHARITY, new ChatFilter(ChatType.MOFOOD_CHARITY, (Text text) -> text.getString().startsWith("Charity")));
        FILTERS.put(ChatType.MOFOOD_REROLL_QUEST, new ChatFilter(ChatType.MOFOOD_REROLL_QUEST, (Text text) -> text.getString().startsWith("MoFood") && text.getString().contains("rerolled the Quest")));
        FILTERS.put(ChatType.MOFOOD_NEW_SEASON, new ChatFilter(ChatType.MOFOOD_NEW_SEASON, (Text text) -> text.getString().startsWith("▅") && text.getString().contains("New Season")));

        FILTERS.put(ChatType.DUNGEON_DROWNED_PIRATE, new ChatFilter(ChatType.DUNGEON_DROWNED_PIRATE, (Text text) -> text.getString().startsWith("Drowned Pirate")));
        FILTERS.put(ChatType.MESSAGE_OTHER, new ChatFilter(ChatType.MESSAGE_OTHER, (Text text) -> {
            for (ChatFilter filter : FILTERS.values()) {
                if (filter.type() == ChatType.MESSAGE_OTHER) continue;
                if (filter.checkText(text)) return false;
            }
            return true;
        }));
    }

    static {
        CONFIGURABLE_FILTERS.put(ChatType.PLAYER_JOIN, () -> CavernChatConfig.getInstance().joinLeaveMessages.playerJoinMessage);
        CONFIGURABLE_FILTERS.put(ChatType.PLAYER_LEAVE, () -> CavernChatConfig.getInstance().joinLeaveMessages.playerLeaveMessage);
        CONFIGURABLE_FILTERS.put(ChatType.NEW_PLAYER, () -> CavernChatConfig.getInstance().joinLeaveMessages.newPlayerMessage);

        CONFIGURABLE_FILTERS.put(ChatType.VOTE, () -> CavernChatConfig.getInstance().serverMessages.vote);
        CONFIGURABLE_FILTERS.put(ChatType.MOTD, () -> CavernChatConfig.getInstance().serverMessages.motd);
        CONFIGURABLE_FILTERS.put(ChatType.CRATE_OPEN, () -> CavernChatConfig.getInstance().serverMessages.crateOpen);

        CONFIGURABLE_FILTERS.put(ChatType.LOTTERY, () -> CavernChatConfig.getInstance().pluginMessages.lottery);
        CONFIGURABLE_FILTERS.put(ChatType.COINFLIP, () -> CavernChatConfig.getInstance().pluginMessages.coinFlip);

        CONFIGURABLE_FILTERS.put(ChatType.MOFOOD_CHARITY, () -> CavernChatConfig.getInstance().moFoodMessages.moFoodCharity);
        CONFIGURABLE_FILTERS.put(ChatType.MOFOOD_REROLL_QUEST, () -> CavernChatConfig.getInstance().moFoodMessages.moFoodRerollQuest);
        CONFIGURABLE_FILTERS.put(ChatType.MOFOOD_NEW_SEASON, () -> CavernChatConfig.getInstance().moFoodMessages.moFoodNewSeason);
    }

    public boolean checkText(Text text) {
        return filter.test(text);
    }

    public void enable() {
        ChatType.enabled.put(type, true);
        if (type == ChatType.MESSAGE_OTHER) {
            for (ChatType filterKey : DYNAMIC_FILTERS) FILTERS.get(filterKey).enable();
        }
        ChatVisibilityController.chatRebuild();
    }

    public void disable() {
        ChatType.enabled.put(type, false);
        if (type == ChatType.MESSAGE_OTHER) {
            for (ChatType filterKey : DYNAMIC_FILTERS) FILTERS.get(filterKey).disable();
        }
        ChatVisibilityController.chatRebuild();
    }

    public boolean getStatus() {
        return ChatType.enabled.get(type);
    }
}