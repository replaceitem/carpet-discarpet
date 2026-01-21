package net.replaceitem.discarpet.mixins;

import net.minecraft.network.chat.Component;
import net.minecraft.server.dedicated.DedicatedServer;
import net.replaceitem.discarpet.script.events.MiscEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DedicatedServer.class)
public class DedicatedServerMixin {
    // better-fabric-console overrides this in MinecraftServer, so if this is overridden this mixin is applied
    // See https://github.com/jpenilla/better-fabric-console/blob/30817f0b2163206e18f2c526a39c092fe6a0600c/src/main/java/xyz/jpenilla/betterfabricconsole/mixin/DedicatedServerMixin.java#L55
    @SuppressWarnings({"MixinAnnotationTarget", "UnresolvedMixinReference"})
    @Inject(method = "sendSystemMessage", at = @At("RETURN"), require = 0)
    public void onSystemMessage(Component message, CallbackInfo ci) {
        MiscEvents.SYSTEM_MESSAGE.onSystemMessage(message);
    }
}
