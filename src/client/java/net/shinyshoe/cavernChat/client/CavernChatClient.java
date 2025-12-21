package net.shinyshoe.cavernChat.client;

import net.fabricmc.api.ClientModInitializer;
import net.shinyshoe.cavernChat.CavernChat;

public class CavernChatClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CavernChat.LOGGER.info("CavernChatClient.onInitializeClient()");
    }
}
