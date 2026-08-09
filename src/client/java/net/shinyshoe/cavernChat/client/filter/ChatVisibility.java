package net.shinyshoe.cavernChat.client.filter;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Set;

/**
 * Controller for all of the currently active Chat Types
 */
public final class ChatVisibility {

    private static final EnumMap<ChatType, Boolean> VISIBLE = new EnumMap<>(ChatType.class);

    /**
     * Types configured to follow the "Other" toggle rather than a fixed setting.
     */
    private static final Set<ChatType> DYNAMIC = EnumSet.noneOf(ChatType.class);

    static {
        for (ChatType type : ChatType.values())
            VISIBLE.put(type, true);
    }

    private ChatVisibility() {
    }

    public static boolean isVisible(ChatType type) {
        return VISIBLE.get(type);
    }

    /** Sets a chat type visibility. */
    public static void set(ChatType type, boolean visible) {
        VISIBLE.put(type, visible);
    }

    /**
     * Sets a type and, when it is "Other", drags the dynamic types with it, so
     * hiding chatter also hides the server noise that rides on top of it.
     */
    public static void setCascading(ChatType type, boolean visible) {
        set(type, visible);
        if (type != ChatType.MESSAGE_OTHER)
            return;

        for (ChatType dynamic : DYNAMIC)
            set(dynamic, visible);
    }

    public static Set<ChatType> dynamicTypes() {
        return DYNAMIC;
    }

    public static void clearDynamicTypes() {
        DYNAMIC.clear();
    }

    public static void markDynamic(ChatType type) {
        DYNAMIC.add(type);
    }
}
