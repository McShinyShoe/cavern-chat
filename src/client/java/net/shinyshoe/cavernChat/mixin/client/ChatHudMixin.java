package net.shinyshoe.cavernChat.mixin.client;

import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.text.Text;
import net.shinyshoe.cavernChat.client.channel.ChannelTracker;
import net.shinyshoe.cavernChat.client.message.ChatHistory;
import net.shinyshoe.cavernChat.client.util.ServerUtils;
import net.shinyshoe.cavernChat.mixin.client.accessor.ChatHudAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public abstract class ChatHudMixin {

    /**
     * If in cavern, dont add chat to VisibleMessages, but rather put it to
     * ChatHistory and decide from there
     */
    @Redirect(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/ChatHud;addVisibleMessage(Lnet/minecraft/client/gui/hud/ChatHudLine;)V"))
    private void cavernChat$routeMessage(ChatHud instance, ChatHudLine line) {
        if (!ServerUtils.isCavern()) {
            ((ChatHudAccessor) instance).invokeAddVisibleMessage(line);
            return;
        }

        ChatHistory.add(line);
    }

    /**
     * If in cavern, addMessage calls ChatTracker.readFrom(message) to constantly
     * check if there is channel changes
     */
    @Inject(method = "addMessage(Lnet/minecraft/text/Text;)V", at = @At("HEAD"))
    private void cavernChat$trackChannel(Text message, CallbackInfo ci) {
        if (!ServerUtils.isCavern())
            return;

        ChannelTracker.readFrom(message);
    }
}
