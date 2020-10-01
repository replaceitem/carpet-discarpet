package Discarpet.mixins;

import carpet.script.CarpetEventServer;
import Discarpet.EventInteface;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CarpetEventServer.Event.class)
public class CarpetEvent_Mixin implements EventInteface {
    @Override
    public void onChatMessage(Text text) {

    }

    @Override
    public void onDiscordMessage(String content, String author, String channel) {

    }
}
