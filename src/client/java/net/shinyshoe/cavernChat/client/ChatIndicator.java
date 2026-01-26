package net.shinyshoe.cavernChat.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.shinyshoe.cavernChat.CavernChat;
import net.shinyshoe.cavernChat.mixin.client.ChatScreenAccessor;

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
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.currentScreen instanceof ChatScreen chat) {
            TextFieldWidget tf = ((ChatScreenAccessor) chat).getChatField();
            String typed = tf.getText();

            if(typed.startsWith("/")) {
                if(typed.startsWith("/lc ")) {
                    currentIndicator.text = Text.literal(ChatChannel.LOCAL.name());
                    currentIndicator.inputColor = ChatChannel.LOCAL.color();
                    return;
                };
                if(typed.startsWith("/pc ")) {
                    currentIndicator.text = Text.literal(ChatChannel.PARTY.name());
                    currentIndicator.inputColor = ChatChannel.PARTY.color();
                    return;
                };
                if(typed.startsWith("/tc ")) {
                    currentIndicator.text = Text.literal(ChatChannel.TOWN.name());
                    currentIndicator.inputColor = ChatChannel.TOWN.color();
                    return;
                };
                if(typed.startsWith("/nc ")) {
                    currentIndicator.text = Text.literal(ChatChannel.NATION.name());
                    currentIndicator.inputColor = ChatChannel.NATION.color();
                    return;
                };
                if(typed.startsWith("/r ")) {
                    currentIndicator.text = Text.literal(CavernChatConfig.getInstance().lastDMPerson);
                    currentIndicator.inputColor = ChatVisibilityController.getDmColor(CavernChatConfig.getInstance().lastDMPerson);
                    return;
                };
                currentIndicator.text = null;
                currentIndicator.inputColor = null;
                return;
            }
        }

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
    public static Integer getInputColor() {
        return currentIndicator.inputColor;
    }
}
