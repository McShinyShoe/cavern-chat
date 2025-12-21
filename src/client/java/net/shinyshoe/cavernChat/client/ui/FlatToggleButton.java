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
    private final Text labelActive, labelInctive;
    private final int colorActive, colorInactive;

    public FlatToggleButton(int x, int y, Text label, int width, int height) {
        super(x, y, width, height, Text.empty());
        this.labelActive = label;
        this.labelInctive = label;
        colorActive = 0xFFFFFFFF;
        colorInactive = 0xFFAAAAAA;
    }
    public FlatToggleButton(int x, int y, Text labelActive, Text labelInctive, int width, int height) {
        super(x, y, width, height, Text.empty());
        this.labelActive = labelActive;
        this.labelInctive = labelInctive;
        colorActive = 0xFFFFFFFF;
        colorInactive = 0xFFAAAAAA;
    }
    public FlatToggleButton(int x, int y, Text label, int color, int width, int height) {
        super(x, y, width, height, Text.empty());
        this.labelActive = label;
        this.labelInctive = label;
        this.colorActive = color;
        this.colorInactive = 0xFFAAAAAA;
    }
    public FlatToggleButton(int x, int y, Text labelActive, Text labelInctive, int color, int width, int height) {
        super(x, y, width, height, Text.empty());
        this.labelActive = labelActive;
        this.labelInctive = labelInctive;
        this.colorActive = color;
        this.colorInactive = 0xFFAAAAAA;
    }
    public FlatToggleButton(int x, int y, Text label, int colorActive, int colorInactive, int width, int height) {
        super(x, y, width, height, Text.empty());
        this.labelActive = label;
        this.labelInctive = label;
        this.colorActive = colorActive;
        this.colorInactive = colorInactive;
    }
    public FlatToggleButton(int x, int y, Text labelActive, Text labelInctive, int colorActive, int colorInactive, int width, int height) {
        super(x, y, width, height, Text.empty());
        this.labelActive = labelActive;
        this.labelInctive = labelInctive;
        this.colorActive = colorActive;
        this.colorInactive = colorInactive;
    }

    @Override
    protected void renderWidget(DrawContext ctx, int mouseX, int mouseY, float delta) {
        // Black background, 80% opacity
        int bgColor = 0x80000000;
        ctx.fill(getX(), getY(), getX() + width, getY() + height, bgColor);

        int textX = getX() + width / 2;
        int textY = getY() + (height - 8) / 2;

        MinecraftClient client = MinecraftClient.getInstance();

        ctx.drawCenteredTextWithShadow(
                client.textRenderer,
                enabled ? labelActive : labelInctive,
                textX,
                textY,
                enabled ? colorActive: colorInactive
        );
    }

    @Override
    public void onClick(Click click, boolean doubled) {
        enabled = !enabled;
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
}
