package net.shinyshoe.cavernChat;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CavernChat implements ModInitializer {
    public static final String MOD_IO = "cavern-chat";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_IO);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Cavern Chat.");
    }
}
