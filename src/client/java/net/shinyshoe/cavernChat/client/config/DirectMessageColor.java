package net.shinyshoe.cavernChat.client.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

/** The colour assigned to one DM partner, remembered across sessions. */
public class DirectMessageColor {
    public String player = "";

    @ConfigEntry.ColorPicker
    public int color = 0xFFFFFF;

    public DirectMessageColor() {
    }

    public DirectMessageColor(String player, int color) {
        this.player = player;
        this.color = color;
    }
}
