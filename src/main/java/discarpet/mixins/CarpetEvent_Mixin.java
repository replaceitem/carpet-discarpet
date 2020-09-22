/*package discarpet.mixins;

import carpet.CarpetServer;
import carpet.script.CarpetEventServer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Collections;

@Mixin(CarpetEventServer.Event.class)
public class CarpetEvent_Mixin {
    @Unique
    private static final CarpetEventServer.Event MINECRAFT_CHAT = new CarpetEventServer.Event("tick", 2, false) {
        public void onTick() {
            this.handler.call(Collections::emptyList, () -> {
                return CarpetServer.minecraft_server.getCommandSource().withWorld(CarpetServer.minecraft_server.getWorld(World.OVERWORLD));
            });
        }
    };
}
*/