package net.shinyshoe.cavernChat.client;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.ActionResult;

import java.util.Map;
import java.util.function.Supplier;

public class CavernChatClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AutoConfig.register(CavernChatConfig.class, GsonConfigSerializer::new);

        AutoConfig.getConfigHolder(CavernChatConfig.class).registerSaveListener((holder, config) -> {
            onConfigChanged(config);
            return ActionResult.SUCCESS;
        });
        AutoConfig.getConfigHolder(CavernChatConfig.class).registerLoadListener((holder, config) -> {
            onConfigChanged(config);
            return ActionResult.SUCCESS;
        });
        onConfigChanged(AutoConfig.getConfigHolder(CavernChatConfig.class).get());
    }
    private static void onConfigChanged(CavernChatConfig config) {
        ChatFilter.DYNAMIC_FILTERS.clear();
        for(Map.Entry<String, Supplier<FilterMode>> filter : ChatFilter.CONFIGURABLE_FILTERS.entrySet()) {
            switch (filter.getValue().get()) {
                case FilterMode.ALWAYS_OFF -> ChatFilter.FILTERS.get(filter.getKey()).disable();
                case FilterMode.ALWAYS_ON -> ChatFilter.FILTERS.get(filter.getKey()).enable();
                case FilterMode.DYNAMIC -> {
                    ChatFilter.DYNAMIC_FILTERS.add(filter.getKey());
                    if(ChatFilter.FILTERS.get("other").getStatus()) ChatFilter.FILTERS.get(filter.getKey()).enable();
                    else ChatFilter.FILTERS.get(filter.getKey()).disable();
                }
            }
        }
        ChatVisibilityController.chatRebuild();
    }
}
