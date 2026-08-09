package net.shinyshoe.cavernChat.client.filter;

/**
 * Every category a chat line can be sorted into. {@link ChatFilterRegistry}
 * owns the rule that recognises each one, {@link ChatVisibility} whether it is
 * currently shown.
 */
public enum ChatType {
    MESSAGE_GLOBAL,
    MESSAGE_TOWN,
    MESSAGE_NATION,
    MESSAGE_LOCAL,
    MESSAGE_PARTY,
    MESSAGE_DM,
    MESSAGE_MARRY,
    MESSAGE_ADMIN,
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

    DUNGEON_DROWNED_PIRATE
}
