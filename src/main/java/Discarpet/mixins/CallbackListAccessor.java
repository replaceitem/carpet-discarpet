package Discarpet.mixins;

import carpet.script.CarpetEventServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CarpetEventServer.CallbackList.class)
public interface CallbackListAccessor {
    @Accessor
    boolean isPerPlayerDistribution();
}
