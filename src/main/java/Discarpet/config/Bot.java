package Discarpet.config;

import Discarpet.script.events.DiscordEvents;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.intent.Intent;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.interaction.ButtonInteraction;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.interaction.SelectMenuInteraction;

import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static Discarpet.Discarpet.LOGGER;

public class Bot {
	public DiscordApi api;
	public String id;
	public DiscordApi getApi() {
		return api;
	}

	public Bot(String id, String token, HashSet<Intent> intents, ServerCommandSource source) {
		this.id = id;
		try {
			DiscordApiBuilder apiBuilder = new DiscordApiBuilder();
			apiBuilder.setToken(token);
			apiBuilder.setAllIntentsWhere(intent -> !intent.isPrivileged() || intents.contains(intent));
			CompletableFuture<DiscordApi> cf = apiBuilder.login();
			cf.orTimeout(10, TimeUnit.SECONDS);
			api = cf.get();

			String msg;
			if(intents.size() == 0) {
				msg = "Bot " + id + " sucessfully logged in";
			} else {
				msg = "Bot " + id + " sucessfully logged in with intents " + intents.stream().map(Enum::toString).collect(Collectors.joining(","));
			}
			if(source != null) source.sendFeedback(new LiteralText(msg),false);
			LOGGER.info(msg);
			api.getListeners().keySet().forEach(globallyAttachableListener -> api.removeListener(globallyAttachableListener));
			api.addMessageCreateListener(event -> {
				DiscordEvents.DISCORD_MESSAGE.onDiscordMessage(this,event.getMessage());
			});

			api.addReactionAddListener(event -> {
				Optional<Reaction> optionalReaction = event.getReaction();
				if(optionalReaction.isPresent()) {
					Reaction reaction = optionalReaction.get();
					event.requestUser().thenAccept(user -> {
						DiscordEvents.DISCORD_REACTION.onDiscordReaction(this,reaction,user,true);
					});
				}

			});
			api.addReactionRemoveListener(event -> {
				Optional<Reaction> optionalReaction = event.getReaction();
				if(optionalReaction.isPresent()) {
					Reaction reaction = optionalReaction.get();
					event.requestUser().thenAccept(user -> {
						DiscordEvents.DISCORD_REACTION.onDiscordReaction(this,reaction,user,false);
					});
				}
			});
			api.addSlashCommandCreateListener(event -> {
				DiscordEvents.DISCORD_SLASH_COMMAND.onDiscordSlashCommand(this,event.getSlashCommandInteraction());
			});
			api.addMessageComponentCreateListener(event -> {
				MessageComponentInteraction messageComponentInteraction = event.getMessageComponentInteraction();

				Optional<ButtonInteraction> buttonInteraction = messageComponentInteraction.asButtonInteraction();
				buttonInteraction.ifPresent(interaction -> DiscordEvents.DISCORD_BUTTON.onDiscordButton(this, interaction));

				Optional<SelectMenuInteraction> selectMenuInteraction = messageComponentInteraction.asSelectMenuInteraction();
				selectMenuInteraction.ifPresent(interaction -> DiscordEvents.DISCORD_SELECT_MENU.onDiscordSelectMenu(this, interaction));
			});
		} catch (CompletionException | InterruptedException | ExecutionException ce) {
			String error = "Could not login bot " + id;
			LOGGER.warn(error,ce);
			if(source != null) source.sendFeedback(new LiteralText(error).formatted(Formatting.RED),false);
			if(api != null) api.disconnect();
			api = null;
		}
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Bot bot && this.id.equals(bot.id);
	}

	public String getInvite() {
		return api.createBotInvite();
	}
}
