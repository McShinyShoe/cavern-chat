package net.shinyshoe.cavernChat.client;

public enum FilterMode {
    ALWAYS_ON,
    DYNAMIC,
    ALWAYS_OFF;

    public FilterMode next() {
        FilterMode[] v = values();
        return v[(ordinal() + 1) % v.length];
    }
}
