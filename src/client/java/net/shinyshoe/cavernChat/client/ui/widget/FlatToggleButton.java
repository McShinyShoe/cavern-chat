package net.shinyshoe.cavernChat.client.ui.widget;

import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.navigation.GuiNavigation;
import net.minecraft.client.gui.navigation.GuiNavigationPath;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

public class FlatToggleButton extends ClickableWidget {

    private static final int DEFAULT_COLOR_ACTIVE = 0xFFFFFFFF;
    private static final int DEFAULT_COLOR_INACTIVE = 0xFFAAAAAA;

    private boolean enabled;
    private Text labelActive;
    private Text labelInactive;
    private int colorActive;
    private int colorInactive;
    private final Runnable onEnable;
    private final Runnable onDisable;
    private final Runnable onShiftClick;
    private final Runnable onCtrlClick;

    public FlatToggleButton(int x, int y, boolean enabled, Text label, int width, int height, Runnable onEnable,
            Runnable onDisable, Runnable onShiftClick, Runnable onCtrlClick) {
        this(x, y, enabled, label, label, DEFAULT_COLOR_ACTIVE, DEFAULT_COLOR_INACTIVE, width, height,
                onEnable, onDisable, onShiftClick, onCtrlClick);
    }

    public FlatToggleButton(int x, int y, boolean enabled, Text labelActive, Text labelInactive, int width, int height,
            Runnable onEnable, Runnable onDisable, Runnable onShiftClick, Runnable onCtrlClick) {
        this(x, y, enabled, labelActive, labelInactive, DEFAULT_COLOR_ACTIVE, DEFAULT_COLOR_INACTIVE, width, height,
                onEnable, onDisable, onShiftClick, onCtrlClick);
    }

    public FlatToggleButton(int x, int y, boolean enabled, Text label, int color, int width, int height,
            Runnable onEnable, Runnable onDisable, Runnable onShiftClick, Runnable onCtrlClick) {
        this(x, y, enabled, label, label, color, DEFAULT_COLOR_INACTIVE, width, height,
                onEnable, onDisable, onShiftClick, onCtrlClick);
    }

    public FlatToggleButton(int x, int y, boolean enabled, Text labelActive, Text labelInactive, int color, int width,
            int height, Runnable onEnable, Runnable onDisable, Runnable onShiftClick, Runnable onCtrlClick) {
        this(x, y, enabled, labelActive, labelInactive, color, DEFAULT_COLOR_INACTIVE, width, height,
                onEnable, onDisable, onShiftClick, onCtrlClick);
    }

    public FlatToggleButton(int x, int y, boolean enabled, Text label, int colorActive, int colorInactive, int width,
            int height, Runnable onEnable, Runnable onDisable, Runnable onShiftClick, Runnable onCtrlClick) {
        this(x, y, enabled, label, label, colorActive, colorInactive, width, height,
                onEnable, onDisable, onShiftClick, onCtrlClick);
    }

    public FlatToggleButton(int x, int y, boolean enabled, Text labelActive, Text labelInactive, int colorActive,
            int colorInactive, int width, int height, Runnable onEnable, Runnable onDisable, Runnable onShiftClick,
            Runnable onCtrlClick) {
        super(x, y, width, height, Text.empty());
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
    protected void renderWidget(DrawContext ctx, int mouseX, int mouseY, float delta) {
        WidgetStyle.renderBackground(ctx, this, isHovered());
        WidgetStyle.renderLabel(ctx, this, enabled ? labelActive : labelInactive,
                enabled ? colorActive : colorInactive);
    }

    @Override
    public void onClick(Click click, boolean doubled) {
        if (!click.hasShift() && !click.hasCtrl())
            toggle();
        if (click.hasShift())
            this.onShiftClick.run();
        if (click.hasCtrl())
            this.onCtrlClick.run();
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        builder.put(
                NarrationPart.TITLE,
                Text.literal(labelActive.getString() + (enabled ? " enabled" : " disabled")));
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
    public GuiNavigationPath getNavigationPath(GuiNavigation navigation) {
        return null;
    }

    @Override
    public void setFocused(boolean focused) {
        if (!focused)
            super.setFocused(false);
    }

    public Text getLabelActive() {
        return labelActive;
    }

    public Text getLabelInactive() {
        return labelInactive;
    }

    public void setLabelActive(Text labelActive) {
        this.labelActive = labelActive;
    }

    public void setLabelInactive(Text labelInactive) {
        this.labelInactive = labelInactive;
    }

    public void setLabel(Text label) {
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
