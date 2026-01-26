package net.shinyshoe.cavernChat.client;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class DirectMessageColor {
    public String player = "";

    @ConfigEntry.ColorPicker
    public int color = 0xFFFFFF;

    public DirectMessageColor() {}

    public DirectMessageColor(String player, int color) {
        this.player = player;
        this.color = color;
    }
}
