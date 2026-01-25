package net.shinyshoe.cavernChat.client;

import com.google.common.collect.Lists;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.text.Text;
import net.shinyshoe.cavernChat.CavernChat;
import net.shinyshoe.cavernChat.mixin.client.ChatHudAccessor;

import java.util.ArrayList;
import java.util.List;

public final class ChatVisibilityController {
    static private List<CategorizedChat> categorizedChatList = new ArrayList<>();
    private ChatVisibilityController() {}

    public static void chatClear() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.inGameHud == null) return;

        ChatHud chatHud = client.inGameHud.getChatHud();
        ChatHudAccessor acc = (ChatHudAccessor) chatHud;

        acc.getVisibleMessages().clear();
    }

    public static void chatRebuild() {
        CavernChat.LOGGER.info("Rebuilding chat...");
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.inGameHud == null) return;

        ChatHud chatHud = client.inGameHud.getChatHud();
        ChatHudAccessor acc = (ChatHudAccessor) chatHud;

        acc.getVisibleMessages().clear();
        for(CategorizedChat categorizedChat : categorizedChatList) {
            if(ChatType.enabled.get(categorizedChat.type)) {
                acc.invokeAddVisibleMessage(categorizedChat.message);
            }
        }
    }

    public static void chatAdd(ChatHudLine message) {
        MinecraftClient client = MinecraftClient.getInstance();
        Text content = message.content();
        ChatHud chatHud = client.inGameHud.getChatHud();
        ChatHudAccessor acc = (ChatHudAccessor) chatHud;

        for (ChatFilter filter : ChatFilter.FILTERS.values()) {
            if(!filter.getStatus()) continue;
            if(filter.checkText(content)) {
                categorizedChatList.add(new CategorizedChat(filter.type(), message));
                acc.invokeAddVisibleMessage(message);
            }
        }
    }

    public static void chatReset() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.inGameHud == null) return;

        ChatHud chatHud = client.inGameHud.getChatHud();
        chatHud.reset();
    }
}