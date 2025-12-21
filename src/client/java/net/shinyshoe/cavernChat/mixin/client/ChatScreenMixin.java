package net.shinyshoe.cavernChat.mixin.client;

import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.text.Text;
import net.shinyshoe.cavernChat.client.ui.FlatToggleButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin {

    @Inject(method = "init", at = @At("TAIL"))
    private void cavernChat$init(CallbackInfo ci) {
        ChatScreen self = (ChatScreen)(Object)this;

        FlatToggleButton buttonGlobal = new FlatToggleButton(2, self.height - 28, Text.of("Global"), 0xFFFCFCFC, 48, 12);
        ((ScreenAccessor) self).invokeAddDrawableChild(buttonGlobal);

        FlatToggleButton buttonLocal = new FlatToggleButton(52, self.height - 28, Text.of("Local"), 0xFFFCD400, 48, 12);
        ((ScreenAccessor) self).invokeAddDrawableChild(buttonLocal);

        FlatToggleButton buttonParty = new FlatToggleButton(104, self.height - 28, Text.of("Party"), 0xFF63f27f, 48, 12);
        ((ScreenAccessor) self).invokeAddDrawableChild(buttonParty);

        FlatToggleButton buttonTown = new FlatToggleButton(156, self.height - 28, Text.of("Town"), 0xFF54fc54, 48, 12);
        ((ScreenAccessor) self).invokeAddDrawableChild(buttonTown);

        FlatToggleButton buttonNation = new FlatToggleButton(208, self.height - 28, Text.of("Nation"), 0xFFfca800, 48, 12);
        ((ScreenAccessor) self).invokeAddDrawableChild(buttonNation);
    }
}
