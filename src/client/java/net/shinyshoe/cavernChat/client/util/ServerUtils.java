package net.shinyshoe.cavernChat.client.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;

public class ServerUtils {
    static public boolean isCavern() {
        MinecraftClient client = MinecraftClient.getInstance();
        ServerInfo info = client.getCurrentServerEntry();
        if (info == null) return false;
        String addressString = info.address;
        return addressString.endsWith("thecavern.net");
    }
}
