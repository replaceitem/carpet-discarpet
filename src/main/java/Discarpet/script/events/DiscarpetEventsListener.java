package Discarpet.script.events;

import Discarpet.config.Bot;
import carpet.CarpetServer;
import net.minecraft.server.ServerTask;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.event.message.reaction.ReactionRemoveEvent;
import org.javacord.api.interaction.ButtonInteraction;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.interaction.SelectMenuInteraction;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import org.javacord.api.listener.message.reaction.ReactionRemoveListener;

import java.util.Optional;

public class DiscarpetEventsListener implements MessageCreateListener, ReactionAddListener, ReactionRemoveListener, SlashCommandCreateListener, MessageComponentCreateListener {
    
    protected final Bot bot;
    
    public DiscarpetEventsListener(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        final Message message = messageCreateEvent.getMessage();
        callEventOnGameThread(() -> DiscordEvents.DISCORD_MESSAGE.onDiscordMessage(bot,message));
    }

    @Override
    public void onReactionAdd(ReactionAddEvent reactionAddEvent) {
        Optional<Reaction> optionalReaction = reactionAddEvent.getReaction();
        if(optionalReaction.isPresent()) {
            Reaction reaction = optionalReaction.get();
            reactionAddEvent.requestUser().thenAccept(user -> 
                    callEventOnGameThread(() -> DiscordEvents.DISCORD_REACTION.onDiscordReaction(bot, reaction, user, true)));
        }
    }

    @Override
    public void onReactionRemove(ReactionRemoveEvent reactionRemoveEvent) {
        Optional<Reaction> optionalReaction = reactionRemoveEvent.getReaction();
        if(optionalReaction.isPresent()) {
            Reaction reaction = optionalReaction.get();
            reactionRemoveEvent.requestUser().thenAccept(user -> 
                    callEventOnGameThread(() -> DiscordEvents.DISCORD_REACTION.onDiscordReaction(bot, reaction, user, false)));
        }
    }

    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent slashCommandCreateEvent) {
        final SlashCommandInteraction slashCommandInteraction = slashCommandCreateEvent.getSlashCommandInteraction();
        callEventOnGameThread(() -> DiscordEvents.DISCORD_SLASH_COMMAND.onDiscordSlashCommand(bot, slashCommandInteraction));
    }

    @Override
    public void onComponentCreate(MessageComponentCreateEvent messageComponentCreateEvent) {
        MessageComponentInteraction messageComponentInteraction = messageComponentCreateEvent.getMessageComponentInteraction();

        if(messageComponentInteraction.asButtonInteraction().isPresent()) {
            final ButtonInteraction buttonInteraction = messageComponentInteraction.asButtonInteraction().get();
            callEventOnGameThread(() -> DiscordEvents.DISCORD_BUTTON.onDiscordButton(bot, buttonInteraction));
        } else if(messageComponentInteraction.asSelectMenuInteraction().isPresent()) {
            final SelectMenuInteraction selectMenuInteraction = messageComponentInteraction.asSelectMenuInteraction().get();
            callEventOnGameThread(() -> DiscordEvents.DISCORD_SELECT_MENU.onDiscordSelectMenu(bot, selectMenuInteraction));
        }
    }

    private static void callEventOnGameThread(Runnable runnable) {
        ServerTask serverTask = new ServerTask(Integer.MIN_VALUE, runnable);
        CarpetServer.minecraft_server.send(serverTask);
    }
}
