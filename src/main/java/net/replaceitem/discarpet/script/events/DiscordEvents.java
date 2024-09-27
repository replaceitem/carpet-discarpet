package net.replaceitem.discarpet.script.events;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.*;

import java.util.Optional;

public class DiscordEvents {
    public static void noop() {} //to load events before scripts do
    
    public static final BotEvents.Args1<Message> DISCORD_MESSAGE = new BotEvents.Args1<>("discord_message");
    public static final BotEvents.Args3<Message, Optional<Message>, Boolean> DISCORD_MESSAGE_EDIT = new BotEvents.Args3<>("discord_message_edit");
    public static final BotEvents.Args1<Optional<Message>> DISCORD_MESSAGE_DELETE = new BotEvents.Args1<>("discord_message_delete");
    public static final BotEvents.Args3<Reaction, User, Boolean> DISCORD_REACTION = new BotEvents.Args3<>("discord_reaction");
    public static final BotEvents.Args1<SlashCommandInteraction> DISCORD_SLASH_COMMAND = new BotEvents.Args1<>("discord_slash_command");
    public static final BotEvents.Args1<ButtonInteraction> DISCORD_BUTTON = new BotEvents.Args1<>("discord_button");
    public static final BotEvents.Args1<SelectMenuInteraction> DISCORD_SELECT_MENU = new BotEvents.Args1<>("discord_select_menu");
    public static final BotEvents.Args1<ModalInteraction> DISCORD_MODAL = new BotEvents.Args1<>("discord_modal");
    public static final BotEvents.Args1<MessageContextMenuInteraction> DISCORD_MESSAGE_CONTEXT_MENU = new BotEvents.Args1<>("discord_message_context_menu");
    public static final BotEvents.Args1<UserContextMenuInteraction> DISCORD_USER_CONTEXT_MENU = new BotEvents.Args1<>("discord_user_context_menu");
    public static final BotEvents.Args2<Server,User> DISCORD_SERVER_MEMBER_JOIN = new BotEvents.Args2<>("discord_server_member_join");
    public static final BotEvents.Args2<Server,User> DISCORD_SERVER_MEMBER_LEAVE = new BotEvents.Args2<>("discord_server_member_leave");
}
