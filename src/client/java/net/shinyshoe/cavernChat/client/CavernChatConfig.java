package net.shinyshoe.cavernChat.client;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "cavern-chat")
public class CavernChatConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    public JoinLeaveMessages joinLeaveMessages = new JoinLeaveMessages();
    @ConfigEntry.Gui.CollapsibleObject
    public ServerMessages serverMessages = new ServerMessages();
    @ConfigEntry.Gui.CollapsibleObject
    public PluginMessages pluginMessages = new PluginMessages();
    @ConfigEntry.Gui.CollapsibleObject
    public MoFoodMessages moFoodMessages = new MoFoodMessages();

    public static class JoinLeaveMessages {
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        public FilterMode playerJoinMessage = FilterMode.DYNAMIC;
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        public FilterMode playerLeaveMessage = FilterMode.DYNAMIC;
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        public FilterMode newPlayerMessage = FilterMode.DYNAMIC;
    }

    public static class ServerMessages {
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        public FilterMode vote = FilterMode.DYNAMIC;
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        public FilterMode motd = FilterMode.DYNAMIC;
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        public FilterMode crateOpen = FilterMode.DYNAMIC;
    }

    public static class PluginMessages {
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        public FilterMode lottery = FilterMode.DYNAMIC;
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        public FilterMode coinFlip = FilterMode.DYNAMIC;
    }

    public static class MoFoodMessages {
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        public FilterMode moFoodCharity = FilterMode.DYNAMIC;
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        public FilterMode moFoodRerollQuest = FilterMode.DYNAMIC;
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        public FilterMode moFoodNewSeason = FilterMode.DYNAMIC;
    }

    public static CavernChatConfig getInstance() {
        return AutoConfig.getConfigHolder(CavernChatConfig.class).getConfig();
    }
}
