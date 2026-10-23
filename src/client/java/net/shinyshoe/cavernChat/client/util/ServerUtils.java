package net.shinyshoe.cavernChat.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public final class ServerUtils {
    private static final String CAVERN_DOMAIN = "thecavern.net";

    private ServerUtils() {
    }

    public static boolean isCavern() {
        Minecraft client = Minecraft.getInstance();
        ServerData info = client.getCurrentServer();
        if (info == null)
            return false;
        return info.ip.endsWith(CAVERN_DOMAIN);
    }
}
