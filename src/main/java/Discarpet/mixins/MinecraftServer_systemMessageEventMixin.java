package Discarpet.mixins;

import Discarpet.script.events.DiscordEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServer_systemMessageEventMixin {
	@Inject(at = @At("RETURN"),method = "sendMessage")
	public void onSystemMessage(Text message, CallbackInfo ci) {
		DiscordEvents.SYSTEM_MESSAGE.onSystemMessage(message);
	}
}
