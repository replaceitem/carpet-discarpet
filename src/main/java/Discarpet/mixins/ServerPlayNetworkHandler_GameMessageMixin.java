package Discarpet.mixins;

import Discarpet.script.events.ChatEvents;
import Discarpet.script.events.DiscordEvents;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandler_GameMessageMixin {
    //Lnet/minecraft/server/network/ServerPlayNetworkHandler;onGameMessage(Lnet/minecraft/network/packet/c2s/play/ChatMessageC2SPacket;)V

    @Shadow public ServerPlayerEntity player;

    @Inject( at = @At("HEAD"), method = "onGameMessage")
    public void onGameMessage(ChatMessageC2SPacket packet, CallbackInfo ci) {
        String normalized = StringUtils.normalizeSpace(packet.getChatMessage());
        ChatEvents.CHAT_MESSAGE.onChatMessage(packet.getChatMessage(),this.player,normalized.startsWith("/"));
    }
}
