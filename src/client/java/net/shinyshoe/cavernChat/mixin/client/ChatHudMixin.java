package net.shinyshoe.cavernChat.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.shinyshoe.cavernChat.client.ChatChannel;
import net.shinyshoe.cavernChat.client.ChatFilter;
import net.shinyshoe.cavernChat.client.util.ChatUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
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

    @Inject(
            method = "addMessage(Lnet/minecraft/text/Text;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void addMessage(Text message, CallbackInfo ci) {
        String str = message.getString();
        if (str.contains("WELCOME TO THE CAVERN")) onJoinTowny();


        char chr = 0;
        if(ChatUtils.checkChar(message, '✎', Formatting.GOLD.getColorValue())) {
            chr = str.charAt(3);
        } else if(str.startsWith("You have switched")) {
            chr = str.charAt(21);
        }

        if(chr != 0) {
            switch (chr) {
                case 'g':
                    ChatChannel.setCurrentActiveChannel(ChatChannel.GLOBAL);
                    break;
                case 't':
                    ChatChannel.setCurrentActiveChannel(ChatChannel.TOWN);
                    break;
                case 'n':
                    ChatChannel.setCurrentActiveChannel(ChatChannel.NATION);
                    break;
                case 'l':
                    ChatChannel.setCurrentActiveChannel(ChatChannel.LOCAL);
                    break;
                case 'p':
                    ChatChannel.setCurrentActiveChannel(ChatChannel.PARTY);
                    break;
            }
        }
    }

    @Unique
    private void onJoinTowny() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        client.player.networkHandler.sendChatCommand("channel");
    }
}