package net.shinyshoe.cavernChat.mixin.client;

import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.text.Text;
import net.shinyshoe.cavernChat.client.ChatFilter;
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

        FlatToggleButton buttonGlobal = new FlatToggleButton(
                2,
                self.height - 28,
                ChatFilter.FILTERS.get("global").getStatus(),
                Text.of("Global"),
                0xFFFCFCFC,
                48,
                12,
                () -> ChatFilter.FILTERS.get("global").enable(),
                () -> ChatFilter.FILTERS.get("global").disable()
        );
        ((ScreenAccessor) self).invokeAddDrawableChild(buttonGlobal);

        FlatToggleButton buttonLocal = new FlatToggleButton(
                52,
                self.height - 28,
                ChatFilter.FILTERS.get("local").getStatus(),
                Text.of("Local"),
                0xFFFCD400,
                48,
                12,
                () -> ChatFilter.FILTERS.get("local").enable(),
                () -> ChatFilter.FILTERS.get("local").disable()
        );
        ((ScreenAccessor) self).invokeAddDrawableChild(buttonLocal);

        FlatToggleButton buttonParty = new FlatToggleButton(
                104,
                self.height - 28,
                ChatFilter.FILTERS.get("party").getStatus(),
                Text.of("Party"),
                0xFF63f27f,
                48,
                12,
                () -> ChatFilter.FILTERS.get("party").enable(),
                () -> ChatFilter.FILTERS.get("party").disable()
        );
        ((ScreenAccessor) self).invokeAddDrawableChild(buttonParty);

        FlatToggleButton buttonTown = new FlatToggleButton(
                156,
                self.height - 28,
                ChatFilter.FILTERS.get("town").getStatus(),
                Text.of("Town"),
                0xFF54fc54,
                48,
                12,
                () -> ChatFilter.FILTERS.get("town").enable(),
                () -> ChatFilter.FILTERS.get("town").disable()
        );
        ((ScreenAccessor) self).invokeAddDrawableChild(buttonTown);

        FlatToggleButton buttonNation = new FlatToggleButton(
                208,
                self.height - 28,
                ChatFilter.FILTERS.get("nation").getStatus(),
                Text.of("Nation"),
                0xFFfca800,
                48,
                12,
                () -> ChatFilter.FILTERS.get("nation").enable(),
                () -> ChatFilter.FILTERS.get("nation").disable()
        );
        ((ScreenAccessor) self).invokeAddDrawableChild(buttonNation);

        FlatToggleButton buttonDM = new FlatToggleButton(
                260,
                self.height - 28,
                ChatFilter.FILTERS.get("dm").getStatus(),
                Text.of("DM"),
                0xFFfca800,
                48,
                12,
                () -> ChatFilter.FILTERS.get("dm").enable(),
                () -> ChatFilter.FILTERS.get("dm").disable()
        );
        ((ScreenAccessor) self).invokeAddDrawableChild(buttonDM);

        FlatToggleButton buttonOther = new FlatToggleButton(
                312,
                self.height - 28,
                ChatFilter.FILTERS.get("other").getStatus(),
                Text.of("Other"),
                0xFFfca800,
                48,
                12,
                () -> ChatFilter.FILTERS.get("other").enable(),
                () -> ChatFilter.FILTERS.get("other").disable()
        );
        ((ScreenAccessor) self).invokeAddDrawableChild(buttonOther);
    }
}
