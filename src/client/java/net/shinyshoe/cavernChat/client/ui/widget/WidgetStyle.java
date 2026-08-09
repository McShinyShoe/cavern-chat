package net.shinyshoe.cavernChat.client.ui.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

/** The shared look of the flat chat-bar widgets. */
final class WidgetStyle {

    private static final int BACKGROUND = 0x80000000;
    private static final int BACKGROUND_HOVERED = 0x80080808;
    private static final int TEXT_HEIGHT = 8;

    private WidgetStyle() {
    }

    static void renderBackground(DrawContext ctx, ClickableWidget widget, boolean hovered) {
        ctx.fill(
                widget.getX(),
                widget.getY(),
                widget.getX() + widget.getWidth(),
                widget.getY() + widget.getHeight(),
                hovered ? BACKGROUND_HOVERED : BACKGROUND);
    }

    static void renderLabel(DrawContext ctx, ClickableWidget widget, Text label, int color) {
        ctx.drawCenteredTextWithShadow(
                MinecraftClient.getInstance().textRenderer,
                label,
                widget.getX() + widget.getWidth() / 2,
                widget.getY() + (widget.getHeight() - TEXT_HEIGHT) / 2,
                color);
    }
}
