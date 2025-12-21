package net.shinyshoe.cavernChat.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.text.Text;
import net.shinyshoe.cavernChat.CavernChat;
import net.shinyshoe.cavernChat.client.ChatFilter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public abstract class ChatHudMixin {
    @Inject(
            method = "addVisibleMessage(Lnet/minecraft/client/gui/hud/ChatHudLine;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onAddVisibleMessage(ChatHudLine message, CallbackInfo ci) {
        Text content = message.content();
        // REMOVE THIS LATER
        CavernChat.LOGGER.info("Data: {}", message.content().toString());

        for (ChatFilter filter : ChatFilter.FILTERS.values()) {
            if(!filter.getStatus()) continue;
            if(filter.checkText(content)) {
                return;
            }
        }
        ci.cancel();
    }

    @Inject(
            method = "render",
            at = @At("HEAD")
    )
    private void onRender(DrawContext context, int currentTick, int mouseX, int mouseY, boolean focused, CallbackInfo ci) {

    }

    @Inject(method = "reset()V", at = @At("HEAD"))
    private void onReset(CallbackInfo ci) {
    }
}