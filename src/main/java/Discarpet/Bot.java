package Discarpet;

import Discarpet.script.events.DiscordEvents;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.intent.Intent;
import org.javacord.api.entity.message.Reaction;

import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.CompletionException;
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
			api = new DiscordApiBuilder().setToken(token).setAllIntentsWhere(intent -> {
				return !intent.isPrivileged() || intents.contains(intent);
			}).login().join();

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

		} catch (CompletionException ce) {
			String error = "Invalid bot token for bot " + id + "!";
			LOGGER.warn(error);
			if(source != null) source.sendFeedback(new LiteralText(error).formatted(Formatting.RED),false);
			api = null;
		}
	}

	public String getInvite() {
		return api.createBotInvite();
	}
}
