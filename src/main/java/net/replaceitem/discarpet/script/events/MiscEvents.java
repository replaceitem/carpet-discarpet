package net.replaceitem.discarpet.script.events;

import carpet.CarpetServer;
import carpet.script.CarpetEventServer.Event;
import carpet.script.value.FormattedTextValue;
import carpet.script.value.StringValue;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.level.Level;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;


public class MiscEvents extends Event {
    
    private static final Supplier<CommandSourceStack> DEFAULT_SOURCE_SUPPLIER = () -> {
        try {
            return CarpetServer.minecraft_server.createCommandSourceStack().withLevel(Objects.requireNonNull(CarpetServer.minecraft_server.getLevel(Level.OVERWORLD)));
        } catch (NullPointerException npe) {
            return null;
        }
    };
    
    public static void noop() {} //to load events before scripts do

    public MiscEvents(String name, int reqArgs, boolean isGlobalOnly) {
        super(name, reqArgs, isGlobalOnly);
    }


    public void onSystemMessage(Component message) {}

    public static MiscEvents SYSTEM_MESSAGE = new MiscEvents("system_message", 2, false) {
        public void onSystemMessage(Component message) {
            String type = (message.getContents() instanceof TranslatableContents translatableTextContent) ? translatableTextContent.getKey() : null;
            this.handler.call(() -> Arrays.asList(FormattedTextValue.of(message), StringValue.of(type)), DEFAULT_SOURCE_SUPPLIER);
        }
    };
}
