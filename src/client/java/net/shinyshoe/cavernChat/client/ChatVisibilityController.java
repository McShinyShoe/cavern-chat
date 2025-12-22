package net.shinyshoe.cavernChat.client;

import com.google.common.collect.Lists;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.shinyshoe.cavernChat.CavernChat;
import net.shinyshoe.cavernChat.mixin.client.ChatHudAccessor;

public final class ChatVisibilityController {
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
        for(ChatHudLine chatHudLine : Lists.reverse(acc.getMessages())) {
            assert chatHudLine != null;
            boolean passFilter = false;
            for (ChatFilter filter : ChatFilter.FILTERS.values()) {
                if(!filter.getStatus()) continue;
                if(filter.checkText(chatHudLine.content())) passFilter = true;
            }
            if(!passFilter) continue;
            acc.invokeAddVisibleMessage(chatHudLine);
        }
    }

    public static void chatReset() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.inGameHud == null) return;

        ChatHud chatHud = client.inGameHud.getChatHud();
        chatHud.reset();
    }
}