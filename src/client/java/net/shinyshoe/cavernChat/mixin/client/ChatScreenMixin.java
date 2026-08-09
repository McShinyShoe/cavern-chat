package net.shinyshoe.cavernChat.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
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
    protected TextFieldWidget chatField;

    @Unique
    private FlatToggleButton cavernChat$channelIndicator;

    @Inject(method = "init", at = @At("TAIL"))
    private void cavernChat$addChannelBar(CallbackInfo ci) {
        if (!ServerUtils.isCavern())
            return;

        ChatScreen self = (ChatScreen) (Object) this;
        if (MinecraftClient.getInstance().player == null)
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

    @Inject(method = "render", at = @At("HEAD"))
    private void cavernChat$updateIndicator(DrawContext context, int mouseX, int mouseY, float deltaTicks,
            CallbackInfo ci) {
        ChannelIndicator.update();
    }

    /** Sizes the channel tag and slides the chat box out from behind it. */
    @Inject(method = "render", at = @At("TAIL"))
    private void cavernChat$layoutChatField(DrawContext context, int mouseX, int mouseY, float deltaTicks,
            CallbackInfo ci) {
        if (!ServerUtils.isCavern())
            return;

        this.chatField.setEditableColor(ChannelIndicator.getEditableColor());

        Text label = ChannelIndicator.getText();
        if (label != null) {
            cavernChat$channelIndicator.setLabel(label);
            cavernChat$channelIndicator.setDimensions(
                    ChannelIndicator.getOffset(),
                    cavernChat$channelIndicator.getHeight());

            Integer labelColor = ChannelIndicator.getLabelColor();
            if (labelColor != null)
                cavernChat$channelIndicator.setColor(labelColor);
        }

        int offset = ChannelIndicator.getLayoutOffset();
        this.chatField.setX(4 + offset);
        this.chatField.setWidth(((ChatScreen) (Object) this).width - 4 - offset);

        cavernChat$channelIndicator.visible = ChannelIndicator.getOffset() != 0;
    }

    /**
     * Off The Cavern the vanilla input background is redrawn at full width; on
     * it, the left edge is pulled in to leave room for the channel tag.
     */
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V"))
    private void cavernChat$insetInputBackground(DrawContext context, int x1, int y1, int x2, int y2, int color) {
        if (!ServerUtils.isCavern()) {
            MinecraftClient client = MinecraftClient.getInstance();
            assert client.currentScreen != null;
            context.fill(2, client.currentScreen.height - 14, client.currentScreen.width - 2,
                    client.currentScreen.height - 2, client.options.getTextBackgroundColor(Integer.MIN_VALUE));
            return;
        }

        context.fill(x1 + ChannelIndicator.getLayoutOffset(), y1, x2, y2, color);
    }
}
