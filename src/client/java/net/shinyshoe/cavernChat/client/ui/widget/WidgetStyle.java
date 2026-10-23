package net.shinyshoe.cavernChat.client.ui.widget;

//? if >=26.1 {
/*import net.minecraft.client.gui.GuiGraphicsExtractor;
*///?} else {
import net.minecraft.client.gui.GuiGraphics;
//?}
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;

/** The shared look of the flat chat-bar widgets. */
final class WidgetStyle {

    private static final int BACKGROUND = 0x80000000;
    private static final int BACKGROUND_HOVERED = 0x80080808;
    private static final int TEXT_HEIGHT = 8;

    private WidgetStyle() {
    }

    //? if >=26.1 {
    /*static void renderBackground(GuiGraphicsExtractor ctx, AbstractWidget widget, boolean hovered) {
    *///?} else {
    static void renderBackground(GuiGraphics ctx, AbstractWidget widget, boolean hovered) {
    //?}
        ctx.fill(
                widget.getX(),
                widget.getY(),
                widget.getX() + widget.getWidth(),
                widget.getY() + widget.getHeight(),
                hovered ? BACKGROUND_HOVERED : BACKGROUND);
    }

    //? if >=26.1 {
    /*static void renderLabel(GuiGraphicsExtractor ctx, AbstractWidget widget, Component label, int color) {
        ctx.centeredText(
    *///?} else {
    static void renderLabel(GuiGraphics ctx, AbstractWidget widget, Component label, int color) {
        ctx.drawCenteredString(
    //?}
                Minecraft.getInstance().font,
                label,
                widget.getX() + widget.getWidth() / 2,
                widget.getY() + (widget.getHeight() - TEXT_HEIGHT) / 2,
                color);
    }
}
