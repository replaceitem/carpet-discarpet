package discarpet.mixins;

import discarpet.Discarpet;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.PlayerListHeaderS2CPacket;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PlayerManager.class)
public abstract class PlayerManager_Mixin {

	@Shadow
	public abstract void sendToAll(Packet<?> packet);

	@Shadow @Final private List<ServerPlayerEntity> players;

	@Inject(at= @At("HEAD"), method = "updatePlayerLatency", cancellable = true)
	public void updatePlayerLatency(CallbackInfo ci) {
		if(Discarpet.updateTabHeader) {
			Discarpet.updateTabHeader = false;
			this.sendToAll(new PlayerListHeaderS2CPacket());
		}
		ci.cancel();
		/*
		if(ScarpetDiscord.updateTabList) {
			ScarpetDiscord.updateTabList = false;
			this.sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_DISPLAY_NAME, this.players));
		}*/
	}
}