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
    }
}
