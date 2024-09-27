package net.replaceitem.discarpet.script.events;

import carpet.CarpetServer;
import carpet.script.CarpetEventServer;
import carpet.script.value.Value;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.world.World;
import net.replaceitem.discarpet.Discarpet;
import net.replaceitem.discarpet.config.Bot;
import net.replaceitem.discarpet.mixins.CallbackListAccessor;
import net.replaceitem.discarpet.script.util.ValueConversions;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class BotEvents extends CarpetEventServer.Event {
    public BotEvents(String name, int reqArgs, boolean isGlobalOnly) {
        super(name, reqArgs, isGlobalOnly);
    }
    
    protected Supplier<List<Value>> convertParameters(Object... params) {
        return () -> Arrays.stream(params).map(ValueConversions::toValue).toList();
    }

    protected void runBotEvent(Bot bot, Object... params) {
        callHandlerInBotApps(bot, convertParameters(params));
    }
    
    private void callHandlerInBotApps(Bot triggerBot, Supplier<List<Value>> argumentSupplier) {
        if(CarpetServer.minecraft_server != null && !CarpetServer.minecraft_server.isRunning()) return; //prevent errors when event triggers while stopping server
        ((CallbackListAccessor) handler).callRemoveCallsIf(callback -> !triggerBot.equals(Discarpet.getBotInHost(CarpetServer.scriptServer.getAppHostByName(callback.host))));
        handler.call(argumentSupplier,DEFAULT_SOURCE_SUPPLIER);
    }

    private static final Supplier<ServerCommandSource> DEFAULT_SOURCE_SUPPLIER = () -> {
        try {
            return CarpetServer.minecraft_server.getCommandSource().withWorld(CarpetServer.minecraft_server.getWorld(World.OVERWORLD));
        } catch (NullPointerException npe) {
            return null;
        }
    };
    
    public static class Args1<A> extends BotEvents {
        public Args1(String name) {
            super(name, 1, false);
        }
        
        public void run(Bot bot, A a) {
            runBotEvent(bot, a);
        }
    }
    public static class Args2<A,B> extends BotEvents {
        public Args2(String name) {
            super(name, 2, false);
        }
        
        public void run(Bot bot, A a, B b) {
            runBotEvent(bot, a, b);
        }
    }
    public static class Args3<A,B,C> extends BotEvents {
        public Args3(String name) {
            super(name, 3, false);
        }

        public void run(Bot bot, A a, B b, C c) {
            runBotEvent(bot, a, b, c);
        }
    }
}
