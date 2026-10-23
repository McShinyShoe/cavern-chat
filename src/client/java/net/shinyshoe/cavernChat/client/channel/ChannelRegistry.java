package net.shinyshoe.cavernChat.client.channel;

import net.minecraft.ChatFormatting;
import net.shinyshoe.cavernChat.client.util.ColorUtils;

import java.util.Map;

/** The channels The Cavern has, and how to look one up. */
public final class ChannelRegistry {

    public static final ChatChannel GLOBAL = new ChatChannel(0xFCFCFC, "Global");
    public static final ChatChannel LOCAL = new ChatChannel(0xFFD700, "Local");
    public static final ChatChannel PARTY = new ChatChannel(0xDF9EFF, "Party");
    public static final ChatChannel TOWN = new ChatChannel(ColorUtils.rgbOf(ChatFormatting.GREEN), "Town");
    public static final ChatChannel NATION = new ChatChannel(ColorUtils.rgbOf(ChatFormatting.GOLD), "Nation");
    public static final ChatChannel ADMIN = new ChatChannel(ColorUtils.rgbOf(ChatFormatting.BLUE), "Admin");

    private static final int DM_COLOR = 0xebc45c;

    /** The letter the server echoes back when you switch channels. */
    private static final Map<Character, ChatChannel> BY_COMMAND_CHAR = Map.of(
            'g', GLOBAL,
            'l', LOCAL,
            'p', PARTY,
            't', TOWN,
            'n', NATION,
            'a', ADMIN);

    private ChannelRegistry() {
    }

    public static ChatChannel byCommandChar(char c) {
        return BY_COMMAND_CHAR.get(c);
    }

    /** DMs aren't a real channel, so one is made up per conversation partner. */
    public static ChatChannel directMessage(String player) {
        return new ChatChannel(DM_COLOR, player);
    }
}
