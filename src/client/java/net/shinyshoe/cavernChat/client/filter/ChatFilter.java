package net.shinyshoe.cavernChat.client.filter;

import net.minecraft.text.Text;

import java.util.function.Predicate;

/**
 * The rule that recognises one {@link ChatType}. Whether that type is currently
 * on screen is {@link ChatVisibility}'s business, not this one's.
 */
public record ChatFilter(ChatType type, Predicate<Text> rule) {

    public boolean matches(Text text) {
        return rule.test(text);
    }
}
