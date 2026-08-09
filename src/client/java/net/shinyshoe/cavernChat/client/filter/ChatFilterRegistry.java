package net.shinyshoe.cavernChat.client.filter;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.shinyshoe.cavernChat.client.util.TextUtils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

/** The rule for recognising each {@link ChatType}, in the order they're tried. */
public final class ChatFilterRegistry {

    private static final int COLOR_LOCAL = 0xFFD700;
    private static final int COLOR_PARTY = 0x64F581;
    private static final int COLOR_TOWN = Formatting.GREEN.getColorValue();
    private static final int COLOR_NATION = Formatting.GOLD.getColorValue();
    private static final int COLOR_SEPARATOR = Formatting.GRAY.getColorValue();

    private static final Map<ChatType, ChatFilter> FILTERS = new LinkedHashMap<>();

    static {
        // Player chat, told apart by the coloured bracket in front of the "»".
        define(ChatType.MESSAGE_GLOBAL, text -> isPlayerChat(text)
                && !TextUtils.hasColoredChar(text, '[', COLOR_TOWN)
                && !TextUtils.hasColoredChar(text, '[', COLOR_NATION)
                && !TextUtils.hasColoredChar(text, '[', COLOR_LOCAL)
                && !TextUtils.hasColoredChar(text, '[', COLOR_PARTY));
        define(ChatType.MESSAGE_TOWN, text -> isPlayerChat(text)
                && TextUtils.hasColoredChar(text, '[', COLOR_TOWN));
        define(ChatType.MESSAGE_NATION, text -> isPlayerChat(text)
                && TextUtils.hasColoredChar(text, '[', COLOR_NATION));
        define(ChatType.MESSAGE_LOCAL, text -> isPlayerChat(text)
                && TextUtils.hasColoredChar(text, '[', COLOR_LOCAL));
        define(ChatType.MESSAGE_PARTY, text -> isPlayerChat(text)
                && TextUtils.hasColoredChar(text, '[', COLOR_PARTY));
        define(ChatType.MESSAGE_DM, startsWith("✉"));

        define(ChatType.PLAYER_JOIN, startsWith("[+]"));
        define(ChatType.PLAYER_LEAVE, startsWith("[-]"));
        define(ChatType.NEW_PLAYER, startsWith("Welcome"));

        define(ChatType.VOTE, startsWith("[/VOTE]"));
        define(ChatType.MOTD, startsWith("\nDISCORD SERVER")
                .or(startsWith("\nSERVER STORE"))
                .or(startsWith("\nSUGGEST IDEAS"))
                .or(startsWith("\nSERVER WIKI")));
        define(ChatType.CRATE_OPEN, contains("\nPurchase keys:"));

        define(ChatType.LOTTERY, startsWith("LOTTERY"));
        define(ChatType.COINFLIP, contains("ᴄᴏɪɴꜰʟɪᴘ"));

        define(ChatType.MOFOOD_CHARITY, startsWith("Charity"));
        define(ChatType.MOFOOD_REROLL_QUEST, startsWith("MoFood").and(contains("rerolled the Quest")));
        define(ChatType.MOFOOD_NEW_SEASON, startsWith("▅").and(contains("New Season")));

        define(ChatType.SLIMEFUN_ITEM_DISABLED, startsWith("Slimefun 4> [This Item"));

        define(ChatType.DUNGEON_DROWNED_PIRATE, startsWith("Drowned Pirate"));

        // Registered last: "Other" is whatever no earlier rule claimed.
        define(ChatType.MESSAGE_OTHER, text -> {
            for (ChatFilter filter : FILTERS.values()) {
                if (filter.type() != ChatType.MESSAGE_OTHER && filter.matches(text))
                    return false;
            }
            return true;
        });
    }

    private ChatFilterRegistry() {
    }

    public static ChatFilter get(ChatType type) {
        return FILTERS.get(type);
    }

    public static Collection<ChatFilter> all() {
        return FILTERS.values();
    }

    private static void define(ChatType type, Predicate<Text> rule) {
        FILTERS.put(type, new ChatFilter(type, rule));
    }

    private static boolean isPlayerChat(Text text) {
        return TextUtils.hasColoredChar(text, '»', COLOR_SEPARATOR);
    }

    private static Predicate<Text> startsWith(String prefix) {
        return text -> text.getString().startsWith(prefix);
    }

    private static Predicate<Text> contains(String needle) {
        return text -> text.getString().contains(needle);
    }
}
