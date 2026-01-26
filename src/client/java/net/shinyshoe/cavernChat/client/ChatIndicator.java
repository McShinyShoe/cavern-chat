package net.shinyshoe.cavernChat.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;

import java.util.Objects;

public class ChatIndicator {
    public Text text;
    public Integer inputColor;
    public static ChatIndicator currentIndicator = new ChatIndicator(null, null);

    public ChatIndicator(Text text, Integer inputColor) {
        this.text = text;
        this.inputColor = inputColor;
    }

    public static void updateIndicator() {ChatChannel channel = ChatChannel.getCurrentActiveChannel();
        if(channel == null) channel = ChatChannel.getCurrentDefaultChannel();
        if(channel == null || Objects.equals(channel.name(), "Global")) {
            currentIndicator.text = null;
            currentIndicator.inputColor = null;
            return;
        }
        currentIndicator.text = Text.literal(channel.name()).withColor(channel.color());
        currentIndicator.inputColor = channel.color();
    }
    public static int getOffset() {
        TextRenderer tr = MinecraftClient.getInstance().textRenderer;
        if(currentIndicator.text == null) return 0;
        return tr.getWidth(currentIndicator.text) + 4;
    }
    public static Text getText() {
        return currentIndicator.text;
    }
    public static int getInputColor() {
        return currentIndicator.inputColor;
    }
}
