package net.shinyshoe.cavernChat.client;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.shinyshoe.cavernChat.client.util.ChatUtils;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ChatFilter {
    public ChatFilter(String name, Predicate<Text> filter, boolean status) {
        this.name = name;
        this.filter = filter;
        this.status = status;
    }

    public static final Map<String, ChatFilter> FILTERS = new LinkedHashMap<>();
    public static final Map<String, Supplier<FilterMode>> CONFIGURABLE_FILTERS = new LinkedHashMap<>();
    public static final Set<String> DYNAMIC_FILTERS = new HashSet<>();

    static {
        FILTERS.put("global", new ChatFilter("global", (Text text) -> ChatUtils.checkChar(text, '»', Formatting.GRAY.getColorValue()) &&
                !ChatUtils.checkChar(text, '[', Formatting.GREEN.getColorValue()) &&
                !ChatUtils.checkChar(text, '[', Formatting.GOLD.getColorValue()) &&
                !ChatUtils.checkChar(text, '[', 0xFFD700) &&
                !ChatUtils.checkChar(text, '[', 0x64F581), true));
        FILTERS.put("town", new ChatFilter("town", (Text text) -> ChatUtils.checkChar(text, '[', Formatting.GREEN.getColorValue()) && ChatUtils.checkChar(text, '»', Formatting.GRAY.getColorValue()), true));
        FILTERS.put("nation", new ChatFilter("nation", (Text text) -> ChatUtils.checkChar(text, '[', Formatting.GOLD.getColorValue()) && ChatUtils.checkChar(text, '»', Formatting.GRAY.getColorValue()), true));
        FILTERS.put("local", new ChatFilter("local", (Text text) -> ChatUtils.checkChar(text, '[', 0xFFD700) && ChatUtils.checkChar(text, '»', Formatting.GRAY.getColorValue()), true));
        FILTERS.put("party", new ChatFilter("party", (Text text) -> ChatUtils.checkChar(text, '[', 0x64F581) && ChatUtils.checkChar(text, '»', Formatting.GRAY.getColorValue()), true));
        FILTERS.put("dm", new ChatFilter("dm", (Text text) -> text.getString().startsWith("✉"), true));

        FILTERS.put("player_join", new ChatFilter("player_join", (Text text) -> text.getString().startsWith("[+]"), true));
        FILTERS.put("player_leave", new ChatFilter("player_leave", (Text text) -> text.getString().startsWith("[-]"), true));
        FILTERS.put("new_player", new ChatFilter("new_player", (Text text) -> text.getString().startsWith("Welcome"), true));

        FILTERS.put("vote", new ChatFilter("vote", (Text text) -> text.getString().startsWith("[/VOTE]"), true));
        FILTERS.put("motd", new ChatFilter("motd", (Text text) -> text.getString().startsWith("\nDISCORD SERVER") || text.getString().startsWith("\nSERVER STORE") || text.getString().startsWith("\nSUGGEST IDEAS") || text.getString().startsWith("\nSERVER WIKI"),true));
        FILTERS.put("crate_open", new ChatFilter("crate_open", (Text text) -> text.getString().contains("\nPurchase keys:"), true));

        FILTERS.put("lottery", new ChatFilter("lottery", (Text text) -> text.getString().startsWith("LOTTERY"), true));
        FILTERS.put("coinflip", new ChatFilter("coinflip", (Text text) -> text.getString().contains("ᴄᴏɪɴꜰʟɪᴘ"), true));

        FILTERS.put("mofood_charity", new ChatFilter("mofood_charity", (Text text) -> text.getString().startsWith("Charity"), true));
        FILTERS.put("mofood_reroll_quest", new ChatFilter("mofood_reroll_quest", (Text text) -> text.getString().startsWith("MoFood") && text.getString().contains("rerolled the Quest"), true));
        FILTERS.put("mofood_new_season", new ChatFilter("mofood_new_season", (Text text) -> text.getString().startsWith("▅") && text.getString().contains("New Season"), true));

        FILTERS.put("dungeon_drowned_pirate", new ChatFilter("dungeon_drowned_pirate", (Text text) -> text.getString().startsWith("Drowned Pirate"), true));
        FILTERS.put("other", new ChatFilter("other", (Text text) -> {
            for(ChatFilter filter : FILTERS.values()) {
                if(Objects.equals(filter.getName(), "other")) continue;
                if(filter.checkText(text)) return  false;
            }
            return true;
        }, true));
    }

    static {
        CONFIGURABLE_FILTERS.put("player_join", () -> CavernChatConfig.getInstance().joinLeaveMessages.playerJoinMessage);
        CONFIGURABLE_FILTERS.put("player_leave", () -> CavernChatConfig.getInstance().joinLeaveMessages.playerLeaveMessage);
        CONFIGURABLE_FILTERS.put("new_player", () -> CavernChatConfig.getInstance().joinLeaveMessages.newPlayerMessage);

        CONFIGURABLE_FILTERS.put("vote", () -> CavernChatConfig.getInstance().serverMessages.vote);
        CONFIGURABLE_FILTERS.put("motd", () -> CavernChatConfig.getInstance().serverMessages.motd);
        CONFIGURABLE_FILTERS.put("crate_open", () -> CavernChatConfig.getInstance().serverMessages.crateOpen);

        CONFIGURABLE_FILTERS.put("lottery", () -> CavernChatConfig.getInstance().pluginMessages.lottery);
        CONFIGURABLE_FILTERS.put("coinflip", () -> CavernChatConfig.getInstance().pluginMessages.coinFlip);

        CONFIGURABLE_FILTERS.put("mofood_charity", () -> CavernChatConfig.getInstance().moFoodMessages.moFoodCharity);
        CONFIGURABLE_FILTERS.put("mofood_reroll_quest", () -> CavernChatConfig.getInstance().moFoodMessages.moFoodRerollQuest);
        CONFIGURABLE_FILTERS.put("mofood_new_season", () -> CavernChatConfig.getInstance().moFoodMessages.moFoodNewSeason);
    }

    private final String name;
    private final Predicate<Text> filter;
    private boolean status;

    public boolean checkText(Text text) {
        return filter.test(text);
    }

    public void enable() {
        status = true;
        if(Objects.equals(name, "other")) {
            for(String filterKey : DYNAMIC_FILTERS) FILTERS.get(filterKey).enable();
        }
        ChatVisibilityController.chatRebuild();
    }
    public void disable() {
        status = false;
        if(Objects.equals(name, "other")) {
            for(String filterKey : DYNAMIC_FILTERS) FILTERS.get(filterKey).disable();
        }
        ChatVisibilityController.chatRebuild();
    }

    public String getName() { return name; }
    public boolean getStatus() { return status; }
}