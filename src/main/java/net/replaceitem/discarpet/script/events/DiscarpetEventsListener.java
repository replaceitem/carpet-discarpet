package net.replaceitem.discarpet.script.events;

import carpet.CarpetServer;
import net.minecraft.server.ServerTask;
import net.replaceitem.discarpet.config.Bot;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.event.interaction.*;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.event.message.reaction.ReactionRemoveEvent;
import org.javacord.api.interaction.*;
import org.javacord.api.listener.interaction.*;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import org.javacord.api.listener.message.reaction.ReactionRemoveListener;

import java.util.Optional;

public class DiscarpetEventsListener implements
        MessageCreateListener,
        ReactionAddListener,
        ReactionRemoveListener,
        SlashCommandCreateListener,
        MessageComponentCreateListener,
        ModalSubmitListener,
        MessageContextMenuCommandListener,
        UserContextMenuCommandListener
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

    private static void callEventOnGameThread(Runnable runnable) {
        if(CarpetServer.minecraft_server == null) return;
        ServerTask serverTask = new ServerTask(Integer.MIN_VALUE, runnable);
        CarpetServer.minecraft_server.send(serverTask);
    }
}
