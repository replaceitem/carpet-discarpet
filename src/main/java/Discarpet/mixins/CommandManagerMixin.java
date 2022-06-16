package Discarpet.mixins;

import Discarpet.script.events.MiscEvents;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CommandManager.class)
public class CommandManagerMixin {
    @Inject(method = "execute", at = @At("HEAD"))
    private void onCommandExecuted(ServerCommandSource commandSource, String command, CallbackInfoReturnable<Integer> cir) {
        MiscEvents.COMMAND_EXECUTED.onCommandExecuted(commandSource.getPlayer(), command);
    }
}
