package net.replaceitem.discarpet.script.events;

import carpet.CarpetServer;
import net.minecraft.server.ServerTask;
import net.replaceitem.discarpet.config.Bot;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.*;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.event.message.MessageDeleteEvent;
import org.javacord.api.event.message.MessageEditEvent;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.event.message.reaction.ReactionRemoveEvent;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.event.server.member.ServerMemberLeaveEvent;
import org.javacord.api.interaction.*;
import org.javacord.api.listener.interaction.*;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.listener.message.MessageDeleteListener;
import org.javacord.api.listener.message.MessageEditListener;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import org.javacord.api.listener.message.reaction.ReactionRemoveListener;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;
import org.javacord.api.listener.server.member.ServerMemberLeaveListener;

import java.util.Optional;

public class DiscarpetEventsListener implements
        MessageCreateListener,
        MessageEditListener,
        MessageDeleteListener,
        ReactionAddListener,
        ReactionRemoveListener,
        SlashCommandCreateListener,
        MessageComponentCreateListener,
        ModalSubmitListener,
        MessageContextMenuCommandListener,
        UserContextMenuCommandListener,
        ServerMemberJoinListener,
        ServerMemberLeaveListener
{
    
    protected final Bot bot;
    
    public DiscarpetEventsListener(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        final Message message = event.getMessage();
        callEventOnGameThread(() -> DiscordEvents.DISCORD_MESSAGE.run(bot,message));
    }

    @Override
    public void onMessageEdit(MessageEditEvent event) {
        final Message message = event.getMessage();
        final Optional<Message> oldMessage = event.getOldMessage();
        final boolean isActualEdit = event.isActualEdit();
        callEventOnGameThread(() -> DiscordEvents.DISCORD_MESSAGE_EDIT.run(bot,message, oldMessage, isActualEdit));
    }

    @Override
    public void onMessageDelete(MessageDeleteEvent event) {
        final Optional<Message> message = event.getMessage();
        callEventOnGameThread(() -> DiscordEvents.DISCORD_MESSAGE_DELETE.run(bot,message));
    }

    @Override
    public void onReactionAdd(ReactionAddEvent event) {
        Optional<Reaction> optionalReaction = event.getReaction();
        if(optionalReaction.isPresent()) {
            Reaction reaction = optionalReaction.get();
            event.requestUser().thenAccept(user -> 
                    callEventOnGameThread(() -> DiscordEvents.DISCORD_REACTION.run(bot, reaction, user, true)));
        }
    }

    @Override
    public void onReactionRemove(ReactionRemoveEvent event) {
        Optional<Reaction> optionalReaction = event.getReaction();
        if(optionalReaction.isPresent()) {
            Reaction reaction = optionalReaction.get();
            event.requestUser().thenAccept(user -> 
                    callEventOnGameThread(() -> DiscordEvents.DISCORD_REACTION.run(bot, reaction, user, false)));
        }
    }

    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent event) {
        final SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();
        callEventOnGameThread(() -> DiscordEvents.DISCORD_SLASH_COMMAND.run(bot, slashCommandInteraction));
    }

    @Override
    public void onComponentCreate(MessageComponentCreateEvent event) {
        MessageComponentInteraction messageComponentInteraction = event.getMessageComponentInteraction();

        if(messageComponentInteraction.asButtonInteraction().isPresent()) {
            final ButtonInteraction buttonInteraction = messageComponentInteraction.asButtonInteraction().get();
            callEventOnGameThread(() -> DiscordEvents.DISCORD_BUTTON.run(bot, buttonInteraction));
        } else if(messageComponentInteraction.asSelectMenuInteraction().isPresent()) {
            final SelectMenuInteraction selectMenuInteraction = messageComponentInteraction.asSelectMenuInteraction().get();
            callEventOnGameThread(() -> DiscordEvents.DISCORD_SELECT_MENU.run(bot, selectMenuInteraction));
        }
    }

    @Override
    public void onModalSubmit(ModalSubmitEvent event) {
        final ModalInteraction modalInteraction = event.getModalInteraction();
        callEventOnGameThread(() -> DiscordEvents.DISCORD_MODAL.run(bot, modalInteraction));
    }

    @Override
    public void onMessageContextMenuCommand(MessageContextMenuCommandEvent event) {
        final MessageContextMenuInteraction messageContextMenuInteraction = event.getMessageContextMenuInteraction();
        callEventOnGameThread(() -> DiscordEvents.DISCORD_MESSAGE_CONTEXT_MENU.run(bot, messageContextMenuInteraction));
    }

    @Override
    public void onUserContextMenuCommand(UserContextMenuCommandEvent event) {
        final UserContextMenuInteraction userContextMenuInteraction = event.getUserContextMenuInteraction();
        callEventOnGameThread(() -> DiscordEvents.DISCORD_USER_CONTEXT_MENU.run(bot, userContextMenuInteraction));
    }

    @Override
    public void onServerMemberJoin(ServerMemberJoinEvent event) {
        final Server server = event.getServer();
        final User user = event.getUser();
        callEventOnGameThread(() -> DiscordEvents.DISCORD_SERVER_MEMBER_JOIN.run(bot, server, user));
    }

    @Override
    public void onServerMemberLeave(ServerMemberLeaveEvent event) {
        final Server server = event.getServer();
        final User user = event.getUser();
        callEventOnGameThread(() -> DiscordEvents.DISCORD_SERVER_MEMBER_LEAVE.run(bot, server, user));
    }

    private static void callEventOnGameThread(Runnable runnable) {
        if(CarpetServer.minecraft_server == null) return;
        ServerTask serverTask = new ServerTask(Integer.MIN_VALUE, runnable);
        CarpetServer.minecraft_server.send(serverTask);
    }
}
