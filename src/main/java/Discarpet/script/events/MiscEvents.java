package Discarpet.script.events;

import carpet.CarpetServer;
import carpet.script.CarpetEventServer.Event;
import carpet.script.value.EntityValue;
import carpet.script.value.StringValue;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.function.Supplier;


public class MiscEvents extends Event {
    
    private static final Supplier<ServerCommandSource> DEFAULT_SOURCE_SUPPLIER = () -> {
        try {
            return CarpetServer.minecraft_server.getCommandSource().withWorld(CarpetServer.minecraft_server.getWorld(World.OVERWORLD));
        } catch (NullPointerException npe) {
            return null;
        }
    };
    
    public static void noop() {} //to load events before scripts do

    public MiscEvents(String name, int reqArgs, boolean isGlobalOnly) {
        super(name, reqArgs, isGlobalOnly);
    }


    public void onSystemMessage(Text text) {}

    public static MiscEvents SYSTEM_MESSAGE = new MiscEvents("system_message", 2, false) {
        public void onSystemMessage(Text text) {
            this.handler.call(() -> {
                String message = text.getString();
                String type = (text.getContent() instanceof TranslatableTextContent translatableTextContent) ? translatableTextContent.getKey() : null;
                return Arrays.asList(StringValue.of(message), StringValue.of(type));
            }, DEFAULT_SOURCE_SUPPLIER);
        }
    };

    public void onCommandExecuted(ServerPlayerEntity player, String command) {}

    public static MiscEvents COMMAND_EXECUTED = new MiscEvents("command_executed", 2, false) {
        public void onCommandExecuted(ServerPlayerEntity player, String command) {
            this.handler.call(() -> {
                return Arrays.asList(EntityValue.of(player), StringValue.of(command));
            }, DEFAULT_SOURCE_SUPPLIER);
        }
    };
}
