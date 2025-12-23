package net.shinyshoe.cavernChat.client.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

public class FlatToggleButton extends ClickableWidget {

    private boolean enabled;
    private final Text labelActive, labelInactive;
    private final int colorActive, colorInactive;
    private final Runnable onEnable;
    private final Runnable onDisable;
    private final Runnable onShiftClick;
    private final Runnable onCtrlClick;

    public FlatToggleButton(int x, int y, boolean enabled, Text label, int width, int height, Runnable onEnable, Runnable onDisable, Runnable onShiftClick, Runnable onCtrlClick) {
        super(x, y, width, height, Text.empty());
        this.enabled = enabled;
        this.labelActive = label;
        this.labelInactive = label;
        colorActive = 0xFFFFFFFF;
        colorInactive = 0xFFAAAAAA;
        this.onEnable = onEnable;
        this.onDisable = onDisable;
        this.onShiftClick = onShiftClick;
        this.onCtrlClick = onCtrlClick;
    }
    public FlatToggleButton(int x, int y, boolean enabled, Text labelActive, Text labelInactive, int width, int height, Runnable onEnable, Runnable onDisable, Runnable onShiftClick, Runnable onCtrlClick) {
        super(x, y, width, height, Text.empty());
        this.enabled = enabled;
        this.labelActive = labelActive;
        this.labelInactive = labelInactive;
        colorActive = 0xFFFFFFFF;
        colorInactive = 0xFFAAAAAA;
        this.onEnable = onEnable;
        this.onDisable = onDisable;
        this.onShiftClick = onShiftClick;
        this.onCtrlClick = onCtrlClick;
    }
    public FlatToggleButton(int x, int y, boolean enabled, Text label, int color, int width, int height, Runnable onEnable, Runnable onDisable, Runnable onShiftClick, Runnable onCtrlClick) {
        super(x, y, width, height, Text.empty());
        this.enabled = enabled;
        this.labelActive = label;
        this.labelInactive = label;
        this.colorActive = color;
        this.colorInactive = 0xFFAAAAAA;
        this.onEnable = onEnable;
        this.onDisable = onDisable;
        this.onShiftClick = onShiftClick;
        this.onCtrlClick = onCtrlClick;
    }
    public FlatToggleButton(int x, int y, boolean enabled, Text labelActive, Text labelInactive, int color, int width, int height, Runnable onEnable, Runnable onDisable, Runnable onShiftClick, Runnable onCtrlClick) {
        super(x, y, width, height, Text.empty());
        this.enabled = enabled;
        this.labelActive = labelActive;
        this.labelInactive = labelInactive;
        this.colorActive = color;
        this.colorInactive = 0xFFAAAAAA;
        this.onEnable = onEnable;
        this.onDisable = onDisable;
        this.onShiftClick = onShiftClick;
        this.onCtrlClick = onCtrlClick;
    }
    public FlatToggleButton(int x, int y, boolean enabled, Text label, int colorActive, int colorInactive, int width, int height, Runnable onEnable, Runnable onDisable, Runnable onShiftClick, Runnable onCtrlClick) {
        super(x, y, width, height, Text.empty());
        this.enabled = enabled;
        this.labelActive = label;
        this.labelInactive = label;
        this.colorActive = colorActive;
        this.colorInactive = colorInactive;
        this.onEnable = onEnable;
        this.onDisable = onDisable;
        this.onShiftClick = onShiftClick;
        this.onCtrlClick = onCtrlClick;
    }
    public FlatToggleButton(int x, int y, boolean enabled, Text labelActive, Text labelInactive, int colorActive, int colorInactive, int width, int height, Runnable onEnable, Runnable onDisable, Runnable onShiftClick, Runnable onCtrlClick) {
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
        int bgColor = this.isHovered() ?  0x80080808 : 0x80000000;
        ctx.fill(getX(), getY(), getX() + width, getY() + height, bgColor);

        int textX = getX() + width / 2;
        int textY = getY() + (height - 8) / 2;

        MinecraftClient client = MinecraftClient.getInstance();

        ctx.drawCenteredTextWithShadow(
                client.textRenderer,
                enabled ? labelActive : labelInactive,
                textX,
                textY,
                enabled ? colorActive: colorInactive
        );
    }

    @Override
    public void onClick(Click click, boolean doubled) {
        if(click.hasShift() && click.hasCtrl()) {

        }
        else if (click.hasShift()) {
            this.onShiftClick.run();
        }
        else if (click.hasCtrl()) {
            this.onCtrlClick.run();
        }
        else {
            toggle();
        }
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        builder.put(
                NarrationPart.TITLE,
                Text.literal("Global chat " + (enabled ? "enabled" : "disabled"))
        );
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        if(this.enabled != enabled) toggle();
    }

    public void toggle() {
        enabled = !enabled;
        if(enabled) onEnable.run();
        else onDisable.run();
    }

    @Override
    public net.minecraft.client.gui.navigation.GuiNavigationPath getNavigationPath(net.minecraft.client.gui.navigation.GuiNavigation navigation) {
        return null;
    }

    @Override
    public void setFocused(boolean focused) {
        if (!focused) {
            super.setFocused(false);
        }
    }

    public Text getLabelActive() {
        return labelActive;
    }

    public Text getLabelInactive() {
        return labelInactive;
    }
}
