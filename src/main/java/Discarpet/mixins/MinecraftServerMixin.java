package Discarpet.mixins;

import Discarpet.script.events.MiscEvents;
import net.minecraft.network.message.MessageSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
	@Inject(at = @At("RETURN"),method = "sendMessage")
	public void onSystemMessage(Text message, CallbackInfo ci) {
		MiscEvents.SYSTEM_MESSAGE.onSystemMessage(message);
	}
	
	@Inject(at = @At("RETURN"),method = "logChatMessage")
	public void onChatMessage(MessageSender sender, Text message, CallbackInfo ci) {
		MiscEvents.SYSTEM_MESSAGE.onSystemMessage(Text.translatable("chat.type.text", sender.name(), message));
	}
}
