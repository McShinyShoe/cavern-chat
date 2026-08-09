package net.shinyshoe.cavernChat.client.ui.widget;

//? if >=1.21.9 {
import net.minecraft.client.gui.Click;
//?} else {
/*import net.minecraft.client.gui.screen.Screen;
*///?}
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.navigation.GuiNavigation;
import net.minecraft.client.gui.navigation.GuiNavigationPath;
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

    public FlatButton(int x, int y, Text label, int color, int width, int height, Runnable onClicked,
            Runnable onShiftClick, Runnable onCtrlClick) {
        super(x, y, width, height, Text.empty());
        this.label = label;
        this.color = color;
        this.onClicked = onClicked;
        this.onShiftClick = onShiftClick;
        this.onCtrlClick = onCtrlClick;
    }

    @Override
    protected void renderWidget(DrawContext ctx, int mouseX, int mouseY, float delta) {
        WidgetStyle.renderBackground(ctx, this, isHovered());
        WidgetStyle.renderLabel(ctx, this, label, color);
    }

    //? if >=1.21.9 {
    @Override
    public void onClick(Click click, boolean doubled) {
        if (!click.hasShift() && !click.hasCtrl())
            click();
        if (click.hasShift())
            this.onShiftClick.run();
        if (click.hasCtrl())
            this.onCtrlClick.run();
    }
    //?} else {
    /*@Override
    public void onClick(double mouseX, double mouseY) {
        if (!Screen.hasShiftDown() && !Screen.hasControlDown())
            click();
        if (Screen.hasShiftDown())
            this.onShiftClick.run();
        if (Screen.hasControlDown())
            this.onCtrlClick.run();
    }
    *///?}

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        builder.put(NarrationPart.TITLE, label);
    }

    public void click() {
        this.onClicked.run();
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

    public Text getLabel() {
        return label;
    }

    public void setLabel(Text label) {
        this.label = label;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
