package net.replaceitem.discarpet.script.events;

import carpet.CarpetServer;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.GenericComponentInteractionCreateEvent;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.context.MessageContextInteraction;
import net.dv8tion.jda.api.interactions.commands.context.UserContextInteraction;
import net.dv8tion.jda.api.interactions.components.ComponentInteraction;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonInteraction;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenuInteraction;
import net.dv8tion.jda.api.interactions.modals.ModalInteraction;
import net.minecraft.server.ServerTask;
import net.replaceitem.discarpet.config.Bot;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

@ParametersAreNonnullByDefault
public class DiscarpetEventsListener extends ListenerAdapter {
    
    protected final Bot bot;
    
    public DiscarpetEventsListener(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        callEventOnGameThread(() -> DiscordEvents.DISCORD_MESSAGE.run(bot, message));
    }

    @Override
    public void onMessageUpdate(MessageUpdateEvent event) {
        Message message = event.getMessage();
        // TODO can the parameter count be changed on this event, or will that break apps using 3 params?
        callEventOnGameThread(() -> DiscordEvents.DISCORD_MESSAGE_EDIT.run(bot, message, Optional.empty(), true));
    }

    @Override
    public void onMessageDelete(MessageDeleteEvent event) {
        String messageId = event.getMessageId();
        MessageChannelUnion channel = event.getChannel();
        callEventOnGameThread(() -> DiscordEvents.DISCORD_MESSAGE_DELETE.run(bot, messageId, channel));
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        MessageReaction reaction = event.getReaction();
        event.retrieveUser().queue(user -> 
                callEventOnGameThread(() -> DiscordEvents.DISCORD_REACTION.run(bot, reaction, user, true))
        );
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        MessageReaction reaction = event.getReaction();
        event.retrieveUser().queue(user -> 
                callEventOnGameThread(() -> DiscordEvents.DISCORD_REACTION.run(bot, reaction, user, false))
        );
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        SlashCommandInteraction interaction = event.getInteraction();
        callEventOnGameThread(() -> DiscordEvents.DISCORD_SLASH_COMMAND.run(bot, interaction));
    }

    @Override
    public void onGenericComponentInteractionCreate(@NotNull GenericComponentInteractionCreateEvent event) {
        ComponentInteraction interaction = event.getInteraction();
        if(interaction instanceof ButtonInteraction buttonInteraction) {
            callEventOnGameThread(() -> DiscordEvents.DISCORD_BUTTON.run(bot, buttonInteraction));
        } else if(interaction instanceof SelectMenuInteraction<?,?> selectMenuInteraction) {
            callEventOnGameThread(() -> DiscordEvents.DISCORD_SELECT_MENU.run(bot, selectMenuInteraction));
        }
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        ModalInteraction interaction = event.getInteraction();
        callEventOnGameThread(() -> DiscordEvents.DISCORD_MODAL.run(bot, interaction));
    }

    @Override
    public void onMessageContextInteraction(@NotNull MessageContextInteractionEvent event) {
        MessageContextInteraction interaction = event.getInteraction();
        callEventOnGameThread(() -> DiscordEvents.DISCORD_MESSAGE_CONTEXT_MENU.run(bot, interaction));
    }

    @Override
    public void onUserContextInteraction(@NotNull UserContextInteractionEvent event) {
        UserContextInteraction interaction = event.getInteraction();
        callEventOnGameThread(() -> DiscordEvents.DISCORD_USER_CONTEXT_MENU.run(bot, interaction));
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        Guild guild = event.getGuild();
        User user = event.getUser();
        callEventOnGameThread(() -> DiscordEvents.DISCORD_SERVER_MEMBER_JOIN.run(bot, guild, user));
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        Guild guild = event.getGuild();
        User user = event.getUser();
        callEventOnGameThread(() -> DiscordEvents.DISCORD_SERVER_MEMBER_LEAVE.run(bot, guild, user));
    }

    private static void callEventOnGameThread(Runnable runnable) {
        if(CarpetServer.minecraft_server == null) return;
        ServerTask serverTask = new ServerTask(Integer.MIN_VALUE, runnable);
        CarpetServer.minecraft_server.send(serverTask);
    }
}
