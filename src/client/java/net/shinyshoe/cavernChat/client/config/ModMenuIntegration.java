package net.shinyshoe.cavernChat.client.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
//? if >=26.1 {
/*import me.shedaniel.autoconfig.AutoConfigClient;
*///?} else {
import me.shedaniel.autoconfig.AutoConfig;
//?}
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.Screen;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        //? if >=26.1 {
        /*return parent -> AutoConfigClient.getConfigScreen(CavernChatConfig.class, (Screen) parent).get();
        *///?} else {
        return parent -> AutoConfig.getConfigScreen(CavernChatConfig.class, (Screen) parent).get();
        //?}
    }
}
