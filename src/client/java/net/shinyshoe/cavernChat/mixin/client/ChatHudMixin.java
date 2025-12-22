package net.shinyshoe.cavernChat.mixin.client;

import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.text.Text;
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

        for (ChatFilter filter : ChatFilter.FILTERS.values()) {
            if(!filter.getStatus()) continue;
            if(filter.checkText(content)) {
                return;
            }
        }
        ci.cancel();
    }
}