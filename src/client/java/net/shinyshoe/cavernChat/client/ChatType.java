package net.shinyshoe.cavernChat.client;

import java.util.EnumMap;

public enum ChatType {
    MESSAGE_GLOBAL,
    MESSAGE_TOWN,
    MESSAGE_NATION,
    MESSAGE_LOCAL ,
    MESSAGE_PARTY,
    MESSAGE_DM,
    MESSAGE_MARRY,
    MESSAGE_OTHER,

    PLAYER_JOIN,
    PLAYER_LEAVE,
    NEW_PLAYER,

    VOTE,
    MOTD,
    CRATE_OPEN,

    LOTTERY,
    COINFLIP,

    MOFOOD_CHARITY,
    MOFOOD_REROLL_QUEST,
    MOFOOD_NEW_SEASON,

    SLIMEFUN_ITEM_DISABLED,

    DUNGEON_DROWNED_PIRATE;

    public static final EnumMap<ChatType, Boolean> enabled = new EnumMap<>(ChatType.class);
    static {
        for (ChatType type : ChatType.values()) {
            enabled.put(type, true);
        }
    }
}