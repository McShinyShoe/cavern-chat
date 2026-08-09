package net.shinyshoe.cavernChat.client.message;

import net.minecraft.client.gui.hud.ChatHudLine;
import net.shinyshoe.cavernChat.client.filter.ChatType;

/** A {@link ChatHudLine} that is categorized. */
public record CategorizedMessage(ChatType type, ChatHudLine message) {
}
