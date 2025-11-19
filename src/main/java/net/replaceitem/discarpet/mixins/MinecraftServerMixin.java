package net.replaceitem.discarpet.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.replaceitem.discarpet.script.events.MiscEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
	@Inject(at = @At("RETURN"), method = "sendSystemMessage")
	public void onSystemMessage(Component message, CallbackInfo ci) {
		MiscEvents.SYSTEM_MESSAGE.onSystemMessage(message);
	}
	
	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Component;getString()Ljava/lang/String;"), method = "logChatMessage")
	public String onChatMessage(Component instance, Operation<String> original) {
		MiscEvents.SYSTEM_MESSAGE.onSystemMessage(instance);
        return original.call(instance);
	}
}
