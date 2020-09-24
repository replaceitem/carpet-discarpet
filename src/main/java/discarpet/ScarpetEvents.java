package discarpet;

import carpet.CarpetServer;
import carpet.script.CarpetEventServer;
import carpet.script.value.BlockValue;
import carpet.script.value.NumericValue;
import carpet.script.value.Value;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Collections;

public class ScarpetEvents {
    public static final CarpetEventServer.Event TEST = new CarpetEventServer.Event("test", 1, false) {
        public void onTest(float num) {
            this.handler.call(() -> {
                return Arrays.asList((c, t) -> {
                    return new NumericValue(Math.random()*num);
                });
            }, () -> {
                return CarpetServer.minecraft_server.getCommandSource().withWorld(CarpetServer.minecraft_server.getWorld(World.OVERWORLD));
            });
        }
    };
}
