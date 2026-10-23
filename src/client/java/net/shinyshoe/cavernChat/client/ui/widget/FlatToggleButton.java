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

public class FlatToggleButton extends AbstractWidget {

    private static final int DEFAULT_COLOR_ACTIVE = 0xFFFFFFFF;
    private static final int DEFAULT_COLOR_INACTIVE = 0xFFAAAAAA;

    private boolean enabled;
    private Component labelActive;
    private Component labelInactive;
    private int colorActive;
    private int colorInactive;
    private final Runnable onEnable;
    private final Runnable onDisable;
    private final Runnable onShiftClick;
    private final Runnable onCtrlClick;

    public FlatToggleButton(int x, int y, boolean enabled, Component label, int width, int height, Runnable onEnable,
            Runnable onDisable, Runnable onShiftClick, Runnable onCtrlClick) {
        this(x, y, enabled, label, label, DEFAULT_COLOR_ACTIVE, DEFAULT_COLOR_INACTIVE, width, height,
                onEnable, onDisable, onShiftClick, onCtrlClick);
    }

    public FlatToggleButton(int x, int y, boolean enabled, Component labelActive, Component labelInactive, int width,
            int height, Runnable onEnable, Runnable onDisable, Runnable onShiftClick, Runnable onCtrlClick) {
        this(x, y, enabled, labelActive, labelInactive, DEFAULT_COLOR_ACTIVE, DEFAULT_COLOR_INACTIVE, width, height,
                onEnable, onDisable, onShiftClick, onCtrlClick);
    }

    public FlatToggleButton(int x, int y, boolean enabled, Component label, int color, int width, int height,
            Runnable onEnable, Runnable onDisable, Runnable onShiftClick, Runnable onCtrlClick) {
        this(x, y, enabled, label, label, color, DEFAULT_COLOR_INACTIVE, width, height,
                onEnable, onDisable, onShiftClick, onCtrlClick);
    }

    public FlatToggleButton(int x, int y, boolean enabled, Component labelActive, Component labelInactive, int color,
            int width, int height, Runnable onEnable, Runnable onDisable, Runnable onShiftClick, Runnable onCtrlClick) {
        this(x, y, enabled, labelActive, labelInactive, color, DEFAULT_COLOR_INACTIVE, width, height,
                onEnable, onDisable, onShiftClick, onCtrlClick);
    }

    public FlatToggleButton(int x, int y, boolean enabled, Component label, int colorActive, int colorInactive,
            int width, int height, Runnable onEnable, Runnable onDisable, Runnable onShiftClick, Runnable onCtrlClick) {
        this(x, y, enabled, label, label, colorActive, colorInactive, width, height,
                onEnable, onDisable, onShiftClick, onCtrlClick);
    }

    public FlatToggleButton(int x, int y, boolean enabled, Component labelActive, Component labelInactive,
            int colorActive, int colorInactive, int width, int height, Runnable onEnable, Runnable onDisable,
            Runnable onShiftClick, Runnable onCtrlClick) {
        super(x, y, width, height, Component.empty());
        this.enabled = enabled;
        this.labelActive = labelActive;
        this.labelInactive = labelInactive;
        this.colorActive = colorActive;
        this.colorInactive = colorInactive;
        this.onEnable = onEnable;
        this.onDisable = onDisable;
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
        WidgetStyle.renderLabel(ctx, this, enabled ? labelActive : labelInactive,
                enabled ? colorActive : colorInactive);
    }

    //? if >=1.21.9 {
    /*@Override
    public void onClick(MouseButtonEvent click, boolean doubled) {
        if (!click.hasShiftDown() && !click.hasControlDown())
            toggle();
        if (click.hasShiftDown())
            this.onShiftClick.run();
        if (click.hasControlDown())
            this.onCtrlClick.run();
    }
    *///?} else {
    @Override
    public void onClick(double mouseX, double mouseY) {
        if (!Screen.hasShiftDown() && !Screen.hasControlDown())
            toggle();
        if (Screen.hasShiftDown())
            this.onShiftClick.run();
        if (Screen.hasControlDown())
            this.onCtrlClick.run();
    }
    //?}

    @Override
    protected void updateWidgetNarration(NarrationElementOutput builder) {
        builder.add(
                NarratedElementType.TITLE,
                Component.literal(labelActive.getString() + (enabled ? " enabled" : " disabled")));
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled != enabled)
            toggle();
    }

    public void toggle() {
        enabled = !enabled;
        if (enabled)
            onEnable.run();
        else
            onDisable.run();
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

    public Component getLabelActive() {
        return labelActive;
    }

    public Component getLabelInactive() {
        return labelInactive;
    }

    public void setLabelActive(Component labelActive) {
        this.labelActive = labelActive;
    }

    public void setLabelInactive(Component labelInactive) {
        this.labelInactive = labelInactive;
    }

    public void setLabel(Component label) {
        setLabelActive(label);
        setLabelInactive(label);
    }

    public int getColorActive() {
        return this.colorActive;
    }

    public int getColorInactive() {
        return this.colorInactive;
    }

    public void setColorActive(int color) {
        this.colorActive = color;
    }

    public void setColorInactive(int color) {
        this.colorInactive = color;
    }

    public void setColor(int color) {
        setColorActive(color);
        setColorInactive(color);
    }
}
