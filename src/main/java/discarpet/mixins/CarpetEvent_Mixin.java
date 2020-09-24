package discarpet.mixins;

import carpet.script.CarpetEventServer;
import discarpet.EventInteface;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CarpetEventServer.Event.class)
public class CarpetEvent_Mixin implements EventInteface {
    @Override
    public void onTest(float num) {

    }
}
