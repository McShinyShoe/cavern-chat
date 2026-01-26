package net.shinyshoe.cavernChat.client;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.ArrayList;
import java.util.List;

@Config(name = "cavern-chat")
public class CavernChatConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    public ChatChannels chatChannels = new ChatChannels();
    @ConfigEntry.Gui.CollapsibleObject
    public JoinLeaveMessages joinLeaveMessages = new JoinLeaveMessages();
    @ConfigEntry.Gui.CollapsibleObject
    public ServerMessages serverMessages = new ServerMessages();
    @ConfigEntry.Gui.CollapsibleObject
    public PluginMessages pluginMessages = new PluginMessages();
    @ConfigEntry.Gui.CollapsibleObject
    public MoFoodMessages moFoodMessages = new MoFoodMessages();
    @ConfigEntry.Gui.CollapsibleObject
    public SlimefunMessages slimefunMessages = new SlimefunMessages();
    @ConfigEntry.Gui.CollapsibleObject
    public DungeonMessages dungeonMessages = new DungeonMessages();
    @ConfigEntry.Gui.CollapsibleObject
    public ColorfulDirectMessages colorfulDirectMessages = new ColorfulDirectMessages();


    public static class ChatChannels {
        public boolean globalMessages = true;
        public boolean localMessages = true;
        public boolean partyMessages = true;
        public boolean townMessages = true;
        public boolean nationMessages = true;
        public boolean dmMessages = true;
        public boolean otherMessages = true;
    }

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

    public static class SlimefunMessages {
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        public FilterMode slimefunItemDisabled = FilterMode.DYNAMIC;
    }

    public static class DungeonMessages {
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        public FilterMode dungeonsDrownedPirate = FilterMode.DYNAMIC;
    }

    public static class ColorfulDirectMessages {
        public boolean colorfulDirectMessageEnabled = true;
        public List<DirectMessageColor> colorfulDirectMessageColors= new ArrayList<>();
    }

    public static CavernChatConfig getInstance() {
        return AutoConfig.getConfigHolder(CavernChatConfig.class).getConfig();
    }
}
