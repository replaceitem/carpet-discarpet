package discarpet.mixins;

import net.minecraft.network.packet.s2c.play.PlayerListHeaderS2CPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import discarpet.Discarpet;

@Mixin(PlayerListHeaderS2CPacket.class)
public class PlayerList_Mixin {
	@Shadow
	private Text footer;

	@Shadow
	private Text header;

	@Inject(at= @At("RETURN"), method = "<init>")
	public void PlayerListHeaderS2CPacket(CallbackInfo ci) {
		if(Discarpet.customHeader.getString() != "") {
			this.header = Discarpet.customHeader;
		}
		if(Discarpet.customFooter.getString() != "") {
			this.footer = Discarpet.customFooter;
		}
	}
}
