package Discarpet.script.events;

import Discarpet.Discarpet;
import Discarpet.mixins.CallbackListAccessor;
import Discarpet.script.values.ButtonInteractionValue;
import Discarpet.script.values.SelectMenuInteractionValue;
import Discarpet.script.values.SlashCommandInteractionValue;
import Discarpet.script.values.UserValue;
import Discarpet.script.values.MessageValue;
import Discarpet.script.values.ReactionValue;
import Discarpet.Bot;
import carpet.CarpetServer;
import carpet.script.CarpetEventServer;
import carpet.script.CarpetScriptHost;
import carpet.script.CarpetScriptServer;
import carpet.script.ScriptHost;
import carpet.script.value.BooleanValue;
import carpet.script.value.Value;
import carpet.utils.CarpetProfiler;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.world.World;
import carpet.script.CarpetEventServer.Event;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.ButtonInteraction;
import org.javacord.api.interaction.SelectMenuInteraction;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;


public class DiscordEvents extends Event {
    public static void noop() {} //to load events before scripts do

    public DiscordEvents(String name, int reqArgs, boolean isGlobalOnly) {
        super(name, reqArgs, isGlobalOnly);
    }

    public void onDiscordMessage(Bot bot, Message message) {}

    public static DiscordEvents DISCORD_MESSAGE = new DiscordEvents("discord_message", 1, false) {
        public void onDiscordMessage(Bot bot, Message message) {
            if(bot == null) return;
            if(CarpetServer.minecraft_server != null && !CarpetServer.minecraft_server.isRunning()) return; //prevent errors when message comes while stopping
            callHandlerInBotApps(bot,() -> {
                return Arrays.asList(new MessageValue(message));
            }, () -> {
                try {
                    return CarpetServer.minecraft_server.getCommandSource().withWorld(CarpetServer.minecraft_server.getWorld(World.OVERWORLD));
                } catch (NullPointerException npe) {
                    return null;
                }
            });
        }
    };

    public void onDiscordReaction(Bot bot, Reaction reaction, User user, boolean added) {}

    public static DiscordEvents DISCORD_REACTION = new DiscordEvents("discord_reaction", 3, false) {
        public void onDiscordReaction(Bot bot, Reaction reaction, User user, boolean added) {
            if(bot == null) return;
            if(CarpetServer.minecraft_server != null && !CarpetServer.minecraft_server.isRunning()) return; //prevent errors when message comes while stopping
            callHandlerInBotApps(bot,() -> {
                return Arrays.asList(new ReactionValue(reaction),new UserValue(user), BooleanValue.of(added));
            }, () -> {
                try {
                    return CarpetServer.minecraft_server.getCommandSource().withWorld(CarpetServer.minecraft_server.getWorld(World.OVERWORLD));
                } catch (NullPointerException npe) {
                    return null;
                }
            });
        }
    };


    public void onDiscordSlashCommand(Bot bot, SlashCommandInteraction slashCommandInteraction) {}

    public static DiscordEvents DISCORD_SLASH_COMMAND = new DiscordEvents("discord_slash_command", 1, false) {
        public void onDiscordSlashCommand(Bot bot, SlashCommandInteraction slashCommandInteraction) {
            if(bot == null) return;
            if(CarpetServer.minecraft_server != null && !CarpetServer.minecraft_server.isRunning()) return; //prevent errors when message comes while stopping
            callHandlerInBotApps(bot,() -> {
                return Arrays.asList(new SlashCommandInteractionValue(slashCommandInteraction));
            }, () -> {
                try {
                    return CarpetServer.minecraft_server.getCommandSource().withWorld(CarpetServer.minecraft_server.getWorld(World.OVERWORLD));
                } catch (NullPointerException npe) {
                    return null;
                }
            });
        }
    };


    public void onDiscordButton(Bot bot, ButtonInteraction buttonInteraction) {}

    public static DiscordEvents DISCORD_BUTTON = new DiscordEvents("discord_button", 1, false) {
        public void onDiscordButton(Bot bot, ButtonInteraction buttonInteraction) {
            if(bot == null) return;
            if(CarpetServer.minecraft_server != null && !CarpetServer.minecraft_server.isRunning()) return; //prevent errors when message comes while stopping
            callHandlerInBotApps(bot,() -> {
                return Arrays.asList(new ButtonInteractionValue(buttonInteraction));
            }, () -> {
                try {
                    return CarpetServer.minecraft_server.getCommandSource().withWorld(CarpetServer.minecraft_server.getWorld(World.OVERWORLD));
                } catch (NullPointerException npe) {
                    return null;
                }
            });
        }
    };


    public void onDiscordSelectMenu(Bot bot, SelectMenuInteraction selectMenuInteraction) {}

    public static DiscordEvents DISCORD_SELECT_MENU = new DiscordEvents("discord_select_menu", 1, false) {
        public void onDiscordSelectMenu(Bot bot, SelectMenuInteraction selectMenuInteraction) {
            if(bot == null) return;
            if(CarpetServer.minecraft_server != null && !CarpetServer.minecraft_server.isRunning()) return; //prevent errors when message comes while stopping
            callHandlerInBotApps(bot,() -> {
                return Arrays.asList(new SelectMenuInteractionValue(selectMenuInteraction));
            }, () -> {
                try {
                    return CarpetServer.minecraft_server.getCommandSource().withWorld(CarpetServer.minecraft_server.getWorld(World.OVERWORLD));
                } catch (NullPointerException npe) {
                    return null;
                }
            });
        }
    };

    public void callHandlerInBotApps(Bot triggerBot, Supplier<List<Value>> argumentSupplier, Supplier<ServerCommandSource> cmdSourceSupplier) {
        ((CallbackListAccessor) handler).callRemoveCallsIf(callback -> !triggerBot.equals(Discarpet.getBotInHost(CarpetServer.scriptServer.getAppHostByName(callback.host))));
        handler.call(argumentSupplier,cmdSourceSupplier);
    }
}
