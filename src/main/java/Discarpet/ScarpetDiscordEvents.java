package Discarpet;

import carpet.CarpetServer;
import carpet.script.value.EntityValue;
import carpet.script.value.NullValue;
import carpet.script.value.StringValue;
import net.minecraft.entity.Entity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import carpet.script.CarpetEventServer.Event;
import java.util.Arrays;


public class ScarpetDiscordEvents extends Event {
    public ScarpetDiscordEvents(String name, int reqArgs, boolean isGlobalOnly) {
        super(name, reqArgs, isGlobalOnly);
    }

    public void onChatMessage(Text text, Entity entity) {}

    public static ScarpetDiscordEvents CHAT_MESSAGE = new ScarpetDiscordEvents("chat_message", 3, false) {
        public void onChatMessage(Text text,Entity entity) {
            this.handler.call(() -> {
                String message;
                String type;
                if(text instanceof TranslatableText) {
                    type = ((TranslatableText) text).getKey();
                    message = text.getString();
                } else if(text instanceof LiteralText) {
                    type = text.getClass().getName();
                    message = (text).getString();
                } else {
                    message = "unknown text type";
                    type = text.getClass().getName();
                }
                if(entity == null) return Arrays.asList(new StringValue(message), new StringValue(type),new NullValue());
                return Arrays.asList(new StringValue(message), new StringValue(type),new EntityValue(entity));

            }, () -> {
                if(entity == null) {
                    return CarpetServer.minecraft_server.getCommandSource().withWorld(CarpetServer.minecraft_server.getWorld(World.OVERWORLD));
                } else {
                    return entity.getCommandSource();
                }
            });
        }
    };

    public void onDiscordMessage(String content, String author, String channel) {}

    public static ScarpetDiscordEvents DISCORD_MESSAGE = new ScarpetDiscordEvents("discord_message", 3, false) {
        public void onDiscordMessage(String content, String author, String channel) {
            this.handler.call(() -> {
                return Arrays.asList(new StringValue(content),new StringValue(author),new StringValue(channel));
            }, () -> {
                return CarpetServer.minecraft_server.getCommandSource().withWorld(CarpetServer.minecraft_server.getWorld(World.OVERWORLD));
            });
        }
    };
}
