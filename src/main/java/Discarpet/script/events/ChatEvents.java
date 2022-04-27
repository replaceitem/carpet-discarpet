package Discarpet.script.events;

import carpet.CarpetServer;
import carpet.script.CarpetEventServer.Event;
import carpet.script.value.BooleanValue;
import carpet.script.value.EntityValue;
import carpet.script.value.StringValue;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

import java.util.Arrays;


public class ChatEvents extends Event {
    public static void noop() {} //to load events before scripts do

    public ChatEvents(String name, int reqArgs, boolean isGlobalOnly) {
        super(name, reqArgs, isGlobalOnly);
    }

    public void onSystemMessage(Text text, Entity entity) {}

    public static ChatEvents SYSTEM_MESSAGE = new ChatEvents("system_message", 3, false) {
        public void onSystemMessage(Text text,Entity entity) {
            this.handler.call(() -> {
                String message = text.getString();
                String type = (text instanceof TranslatableText translatableText) ? translatableText.getKey() : null;
                return Arrays.asList(StringValue.of(message), StringValue.of(type), EntityValue.of(entity));
            }, () -> {
                if(entity == null) {
                    return CarpetServer.minecraft_server.getCommandSource().withWorld(CarpetServer.minecraft_server.getWorld(World.OVERWORLD));
                } else {
                    return entity.getCommandSource();
                }
            });
        }
    };


    public void onChatMessage(String message, ServerPlayerEntity player, boolean isCommand) {}

    public static ChatEvents CHAT_MESSAGE = new ChatEvents("chat_message", 3, false) {
        public void onChatMessage(String message, ServerPlayerEntity player,boolean isCommand) {
            this.handler.call(() -> {
                return Arrays.asList(new StringValue(message), new EntityValue(player), BooleanValue.of(isCommand));
            }, () -> {
                return player.getCommandSource();
            });
        }
    };
}
