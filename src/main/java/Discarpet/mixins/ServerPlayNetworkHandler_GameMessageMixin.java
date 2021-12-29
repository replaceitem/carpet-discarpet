package Discarpet.mixins;

import Discarpet.script.events.ChatEvents;
import net.minecraft.server.filter.TextStream;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandler_GameMessageMixin {
    @Shadow public ServerPlayerEntity player;

    @Inject(method = "handleMessage",locals = LocalCapture.CAPTURE_FAILHARD,at = @At(value = "INVOKE", target = "Ljava/lang/String;startsWith(Ljava/lang/String;)Z"))
    public void onGameMessage(TextStream.Message message, CallbackInfo ci, String string) {
        ChatEvents.CHAT_MESSAGE.onChatMessage(string,this.player,string.startsWith("/"));
    }
}
