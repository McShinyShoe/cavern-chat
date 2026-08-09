package net.shinyshoe.cavernChat.client.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;

public final class ServerUtils {
    private static final String CAVERN_DOMAIN = "thecavern.net";

    private ServerUtils() {
    }

    public static boolean isCavern() {
        MinecraftClient client = MinecraftClient.getInstance();
        ServerInfo info = client.getCurrentServerEntry();
        if (info == null)
            return false;
        return info.address.endsWith(CAVERN_DOMAIN);
    }
}
