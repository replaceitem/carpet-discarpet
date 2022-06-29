package Discarpet.script.events;

import Discarpet.config.Bot;
import carpet.CarpetServer;
import net.minecraft.server.ServerTask;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.event.interaction.ModalSubmitEvent;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.event.message.reaction.ReactionRemoveEvent;
import org.javacord.api.interaction.ButtonInteraction;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.interaction.ModalInteraction;
import org.javacord.api.interaction.SelectMenuInteraction;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;
import org.javacord.api.listener.interaction.ModalSubmitListener;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import org.javacord.api.listener.message.reaction.ReactionRemoveListener;

import java.util.Optional;

public class DiscarpetEventsListener implements MessageCreateListener, ReactionAddListener, ReactionRemoveListener, SlashCommandCreateListener, MessageComponentCreateListener, ModalSubmitListener {
    
    protected final Bot bot;
    
    public DiscarpetEventsListener(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        final Message message = event.getMessage();
        callEventOnGameThread(() -> DiscordEvents.DISCORD_MESSAGE.onDiscordMessage(bot,message));
    }

    @Override
    public void onReactionAdd(ReactionAddEvent event) {
        Optional<Reaction> optionalReaction = event.getReaction();
        if(optionalReaction.isPresent()) {
            Reaction reaction = optionalReaction.get();
            event.requestUser().thenAccept(user -> 
                    callEventOnGameThread(() -> DiscordEvents.DISCORD_REACTION.onDiscordReaction(bot, reaction, user, true)));
        }
    }

    @Override
    public void onReactionRemove(ReactionRemoveEvent event) {
        Optional<Reaction> optionalReaction = event.getReaction();
        if(optionalReaction.isPresent()) {
            Reaction reaction = optionalReaction.get();
            event.requestUser().thenAccept(user -> 
                    callEventOnGameThread(() -> DiscordEvents.DISCORD_REACTION.onDiscordReaction(bot, reaction, user, false)));
        }
    }

    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent event) {
        final SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();
        callEventOnGameThread(() -> DiscordEvents.DISCORD_SLASH_COMMAND.onDiscordSlashCommand(bot, slashCommandInteraction));
    }

    @Override
    public void onComponentCreate(MessageComponentCreateEvent event) {
        MessageComponentInteraction messageComponentInteraction = event.getMessageComponentInteraction();

        if(messageComponentInteraction.asButtonInteraction().isPresent()) {
            final ButtonInteraction buttonInteraction = messageComponentInteraction.asButtonInteraction().get();
            callEventOnGameThread(() -> DiscordEvents.DISCORD_BUTTON.onDiscordButton(bot, buttonInteraction));
        } else if(messageComponentInteraction.asSelectMenuInteraction().isPresent()) {
            final SelectMenuInteraction selectMenuInteraction = messageComponentInteraction.asSelectMenuInteraction().get();
            callEventOnGameThread(() -> DiscordEvents.DISCORD_SELECT_MENU.onDiscordSelectMenu(bot, selectMenuInteraction));
        }
    }

    @Override
    public void onModalSubmit(ModalSubmitEvent event) {
        final ModalInteraction modalInteraction = event.getModalInteraction();
        callEventOnGameThread(() -> DiscordEvents.DISCORD_MODAL.onDiscordModal(bot, modalInteraction));
    }

    private static void callEventOnGameThread(Runnable runnable) {
        ServerTask serverTask = new ServerTask(Integer.MIN_VALUE, runnable);
        CarpetServer.minecraft_server.send(serverTask);
    }
}
