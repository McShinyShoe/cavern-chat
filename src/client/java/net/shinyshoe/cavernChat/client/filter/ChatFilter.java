package net.shinyshoe.cavernChat.client.filter;

import net.minecraft.network.chat.Component;

import java.util.function.Predicate;

/**
 * The rule that recognises one {@link ChatType}. Whether that type is currently
 * on screen is {@link ChatVisibility}'s business, not this one's.
 */
public record ChatFilter(ChatType type, Predicate<Component> rule) {

    public boolean matches(Component text) {
        return rule.test(text);
    }
}
