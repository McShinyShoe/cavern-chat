package net.shinyshoe.cavernChat.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.shinyshoe.cavernChat.CavernChat;
import net.shinyshoe.cavernChat.client.ChatChannel;
import net.shinyshoe.cavernChat.client.ChatFilter;
import net.shinyshoe.cavernChat.client.ui.FlatToggleButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin {

    @Inject(method = "init", at = @At("TAIL"))
    private void cavernChat$init(CallbackInfo ci) {
        ChatScreen self = (ChatScreen)(Object)this;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        List<FlatToggleButton> buttonList = new ArrayList<>();

        if (client.getServer() != null) CavernChat.LOGGER.info("in on {}", client.getServer().getName());

        for (Field field : ChatScreen.class.getDeclaredFields()) {
            if (TextFieldWidget.class.isAssignableFrom(field.getType())) {
                field.setAccessible(true);
                try {
                    TextFieldWidget input = (TextFieldWidget) field.get(self);
                    ChatChannel channel = ChatChannel.getCurrentActiveChannel();
                    if(channel == null) channel = ChatChannel.getCurrentDefaultChannel();
                    if(channel == null) break;

                    if (input != null) {
                        input.setEditableColor(0xFF000000 + channel.color());
                    }
                } catch (IllegalAccessException ignored) {}
                break;
            }
        }

        buttonList.add(new FlatToggleButton(
                2,
                self.height - 28,
                ChatFilter.FILTERS.get("global").getStatus(),
                Text.of("Global"),
                0xFFFCFCFC,
                48,
                12,
                () -> ChatFilter.FILTERS.get("global").enable(),
                () -> ChatFilter.FILTERS.get("global").disable(),
                () -> client.player.networkHandler.sendChatCommand("g"),
                () -> buttonList.forEach(button -> { if(!Objects.equals(button.getLabelActive().getString(), "Global")) button.setEnabled(false); else button.setEnabled(true); })
        ));

        buttonList.add(new FlatToggleButton(
                52,
                self.height - 28,
                ChatFilter.FILTERS.get("local").getStatus(),
                Text.of("Local"),
                0xFFFCD400,
                48,
                12,
                () -> ChatFilter.FILTERS.get("local").enable(),
                () -> ChatFilter.FILTERS.get("local").disable(),
                () -> client.player.networkHandler.sendChatCommand("lc"),
                () -> buttonList.forEach(button -> { if(!Objects.equals(button.getLabelActive().getString(), "Local")) button.setEnabled(false); else button.setEnabled(true); })
        ));

        buttonList.add(new FlatToggleButton(
                104,
                self.height - 28,
                ChatFilter.FILTERS.get("party").getStatus(),
                Text.of("Party"),
                0xFF63f27f,
                48,
                12,
                () -> ChatFilter.FILTERS.get("party").enable(),
                () -> ChatFilter.FILTERS.get("party").disable(),
                () -> client.player.networkHandler.sendChatCommand("pc"),
                () -> buttonList.forEach(button -> { if(!Objects.equals(button.getLabelActive().getString(), "Party")) button.setEnabled(false); else button.setEnabled(true); })
        ));

        buttonList.add(new FlatToggleButton(
                156,
                self.height - 28,
                ChatFilter.FILTERS.get("town").getStatus(),
                Text.of("Town"),
                0xFF54fc54,
                48,
                12,
                () -> ChatFilter.FILTERS.get("town").enable(),
                () -> ChatFilter.FILTERS.get("town").disable(),
                () -> client.player.networkHandler.sendChatCommand("tc"),
                () -> buttonList.forEach(button -> { if(!Objects.equals(button.getLabelActive().getString(), "Town")) button.setEnabled(false); else button.setEnabled(true); })
        ));

        buttonList.add(new FlatToggleButton(
                208,
                self.height - 28,
                ChatFilter.FILTERS.get("nation").getStatus(),
                Text.of("Nation"),
                0xFFfca800,
                48,
                12,
                () -> ChatFilter.FILTERS.get("nation").enable(),
                () -> ChatFilter.FILTERS.get("nation").disable(),
                () -> client.player.networkHandler.sendChatCommand("nc"),
                () -> buttonList.forEach(button -> { if(!Objects.equals(button.getLabelActive().getString(), "Nation")) button.setEnabled(false); else button.setEnabled(true); })
        ));

        buttonList.add(new FlatToggleButton(
                260,
                self.height - 28,
                ChatFilter.FILTERS.get("dm").getStatus(),
                Text.of("DM"),
                0xFFfca800,
                48,
                12,
                () -> ChatFilter.FILTERS.get("dm").enable(),
                () -> ChatFilter.FILTERS.get("dm").disable(),
                () -> {},
                () -> buttonList.forEach(button -> { if(!Objects.equals(button.getLabelActive().getString(), "DM")) button.setEnabled(false); else button.setEnabled(true); })
        ));

        buttonList.add(new FlatToggleButton(
                312,
                self.height - 28,
                ChatFilter.FILTERS.get("other").getStatus(),
                Text.of("Other"),
                0xFFfca800,
                48,
                12,
                () -> ChatFilter.FILTERS.get("other").enable(),
                () -> ChatFilter.FILTERS.get("other").disable(),
                () -> {},
                () -> buttonList.forEach(button -> { if(!Objects.equals(button.getLabelActive().getString(), "Other")) button.setEnabled(false); else button.setEnabled(true); })
        ));

        for(FlatToggleButton button : buttonList) {
            ((ScreenAccessor) self).invokeAddDrawableChild(button);
        }
    }
}
