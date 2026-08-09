package net.shinyshoe.cavernChat.client.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.shinyshoe.cavernChat.client.config.CavernChatConfig;
import net.shinyshoe.cavernChat.client.filter.ChatType;
import net.shinyshoe.cavernChat.client.filter.ChatVisibility;
import net.shinyshoe.cavernChat.client.message.ChatHistory;
import net.shinyshoe.cavernChat.client.ui.widget.FlatButton;
import net.shinyshoe.cavernChat.client.ui.widget.FlatToggleButton;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * The button above chat input to list the available channel filters.
 */
public final class ChannelBar {

    private static final int MARGIN = 2;
    private static final int GAP = 2;
    private static final int BUTTON_HEIGHT = 12;

    private static final int TOGGLE_ROW_OFFSET = 28;
    private static final int INDICATOR_ROW_OFFSET = 14;

    private static final String RESET_LABEL = "🗘";
    private static final int RESET_WIDTH = 12;
    private static final int RESET_COLOR = 0xFFFFFFFF;

    private static final int INDICATOR_COLOR = 0xFFFFFFFF;

    private static final Runnable NOTHING = () -> {
    };

    private record ChannelToggle(ChatType type, String label, int color, int width, String switchCommand,
            Consumer<Boolean> setting) {
    }

    private static final List<ChannelToggle> TOGGLES = List.of(
            new ChannelToggle(ChatType.MESSAGE_GLOBAL, "🌏 Global", 0xFFFCFCFC, 50, "g",
                    visible -> channels().globalMessages = visible),
            new ChannelToggle(ChatType.MESSAGE_LOCAL, "⛳ Local", 0xFFFCD400, 46, "lc",
                    visible -> channels().localMessages = visible),
            new ChannelToggle(ChatType.MESSAGE_PARTY, "🎂 Party", 0xFF63f27f, 46, "pc",
                    visible -> channels().partyMessages = visible),
            new ChannelToggle(ChatType.MESSAGE_TOWN, "🏚 Town", 0xFF54fc54, 42, "tc",
                    visible -> channels().townMessages = visible),
            new ChannelToggle(ChatType.MESSAGE_NATION, "⚑ Nation", 0xFFfca800, 48, "nc",
                    visible -> channels().nationMessages = visible),
            new ChannelToggle(ChatType.MESSAGE_DM, "✉ DM", 0xFFEEC65D, 30, null,
                    visible -> channels().dmMessages = visible),
            new ChannelToggle(ChatType.MESSAGE_OTHER, "Other", 0xFFFFFFFF, 34, null,
                    visible -> channels().otherMessages = visible));

    public record Widgets(FlatButton reset, FlatToggleButton indicator, List<FlatToggleButton> toggles) {
    }

    private ChannelBar() {
    }

    public static Widgets create(int screenHeight) {
        int y = screenHeight - TOGGLE_ROW_OFFSET;
        List<FlatToggleButton> toggles = new ArrayList<>();

        int x = MARGIN;
        for (int i = 0; i < TOGGLES.size(); i++) {
            ChannelToggle toggle = TOGGLES.get(i);
            int index = i;

            toggles.add(new FlatToggleButton(
                    x,
                    y,
                    ChatVisibility.isVisible(toggle.type()),
                    Text.of(toggle.label()),
                    toggle.color(),
                    toggle.width(),
                    BUTTON_HEIGHT,
                    () -> setVisible(toggle, true),
                    () -> setVisible(toggle, false),
                    () -> switchChannel(toggle.switchCommand()),
                    () -> showOnly(toggles, index)));

            x += toggle.width() + GAP;
        }

        FlatButton reset = new FlatButton(
                x,
                y,
                Text.of(RESET_LABEL),
                RESET_COLOR,
                RESET_WIDTH,
                BUTTON_HEIGHT,
                () -> toggles.forEach(button -> button.setEnabled(true)),
                NOTHING,
                NOTHING);

        return new Widgets(reset, createIndicator(screenHeight), toggles);
    }

    private static FlatToggleButton createIndicator(int screenHeight) {
        return new FlatToggleButton(
                MARGIN,
                screenHeight - INDICATOR_ROW_OFFSET,
                true,
                Text.of(""),
                INDICATOR_COLOR,
                INDICATOR_COLOR,
                0,
                BUTTON_HEIGHT,
                NOTHING,
                NOTHING,
                NOTHING,
                NOTHING);
    }

    private static void setVisible(ChannelToggle toggle, boolean visible) {
        ChatVisibility.setCascading(toggle.type(), visible);
        ChatHistory.rebuild();
        toggle.setting().accept(visible);
    }

    private static void switchChannel(String command) {
        if (command == null)
            return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null)
            return;

        client.player.networkHandler.sendChatCommand(command);
    }

    private static void showOnly(List<FlatToggleButton> toggles, int index) {
        for (int i = 0; i < toggles.size(); i++)
            toggles.get(i).setEnabled(i == index);
    }

    private static CavernChatConfig.ChatChannels channels() {
        return CavernChatConfig.getInstance().chatChannels;
    }
}
