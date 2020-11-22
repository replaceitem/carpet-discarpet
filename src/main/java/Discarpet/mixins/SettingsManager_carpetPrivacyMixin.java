package Discarpet.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import Discarpet.Discarpet;
import carpet.settings.ParsedRule;
import carpet.settings.SettingsManager;
import net.minecraft.server.command.ServerCommandSource;

@Mixin(SettingsManager.class)
public class SettingsManager_carpetPrivacyMixin {
	@Inject(
        method = "notifyRuleChanged",
        at = @At(value = "HEAD"),
        cancellable = true,
        remap = false
    )
	private void dontBroadcastOurChanges(ServerCommandSource source, ParsedRule<?> rule, String userTypedValue, CallbackInfo ci) {
		if (rule.settingsManager == Discarpet.mySettingManager) {
			Discarpet.ruleObserver(source, rule);
			ci.cancel();
		}
	}
}
