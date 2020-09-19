package scarpet.discord.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class PlayerNameMixin extends PlayerEntity {
	public PlayerNameMixin(World world, BlockPos blockPos, GameProfile gameProfile) {
		super(world, blockPos, gameProfile);
	}

	//Lnet/minecraft/server/network/ServerPlayerEntity;getPlayerListName()Lnet/minecraft/text/Text;
	@Inject(at = @At("HEAD"), method = "getPlayerListName()Lnet/minecraft/text/Text;", cancellable = true)
	public void changeColor(CallbackInfoReturnable<Text> ci) {
		//ci.setReturnValue(new LiteralText("HALLO"));
		ci.setReturnValue(this.getDisplayName());
	}
}
