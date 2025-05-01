package net.replaceitem.discarpet.script.events;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.GenericSelectMenuInteractionEvent;

public class DiscordEvents {
    public static void noop() {} //to load events before scripts do
    
    public static final BotEvents.Args1<Message> DISCORD_MESSAGE = new BotEvents.Args1<>("discord_message");
    public static final BotEvents.Args1<Message> DISCORD_MESSAGE_EDIT = new BotEvents.Args1<>("discord_message_edit");
    public static final BotEvents.Args2<String, MessageChannelUnion> DISCORD_MESSAGE_DELETE = new BotEvents.Args2<>("discord_message_delete");
    public static final BotEvents.Args3<MessageReaction, User, Boolean> DISCORD_REACTION = new BotEvents.Args3<>("discord_reaction");
    public static final BotEvents.Args1<SlashCommandInteractionEvent> DISCORD_SLASH_COMMAND = new BotEvents.Args1<>("discord_slash_command");
    public static final BotEvents.Args1<ButtonInteractionEvent> DISCORD_BUTTON = new BotEvents.Args1<>("discord_button");
    public static final BotEvents.Args1<GenericSelectMenuInteractionEvent<?,?>> DISCORD_SELECT_MENU = new BotEvents.Args1<>("discord_select_menu");
    public static final BotEvents.Args1<ModalInteractionEvent> DISCORD_MODAL = new BotEvents.Args1<>("discord_modal");
    public static final BotEvents.Args1<MessageContextInteractionEvent> DISCORD_MESSAGE_CONTEXT_MENU = new BotEvents.Args1<>("discord_message_context_menu");
    public static final BotEvents.Args1<UserContextInteractionEvent> DISCORD_USER_CONTEXT_MENU = new BotEvents.Args1<>("discord_user_context_menu");
    public static final BotEvents.Args2<Guild,User> DISCORD_SERVER_MEMBER_JOIN = new BotEvents.Args2<>("discord_server_member_join");
    public static final BotEvents.Args2<Guild,User> DISCORD_SERVER_MEMBER_LEAVE = new BotEvents.Args2<>("discord_server_member_leave");
}
