package net.shinyshoe.cavernChat.client;

import net.minecraft.client.gui.hud.ChatHudLine;

public class CategorizedChat {
    public ChatType type;
    public ChatHudLine message;

    CategorizedChat(ChatType type, ChatHudLine message) {
        this.type = type;
        this.message = message;
    }

    public ChatType getType() {
        return type;
    }

    public ChatHudLine getMessage() {
        return message;
    }
    public void setMessage(ChatHudLine message) {
        this.message = message;
    }

    public void setType(ChatType type) {
        this.type = type;
    }
}
