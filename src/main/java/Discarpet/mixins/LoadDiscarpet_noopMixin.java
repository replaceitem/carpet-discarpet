package Discarpet.mixins;

import Discarpet.Discarpet;
import net.minecraft.util.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CrashReport.class)
public class LoadDiscarpet_noopMixin {
    @Inject(method = "initCrashReport", at = @At("HEAD"))
    private static void loadDiscarpet(CallbackInfo ci) {
        Discarpet.noop();
    }
}
