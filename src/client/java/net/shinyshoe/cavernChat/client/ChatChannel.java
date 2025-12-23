package net.shinyshoe.cavernChat.client;

import net.minecraft.util.Formatting;

public record ChatChannel(int color, String name) {
    private static ChatChannel currentDefaultChannel;
    private static ChatChannel currentActiveChannel;

    public static ChatChannel GLOBAL = new ChatChannel(0xfcfcfc, "Global");
    public static ChatChannel LOCAL = new ChatChannel(0xFFD700, "Local");
    public static ChatChannel PARTY = new ChatChannel(0x64F581, "Party");
    public static ChatChannel TOWN = new ChatChannel(Formatting.GREEN.getColorValue(), "Town");
    public static ChatChannel NATION = new ChatChannel(Formatting.GOLD.getColorValue(), "Nation");

    public static ChatChannel getCurrentDefaultChannel() {
        return currentDefaultChannel;
    }

    public static void setCurrentDefaultChannel(ChatChannel currentDefaultChannel) {
        ChatChannel.currentDefaultChannel = currentDefaultChannel;
    }

    public static ChatChannel getCurrentActiveChannel() {
        return currentActiveChannel;
    }

    public static void setCurrentActiveChannel(ChatChannel currentActiveChannel) {
        ChatChannel.currentActiveChannel = currentActiveChannel;
    }

    static public ChatChannel makeDMChannel(String player) {
        return new ChatChannel(0xebc45c, player);
    }
}
