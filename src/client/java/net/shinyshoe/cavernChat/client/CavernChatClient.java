package net.shinyshoe.cavernChat.client;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.ActionResult;
import net.shinyshoe.cavernChat.client.config.CavernChatConfig;
import net.shinyshoe.cavernChat.client.config.ConfigApplier;
import net.shinyshoe.cavernChat.client.config.ConfigLocks;
import net.shinyshoe.cavernChat.client.message.ChatHistory;

public class CavernChatClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AutoConfig.register(CavernChatConfig.class, GsonConfigSerializer::new);
        ConfigLocks.register();

        ConfigHolder<CavernChatConfig> holder = AutoConfig.getConfigHolder(CavernChatConfig.class);
        holder.registerSaveListener(CavernChatClient::onConfigChanged);
        holder.registerLoadListener(CavernChatClient::onConfigChanged);

        applyConfig(holder.get());
    }

    private static ActionResult onConfigChanged(ConfigHolder<CavernChatConfig> holder, CavernChatConfig config) {
        applyConfig(config);
        return ActionResult.SUCCESS;
    }

    private static void applyConfig(CavernChatConfig config) {
        ConfigApplier.apply(config);
        ChatHistory.rebuild();
    }
}
