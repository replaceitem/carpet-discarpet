package discarpet.mixins;

import discarpet.Discarpet;
import discarpet.EventInteface;
import discarpet.ScarpetEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(MinecraftServer.class)
public class MinecraftChat_Mixin {
	//Lnet/minecraft/server/MinecraftServer;sendMessage(Lnet/minecraft/text/Text;)V
	//Lnet/minecraft/server/MinecraftServer;sendSystemMessage(Lnet/minecraft/text/Text;Ljava/util/UUID;)V
	@Inject(at = @At("RETURN"),method = "sendSystemMessage(Lnet/minecraft/text/Text;Ljava/util/UUID;)V")
	public void redirectChatToScarpet(Text message, UUID senderUuid ,CallbackInfo ci) {
		((EventInteface)ScarpetEvents.CHAT_MESSAGE).onChatMessage(message);
	}
}
