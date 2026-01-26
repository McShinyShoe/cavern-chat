package net.shinyshoe.cavernChat.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.shinyshoe.cavernChat.client.*;
import net.shinyshoe.cavernChat.client.ui.FlatButton;
import net.shinyshoe.cavernChat.client.ui.FlatToggleButton;
import net.shinyshoe.cavernChat.client.util.ServerUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin {

    @Shadow
    protected TextFieldWidget chatField;

    @Unique
    private FlatToggleButton cavernChat$channelIndicator;

    @Inject(method = "init", at = @At("TAIL"))
    private void cavernChat$init(CallbackInfo ci) {
        if(!ServerUtils.isCavern()) return;
        ChatScreen self = (ChatScreen)(Object)this;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        List<FlatToggleButton> buttonList = new ArrayList<>();

        buttonList.add(new FlatToggleButton(
                2,
                self.height - 28,
                ChatFilter.FILTERS.get(ChatType.MESSAGE_GLOBAL).getStatus(),
                Text.of("\uD83C\uDF0F Global"),
                0xFFFCFCFC,
                50,
                12,
                () -> {
                    ChatFilter.FILTERS.get(ChatType.MESSAGE_GLOBAL).enable();
                    CavernChatConfig.getInstance().chatChannels.globalMessages = true;
                },
                () -> {
                    ChatFilter.FILTERS.get(ChatType.MESSAGE_GLOBAL).disable();
                    CavernChatConfig.getInstance().chatChannels.globalMessages = false;
                },
                () -> client.player.networkHandler.sendChatCommand("g"),
                () -> buttonList.forEach(button -> button.setEnabled(button.getLabelActive().getString().contains("Global")))
        ));

        buttonList.add(new FlatToggleButton(
                54,
                self.height - 28,
                ChatFilter.FILTERS.get(ChatType.MESSAGE_LOCAL).getStatus(),
                Text.of("⛳ Local"),
                0xFFFCD400,
                46,
                12,
                () -> {
                    ChatFilter.FILTERS.get(ChatType.MESSAGE_LOCAL).enable();
                    CavernChatConfig.getInstance().chatChannels.localMessages = true;
                },
                () -> {
                    ChatFilter.FILTERS.get(ChatType.MESSAGE_LOCAL).disable();
                    CavernChatConfig.getInstance().chatChannels.localMessages = false;
                },
                () -> client.player.networkHandler.sendChatCommand("lc"),
                () -> buttonList.forEach(button -> button.setEnabled(button.getLabelActive().getString().contains("Local")))
        ));

        buttonList.add(new FlatToggleButton(
                102,
                self.height - 28,
                ChatFilter.FILTERS.get(ChatType.MESSAGE_PARTY).getStatus(),
                Text.of("\uD83C\uDF82 Party"),
                0xFF63f27f,
                46,
                12,
                () -> {
                    ChatFilter.FILTERS.get(ChatType.MESSAGE_PARTY).enable();
                    CavernChatConfig.getInstance().chatChannels.partyMessages = true;
                },
                () -> {
                    ChatFilter.FILTERS.get(ChatType.MESSAGE_PARTY).disable();
                    CavernChatConfig.getInstance().chatChannels.partyMessages = false;
                },
                () -> client.player.networkHandler.sendChatCommand("pc"),
                () -> buttonList.forEach(button -> button.setEnabled(button.getLabelActive().getString().contains("Party")))
        ));

        buttonList.add(new FlatToggleButton(
                150,
                self.height - 28,
                ChatFilter.FILTERS.get(ChatType.MESSAGE_TOWN).getStatus(),
                Text.of("\uD83C\uDFDA Town"),
                0xFF54fc54,
                42,
                12,
                () -> {
                    ChatFilter.FILTERS.get(ChatType.MESSAGE_TOWN).enable();
                    CavernChatConfig.getInstance().chatChannels.townMessages = true;
                },
                () -> {
                    ChatFilter.FILTERS.get(ChatType.MESSAGE_TOWN).disable();
                    CavernChatConfig.getInstance().chatChannels.townMessages = false;
                },
                () -> client.player.networkHandler.sendChatCommand("tc"),
                () -> buttonList.forEach(button -> button.setEnabled(button.getLabelActive().getString().contains("Town")))
        ));

        buttonList.add(new FlatToggleButton(
                194,
                self.height - 28,
                ChatFilter.FILTERS.get(ChatType.MESSAGE_NATION).getStatus(),
                Text.of("⚑ Nation"),
                0xFFfca800,
                48,
                12,
                () -> {
                    ChatFilter.FILTERS.get(ChatType.MESSAGE_NATION).enable();
                    CavernChatConfig.getInstance().chatChannels.nationMessages = true;
                },
                () -> {
                    ChatFilter.FILTERS.get(ChatType.MESSAGE_NATION).disable();
                    CavernChatConfig.getInstance().chatChannels.nationMessages = false;
                },
                () -> client.player.networkHandler.sendChatCommand("nc"),
                () -> buttonList.forEach(button -> button.setEnabled(button.getLabelActive().getString().contains("Nation")))
        ));

        buttonList.add(new FlatToggleButton(
                244,
                self.height - 28,
                ChatFilter.FILTERS.get(ChatType.MESSAGE_DM).getStatus(),
                Text.of("✉ DM"),
                0xFFEEC65D,
                30,
                12,
                () -> {
                    ChatFilter.FILTERS.get(ChatType.MESSAGE_DM).enable();
                    CavernChatConfig.getInstance().chatChannels.dmMessages = true;
                },
                () -> {
                    ChatFilter.FILTERS.get(ChatType.MESSAGE_DM).disable();
                    CavernChatConfig.getInstance().chatChannels.dmMessages = false;
                },
                () -> {},
                () -> buttonList.forEach(button -> button.setEnabled(button.getLabelActive().getString().contains("DM")))
        ));

        buttonList.add(new FlatToggleButton(
                276,
                self.height - 28,
                ChatFilter.FILTERS.get(ChatType.MESSAGE_OTHER).getStatus(),
                Text.of("Other"),
                0xFFFFFFFF,
                34,
                12,
                () -> {
                    ChatFilter.FILTERS.get(ChatType.MESSAGE_OTHER).enable();
                    CavernChatConfig.getInstance().chatChannels.otherMessages = true;
                },
                () -> {
                    ChatFilter.FILTERS.get(ChatType.MESSAGE_OTHER).disable();
                    CavernChatConfig.getInstance().chatChannels.otherMessages = false;
                },
                () -> {},
                () -> buttonList.forEach(button -> button.setEnabled(Objects.equals(button.getLabelActive().getString(), "Other")))
        ));

        FlatButton resetButton = new FlatButton(
                312,
                self.height - 28,
                Text.of("\uD83D\uDDD8"),
                0xFFFFFFFF,
                12,
                12,
                () -> buttonList.forEach(button -> button.setEnabled(true)),
                () -> {},
                () -> {}
        );

        cavernChat$channelIndicator = new FlatToggleButton(
                2,
                self.height - 14,
                true,
                Text.of(""),
                0xFFFFFFFF,
                0xFFFFFFFF,
                0,
                12,
                () -> {},
                () -> {},
                () -> {},
                () -> {}
        );

        ((ScreenAccessor) self).invokeAddDrawableChild(resetButton);

        cavernChat$channelIndicator.visible = false;
        ((ScreenAccessor) self).invokeAddDrawableChild(cavernChat$channelIndicator);

        for(FlatToggleButton button : buttonList) {
            ((ScreenAccessor) self).invokeAddDrawableChild(button);
        }
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void cavernChat$renderHead(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
        ChatIndicator.updateIndicator();
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void cavernChat$renderTail(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
        if(!ServerUtils.isCavern()) return;
        Text indicator = ChatIndicator.getText();
        if(indicator == null) this.chatField.setEditableColor(0xFFFFFFFF);
        else {
            this.chatField.setEditableColor(0xFF000000 + ChatIndicator.getInputColor());
            cavernChat$channelIndicator.setLabel(ChatIndicator.getText());
            cavernChat$channelIndicator.setDimensions(ChatIndicator.getOffset(), 12);
            cavernChat$channelIndicator.setColor(0xFF000000 + ChatIndicator.getInputColor());
        }

        int totalOffset = ChatIndicator.getOffset() + (ChatIndicator.getOffset() == 0 ? 0 : 2);

        chatField.setX(4 + totalOffset);
        chatField.setWidth(((ChatScreen)(Object)this).width - 4 - totalOffset);

        cavernChat$channelIndicator.visible = ChatIndicator.getOffset() != 0;
    }

    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V"
            )
    )
    private void cavernChat$disableInputBackground(
            DrawContext context,
            int x1, int y1, int x2, int y2, int color
    ) {
        if(!ServerUtils.isCavern()) {
            MinecraftClient client = MinecraftClient.getInstance();
            assert client.currentScreen != null;
            context.fill(2, client.currentScreen.height - 14, client.currentScreen.width - 2, client.currentScreen.height - 2, client.options.getTextBackgroundColor(Integer.MIN_VALUE));
            return;
        }
        context.fill(
                x1 + ChatIndicator.getOffset() + (ChatIndicator.getOffset() == 0 ? 0 : 2),
                y1,
                x2,
                y2,
                color
        );
    }
}
