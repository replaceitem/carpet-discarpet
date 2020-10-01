package Discarpet;

import carpet.CarpetServer;
import carpet.script.CarpetEventServer;
import carpet.script.value.StringValue;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

import java.util.Arrays;

public class ScarpetEvents {
    public static final CarpetEventServer.Event CHAT_MESSAGE = new CarpetEventServer.Event("chat_message", 2, false) {
        public void onChatMessage(Text text) {
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
                    message = null;
                    type = text.getClass().getName();
                    Discarpet.warn("Unknown chat event type: " + type);
                }

                return Arrays.asList((c, t) -> {
                    return new StringValue(message);
                } , (c, t) -> {
                    return new StringValue(type);
                });
            }, () -> {
                return CarpetServer.minecraft_server.getCommandSource().withWorld(CarpetServer.minecraft_server.getWorld(World.OVERWORLD));
            });
        }
    };



    public static final CarpetEventServer.Event DISCORD_MESSAGE = new CarpetEventServer.Event("discord_message", 3, false) {
        public void onDiscordMessage(String content, String author, String channel) {
            this.handler.call(() -> {
                return Arrays.asList((c, t) -> {
                    return new StringValue(content);
                } , (c, t) -> {
                    return new StringValue(author);
                } , (c, t) -> {
                    return new StringValue(channel);
                });
            }, () -> {
                return CarpetServer.minecraft_server.getCommandSource().withWorld(CarpetServer.minecraft_server.getWorld(World.OVERWORLD));
            });
        }
    };
}
