package scarpet.discord.mixins;

import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Team.class)
public abstract class TeamModifyTextMixin {
	//Lnet/minecraft/scoreboard/Team;modifyText(Lnet/minecraft/text/Text;)Lnet/minecraft/text/MutableText;
	//Lnet/minecraft/scoreboard/Team;modifyText(Lnet/minecraft/scoreboard/AbstractTeam;Lnet/minecraft/text/Text;)Lnet/minecraft/text/MutableText;
	@Shadow
	private Text prefix;
	@Shadow
	private Text suffix;

	@Shadow @Final private Scoreboard scoreboard;

	@Inject(at = @At("HEAD"), method = "modifyText(Lnet/minecraft/text/Text;)Lnet/minecraft/text/MutableText;", cancellable = true, remap = true)
	public void changeColor(Text text, CallbackInfoReturnable<MutableText> ci) {
		String player = text.asString();
		ScoreboardObjective obj = scoreboard.getObjective("customnamecolor");
		ScoreboardPlayerScore colorScore = this.scoreboard.getPlayerScore(player, obj);
		Style style = text.getStyle();
		if(colorScore != null) {
			System.out.println("HAS COLOR");
			style = style.withColor(TextColor.fromRgb(colorScore.getScore()));
		} else {
			System.out.println("HAS NOT COLOR");
		}
		System.out.println(style.toString());
		Text modifiedText = ((LiteralText) text).setStyle(style);
		MutableText mutableText = (new LiteralText("")).append(this.prefix).append(modifiedText).append(this.suffix);
		ci.setReturnValue(mutableText);
	}

/*
	@Inject(at = @At("HEAD"), method = "modifyText(Lnet/minecraft/scoreboard/AbstractTeam;Lnet/minecraft/text/Text;)Lnet/minecraft/text/MutableText;", cancellable = true)
	private static void redirectStatic(AbstractTeam team, Text text,  CallbackInfoReturnable<MutableText> cir) {
		cir.setReturnValue(team.modifyText(text));
	}*/
}
