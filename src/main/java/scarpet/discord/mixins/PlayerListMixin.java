package scarpet.discord.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerListS2CPacket.Entry.class)
public abstract class PlayerListMixin {
	//Lnet/minecraft/network/packet/s2c/play/PlayerListS2CPacket$Entry;<init>(Lcom/mojang/authlib/GameProfile;ILnet/minecraft/world/GameMode;Lnet/minecraft/text/Text;)V
	@Mutable
	@Shadow @Final private GameMode gameMode;
	@Mutable
	@Shadow @Final private Text displayName;

	//(Lcom/mojang/authlib/GameProfile;ILnet/minecraft/world/GameMode;Lnet/minecraft/text/Text;)V
	@Inject(at= @At("RETURN"), method = "<init>")
	public void EntryConstructorMixin(PlayerListS2CPacket p, GameProfile profile, int latency, GameMode gameMode, Text displayName, CallbackInfo ci) {
		//this.gameMode = GameMode.SURVIVAL;
		this.displayName = new LiteralText("unknown");
	}
}
