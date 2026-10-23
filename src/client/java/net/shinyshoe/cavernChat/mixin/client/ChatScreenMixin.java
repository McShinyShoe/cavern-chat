package net.shinyshoe.cavernChat.mixin.client;

//? if >=26.1 {
/*import net.minecraft.client.gui.GuiGraphicsExtractor;
*///?} else {
import net.minecraft.client.gui.GuiGraphics;
//?}
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.network.chat.Component;
import net.shinyshoe.cavernChat.client.ui.ChannelBar;
import net.shinyshoe.cavernChat.client.ui.ChannelIndicator;
import net.shinyshoe.cavernChat.client.ui.widget.FlatToggleButton;
import net.shinyshoe.cavernChat.client.util.ServerUtils;
import net.shinyshoe.cavernChat.mixin.client.accessor.ScreenAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin {

    @Shadow
    protected EditBox input;

    @Unique
    private FlatToggleButton cavernChat$channelIndicator;

    @Inject(method = "init", at = @At("TAIL"))
    private void cavernChat$addChannelBar(CallbackInfo ci) {
        if (!ServerUtils.isCavern())
            return;

        ChatScreen self = (ChatScreen) (Object) this;
        if (Minecraft.getInstance().player == null)
            return;

        ChannelBar.Widgets bar = ChannelBar.create(self.height);
        ScreenAccessor screen = (ScreenAccessor) self;

        screen.invokeAddDrawableChild(bar.reset());

        cavernChat$channelIndicator = bar.indicator();
        cavernChat$channelIndicator.visible = false;
        screen.invokeAddDrawableChild(cavernChat$channelIndicator);

        for (FlatToggleButton toggle : bar.toggles())
            screen.invokeAddDrawableChild(toggle);
    }

    //? if >=26.1 {
    /*@Inject(method = "extractRenderState", at = @At("HEAD"))
    private void cavernChat$updateIndicator(GuiGraphicsExtractor context, int mouseX, int mouseY, float deltaTicks,
    *///?} else {
    @Inject(method = "render", at = @At("HEAD"))
    private void cavernChat$updateIndicator(GuiGraphics context, int mouseX, int mouseY, float deltaTicks,
    //?}
            CallbackInfo ci) {
        ChannelIndicator.update();
    }

    /** Sizes the channel tag and slides the chat box out from behind it. */
    //? if >=26.1 {
    /*@Inject(method = "extractRenderState", at = @At("TAIL"))
    private void cavernChat$layoutChatField(GuiGraphicsExtractor context, int mouseX, int mouseY, float deltaTicks,
    *///?} else {
    @Inject(method = "render", at = @At("TAIL"))
    private void cavernChat$layoutChatField(GuiGraphics context, int mouseX, int mouseY, float deltaTicks,
    //?}
            CallbackInfo ci) {
        if (!ServerUtils.isCavern())
            return;

        this.input.setTextColor(ChannelIndicator.getEditableColor());

        Component label = ChannelIndicator.getText();
        if (label != null) {
            cavernChat$channelIndicator.setLabel(label);
            cavernChat$channelIndicator.setSize(
                    ChannelIndicator.getOffset(),
                    cavernChat$channelIndicator.getHeight());

            Integer labelColor = ChannelIndicator.getLabelColor();
            if (labelColor != null)
                cavernChat$channelIndicator.setColor(labelColor);
        }

        int offset = ChannelIndicator.getLayoutOffset();
        this.input.setX(4 + offset);
        this.input.setWidth(((ChatScreen) (Object) this).width - 4 - offset);

        cavernChat$channelIndicator.visible = ChannelIndicator.getOffset() != 0;
    }

    /**
     * Off The Cavern the vanilla input background is redrawn at full width; on
     * it, the left edge is pulled in to leave room for the channel tag.
     */
    //? if >=26.1 {
    /*@Redirect(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;fill(IIIII)V"))
    private void cavernChat$insetInputBackground(GuiGraphicsExtractor context, int x1, int y1, int x2, int y2,
            int color) {
    *///?} else {
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"))
    private void cavernChat$insetInputBackground(GuiGraphics context, int x1, int y1, int x2, int y2, int color) {
    //?}
        if (!ServerUtils.isCavern()) {
            context.fill(x1, y1, x2, y2, color);
            return;
        }

        context.fill(x1 + ChannelIndicator.getLayoutOffset(), y1, x2, y2, color);
    }
}
