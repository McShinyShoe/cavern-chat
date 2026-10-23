package net.shinyshoe.cavernChat.mixin.client;

//? if >=26.1 {
/*import net.minecraft.client.multiplayer.chat.GuiMessage;
*///?} else {
import net.minecraft.client.GuiMessage;
//?}
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import net.shinyshoe.cavernChat.client.channel.ChannelTracker;
import net.shinyshoe.cavernChat.client.message.ChatHistory;
import net.shinyshoe.cavernChat.client.util.ServerUtils;
import net.shinyshoe.cavernChat.mixin.client.accessor.ChatHudAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatComponent.class)
public abstract class ChatHudMixin {

    /**
     * If in cavern, dont add chat to VisibleMessages, but rather put it to
     * ChatHistory and decide from there
     */
    //? if >=26.1 {
    /*@Redirect(method = "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/multiplayer/chat/GuiMessageSource;Lnet/minecraft/client/multiplayer/chat/GuiMessageTag;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent;addMessageToDisplayQueue(Lnet/minecraft/client/multiplayer/chat/GuiMessage;)V"))
    *///?} else {
    @Redirect(method = "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent;addMessageToDisplayQueue(Lnet/minecraft/client/GuiMessage;)V"))
    //?}
    private void cavernChat$routeMessage(ChatComponent instance, GuiMessage line) {
        if (!ServerUtils.isCavern()) {
            ((ChatHudAccessor) instance).invokeAddVisibleMessage(line);
            return;
        }

        ChatHistory.add(line);
    }

    /**
     * If in cavern, addMessage calls ChatTracker.readFrom(message) to constantly
     * check if there is channel changes
     * (26.1 split it into a client and a server version, so we listen to both)
     */
    //? if >=26.1 {
    /*@Inject(method = { "addClientSystemMessage", "addServerSystemMessage" }, at = @At("HEAD"))
    *///?} else {
    @Inject(method = "addMessage(Lnet/minecraft/network/chat/Component;)V", at = @At("HEAD"))
    //?}
    private void cavernChat$trackChannel(Component message, CallbackInfo ci) {
        if (!ServerUtils.isCavern())
            return;

        ChannelTracker.readFrom(message);
    }
}
