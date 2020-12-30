package Discarpet.script.events;

import Discarpet.Discarpet;
import Discarpet.script.values.UserValue;
import Discarpet.script.values.MessageValue;
import Discarpet.script.values.ReactionValue;
import Discarpet.Bot;
import carpet.CarpetServer;
import carpet.script.CarpetEventServer;
import carpet.script.CarpetScriptHost;
import carpet.script.CarpetScriptServer;
import carpet.script.value.EntityValue;
import carpet.script.value.NullValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import carpet.script.CarpetEventServer.Event;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;


public class DiscordEvents extends Event {
    public DiscordEvents(String name, int reqArgs, boolean isGlobalOnly) {
        super(name, reqArgs, isGlobalOnly);
    }

    public void onDiscordMessage(Bot bot, Message message) {}

    public static DiscordEvents DISCORD_MESSAGE = new DiscordEvents("discord_message", 1, false) {
        public void onDiscordMessage(Bot bot, Message message) {
            if(!CarpetServer.minecraft_server.isRunning()) return; //prevent errors when message comes while stopping
            callHandlerInBotApps(bot,handler,() -> {
                return Arrays.asList(new MessageValue(message));
            }, () -> {
                return CarpetServer.minecraft_server.getCommandSource().withWorld(CarpetServer.minecraft_server.getWorld(World.OVERWORLD));
            });
        }
    };

    public void onDiscordReaction(Bot bot, Reaction reaction, User user, boolean added) {}

    public static DiscordEvents DISCORD_REACTION = new DiscordEvents("discord_reaction", 2, false) {
        public void onDiscordReaction(Bot bot, Reaction reaction, User user, boolean added) {
            if(!CarpetServer.minecraft_server.isRunning()) return; //prevent errors when message comes while stopping
            callHandlerInBotApps(bot,handler,() -> {
                return Arrays.asList(new ReactionValue(reaction),new UserValue(user), new NumericValue(added));
            }, () -> {
                return CarpetServer.minecraft_server.getCommandSource().withWorld(CarpetServer.minecraft_server.getWorld(World.OVERWORLD));
            });
        }
    };

    public void callHandlerInBotApps(Bot triggerBot, CarpetEventServer.CallbackList handler, Supplier<List<Value>> argumentSupplier, Supplier<ServerCommandSource> cmdSourceSupplier) {
        if (handler.callList.size() > 0) {
            List<Value> argv = (List)argumentSupplier.get();
            ServerCommandSource source = (ServerCommandSource)cmdSourceSupplier.get();

            assert argv.size() == handler.reqArgs;

            List<CarpetEventServer.Callback> fails = new ArrayList();
            Iterator var6 = handler.callList.iterator();

            CarpetEventServer.Callback call;
            while(var6.hasNext()) {
                call = (CarpetEventServer.Callback)var6.next();
                Bot scriptBot = Discarpet.getBotInHost(CarpetServer.scriptServer.getHostByName(call.host));
                if(scriptBot == null) continue;
                if(scriptBot.id.equals(triggerBot.id)) {
                    if (!call.execute(source, argv)) {
                        fails.add(call);
                    }
                }
            }

            var6 = fails.iterator();

            while(var6.hasNext()) {
                call = (CarpetEventServer.Callback)var6.next();
                handler.callList.remove(call);
            }
        }

    }

}
