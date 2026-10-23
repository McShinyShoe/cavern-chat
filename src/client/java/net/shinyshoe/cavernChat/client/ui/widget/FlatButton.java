package net.shinyshoe.cavernChat.client.ui.widget;

//? if >=1.21.9 {
/*import net.minecraft.client.input.MouseButtonEvent;
*///?} else {
import net.minecraft.client.gui.screens.Screen;
//?}
//? if >=26.1 {
/*import net.minecraft.client.gui.GuiGraphicsExtractor;
*///?} else {
import net.minecraft.client.gui.GuiGraphics;
//?}
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.network.chat.Component;

public class FlatButton extends AbstractWidget {

    private Component label;
    private int color;
    private final Runnable onClicked;
    private final Runnable onShiftClick;
    private final Runnable onCtrlClick;

    public FlatButton(int x, int y, Component label, int color, int width, int height, Runnable onClicked,
            Runnable onShiftClick, Runnable onCtrlClick) {
        super(x, y, width, height, Component.empty());
        this.label = label;
        this.color = color;
        this.onClicked = onClicked;
        this.onShiftClick = onShiftClick;
        this.onCtrlClick = onCtrlClick;
    }

    @Override
    //? if >=26.1 {
    /*protected void extractWidgetRenderState(GuiGraphicsExtractor ctx, int mouseX, int mouseY, float delta) {
    *///?} else {
    protected void renderWidget(GuiGraphics ctx, int mouseX, int mouseY, float delta) {
    //?}
        WidgetStyle.renderBackground(ctx, this, isHovered());
        WidgetStyle.renderLabel(ctx, this, label, color);
    }

    //? if >=1.21.9 {
    /*@Override
    public void onClick(MouseButtonEvent click, boolean doubled) {
        if (!click.hasShiftDown() && !click.hasControlDown())
            click();
        if (click.hasShiftDown())
            this.onShiftClick.run();
        if (click.hasControlDown())
            this.onCtrlClick.run();
    }
    *///?} else {
    @Override
    public void onClick(double mouseX, double mouseY) {
        if (!Screen.hasShiftDown() && !Screen.hasControlDown())
            click();
        if (Screen.hasShiftDown())
            this.onShiftClick.run();
        if (Screen.hasControlDown())
            this.onCtrlClick.run();
    }
    //?}

    @Override
    protected void updateWidgetNarration(NarrationElementOutput builder) {
        builder.add(NarratedElementType.TITLE, label);
    }

    public void click() {
        this.onClicked.run();
    }

    @Override
    public ComponentPath nextFocusPath(FocusNavigationEvent navigation) {
        return null;
    }

    @Override
    public void setFocused(boolean focused) {
        if (!focused)
            super.setFocused(false);
    }

    public Component getLabel() {
        return label;
    }

    public void setLabel(Component label) {
        this.label = label;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
