package net.shinyshoe.cavernChat.client.message;

//? if >=26.1 {
/*import net.minecraft.client.multiplayer.chat.GuiMessage;
*///?} else {
import net.minecraft.client.GuiMessage;
//?}
import net.shinyshoe.cavernChat.client.filter.ChatType;

/** A {@link GuiMessage} that is categorized. */
public record CategorizedMessage(ChatType type, GuiMessage message) {
}
