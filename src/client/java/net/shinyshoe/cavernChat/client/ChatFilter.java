package net.shinyshoe.cavernChat.client;

import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class ChatFilter {
    public ChatFilter(String name, Predicate<Text> filter, boolean status) {
        this.name = name;
        this.filter = filter;
        this.status = status;
    }

    public static final Map<String, ChatFilter> FILTERS = new LinkedHashMap<>();

    private static boolean checkChar(Text text, char c, Integer color) {
        return text.visit((style, segment) -> {
            if (segment != null && !segment.isEmpty() && segment.indexOf(c) >= 0) {
                var charColor = style.getColor();
                assert color != null;
                if (charColor != null && charColor.getRgb() == color) {
                    return Optional.of(true);
                }
            }
            return Optional.empty();
        }, Style.EMPTY).orElse(false);
    }

    static {
        FILTERS.put("global", new ChatFilter("global", (Text text) -> checkChar(text, '»', Formatting.GRAY.getColorValue()) &&
                !checkChar(text, '[', Formatting.GREEN.getColorValue()) &&
                !checkChar(text, '[', Formatting.GOLD.getColorValue()) &&
                !checkChar(text, '[', 0xFFD700) &&
                !checkChar(text, '[', 0x64F581), true));
        FILTERS.put("town", new ChatFilter("town", (Text text) -> checkChar(text, '[', Formatting.GREEN.getColorValue()) && checkChar(text, '»', Formatting.GRAY.getColorValue()), true));
        FILTERS.put("nation", new ChatFilter("nation", (Text text) -> checkChar(text, '[', Formatting.GOLD.getColorValue()) && checkChar(text, '»', Formatting.GRAY.getColorValue()), true));
        FILTERS.put("local", new ChatFilter("local", (Text text) -> checkChar(text, '[', 0xFFD700) && checkChar(text, '»', Formatting.GRAY.getColorValue()), true));
        FILTERS.put("party", new ChatFilter("party", (Text text) -> checkChar(text, '[', 0x64F581) && checkChar(text, '»', Formatting.GRAY.getColorValue()), true));
        FILTERS.put("dm", new ChatFilter("dm", (Text text) -> text.getString().startsWith("✉"), true));
        FILTERS.put("other", new ChatFilter("other", (Text text) -> {
            for(ChatFilter filter : FILTERS.values()) {
                if(Objects.equals(filter.getName(), "other")) continue;
                if(filter.checkText(text)) return  false;
            }
            return true;
        }, true));
    }

    private final String name;
    private final Predicate<Text> filter;
    private boolean status;

    public boolean checkText(Text text) {
        return filter.test(text);
    }

    public void enable() {
        status = true;
        ChatVisibilityController.chatRebuild();
    }
    public void disable() {
        status = false;
        ChatVisibilityController.chatRebuild();
    }

    public String getName() { return name; }
    public boolean getStatus() { return status; }
}