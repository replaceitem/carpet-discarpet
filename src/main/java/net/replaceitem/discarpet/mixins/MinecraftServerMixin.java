package net.replaceitem.discarpet.mixins;

import net.replaceitem.discarpet.script.events.MiscEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
	@Inject(at = @At("RETURN"),method = "sendMessage")
	public void onSystemMessage(Text message, CallbackInfo ci) {
		MiscEvents.SYSTEM_MESSAGE.onSystemMessage(message);
	}
	
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/text/Text;getString()Ljava/lang/String;"),method = "logChatMessage")
	public String onChatMessage(Text text) {
		MiscEvents.SYSTEM_MESSAGE.onSystemMessage(text);
		return text.getString();
	}
}
