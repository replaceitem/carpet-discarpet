package discarpet.mixins;

import discarpet.Discarpet;
import net.minecraft.server.Main;
import net.minecraft.util.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CrashReport.class)
public class MinecraftServer_noopMixin
{
    @Inject(method = "initCrashReport", at = @At("HEAD"))
    private static void onServerStarted(CallbackInfo ci) {
        Discarpet.noop();
    }
}
