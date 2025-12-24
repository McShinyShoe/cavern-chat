package net.shinyshoe.cavernChat.client.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

public class FlatButton extends ClickableWidget {

    private Text label;
    private int color;
    private final Runnable onClicked;
    private final Runnable onShiftClick;
    private final Runnable onCtrlClick;

    public FlatButton(int x, int y, Text label, int color, int width, int height, Runnable onClicked, Runnable onShiftClick, Runnable onCtrlClick) {
        super(x, y, width, height, Text.empty());
        this.label = label;
        this.color = color;
        this.onClicked = onClicked;
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
                label,
                textX,
                textY,
                color
        );
    }

    @Override
    public void onClick(Click click, boolean doubled) {
        if(!click.hasShift() && !click.hasCtrl()) {
            click();
        }
        if (click.hasShift()) {
            this.onShiftClick.run();
        }
        if (click.hasCtrl()) {
            this.onCtrlClick.run();
        }
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        builder.put(
                NarrationPart.TITLE,
                Text.literal("Button clicked")
        );
    }

    public void click() {
        this.onClicked.run();
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

    public Text getLabel() { return label; }
    public void setLabel(Text label) { this.label = label; }
    public int getColorActive() {return this.color; }
    public void setColor(int color) { this.color = color; }
}
