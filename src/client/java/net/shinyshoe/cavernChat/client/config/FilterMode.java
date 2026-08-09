package net.shinyshoe.cavernChat.client.config;

/** How a filterable message type reacts to the chat buttons. */
public enum FilterMode {
    /** Always shown, whatever the buttons say. */
    ALWAYS_ON,
    /** Follows the "Other" toggle. */
    DYNAMIC,
    /** Never shown. */
    ALWAYS_OFF;

    public FilterMode next() {
        FilterMode[] modes = values();
        return modes[(ordinal() + 1) % modes.length];
    }
}
