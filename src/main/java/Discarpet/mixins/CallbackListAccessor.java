package Discarpet.mixins;

import carpet.script.CarpetEventServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Predicate;

@Mixin(CarpetEventServer.CallbackList.class)
public interface CallbackListAccessor {
    @Invoker(remap = false)
    void callRemoveCallsIf(Predicate<CarpetEventServer.Callback> when);
}
