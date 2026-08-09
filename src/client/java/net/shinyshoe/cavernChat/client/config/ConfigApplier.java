package net.shinyshoe.cavernChat.client.config;

import net.shinyshoe.cavernChat.client.filter.ChatVisibility;
import net.shinyshoe.cavernChat.client.filter.ChatType;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Pushes the saved settings into the live filter state. This is the only place
 * that knows which config field belongs to which {@link ChatType}. Redrawing is
 * left to the caller.
 */
public final class ConfigApplier {

    private static final Map<ChatType, Supplier<FilterMode>> MODES = new LinkedHashMap<>();

    static {
        MODES.put(ChatType.PLAYER_JOIN, () -> config().joinLeaveMessages.playerJoinMessage);
        MODES.put(ChatType.PLAYER_LEAVE, () -> config().joinLeaveMessages.playerLeaveMessage);
        MODES.put(ChatType.NEW_PLAYER, () -> config().joinLeaveMessages.newPlayerMessage);

        MODES.put(ChatType.VOTE, () -> config().serverMessages.vote);
        MODES.put(ChatType.MOTD, () -> config().serverMessages.motd);
        MODES.put(ChatType.CRATE_OPEN, () -> config().serverMessages.crateOpen);

        MODES.put(ChatType.LOTTERY, () -> config().pluginMessages.lottery);
        MODES.put(ChatType.COINFLIP, () -> config().pluginMessages.coinFlip);

        MODES.put(ChatType.MOFOOD_CHARITY, () -> config().moFoodMessages.moFoodCharity);
        MODES.put(ChatType.MOFOOD_REROLL_QUEST, () -> config().moFoodMessages.moFoodRerollQuest);
        MODES.put(ChatType.MOFOOD_NEW_SEASON, () -> config().moFoodMessages.moFoodNewSeason);

        MODES.put(ChatType.SLIMEFUN_ITEM_DISABLED, () -> config().slimefunMessages.slimefunItemDisabled);

        MODES.put(ChatType.DUNGEON_DROWNED_PIRATE, () -> config().dungeonMessages.dungeonsDrownedPirate);
    }

    private ConfigApplier() {
    }

    public static void apply(CavernChatConfig config) {
        ChatVisibility.clearDynamicTypes();

        boolean otherVisible = ChatVisibility.isVisible(ChatType.MESSAGE_OTHER);
        for (Map.Entry<ChatType, Supplier<FilterMode>> entry : MODES.entrySet()) {
            ChatType type = entry.getKey();
            switch (entry.getValue().get()) {
                case ALWAYS_ON -> ChatVisibility.set(type, true);
                case ALWAYS_OFF -> ChatVisibility.set(type, false);
                case DYNAMIC -> {
                    ChatVisibility.markDynamic(type);
                    ChatVisibility.set(type, otherVisible);
                }
            }
        }

        ChatVisibility.set(ChatType.MESSAGE_GLOBAL, config.chatChannels.globalMessages);
        ChatVisibility.set(ChatType.MESSAGE_LOCAL, config.chatChannels.localMessages);
        ChatVisibility.set(ChatType.MESSAGE_PARTY, config.chatChannels.partyMessages);
        ChatVisibility.set(ChatType.MESSAGE_TOWN, config.chatChannels.townMessages);
        ChatVisibility.set(ChatType.MESSAGE_NATION, config.chatChannels.nationMessages);
        ChatVisibility.set(ChatType.MESSAGE_DM, config.chatChannels.dmMessages);
        ChatVisibility.set(ChatType.MESSAGE_OTHER, config.chatChannels.otherMessages);
    }

    private static CavernChatConfig config() {
        return CavernChatConfig.getInstance();
    }
}
