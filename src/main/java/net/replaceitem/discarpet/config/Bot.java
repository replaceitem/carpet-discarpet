package net.replaceitem.discarpet.config;

import net.minecraft.text.MutableText;
import net.replaceitem.discarpet.script.events.DiscarpetEventsListener;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.intent.Intent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static net.replaceitem.discarpet.Discarpet.LOGGER;

public class Bot {
	public DiscordApi api;
	public String id;
	public DiscordApi getApi() {
		return api;
	}

	public Bot(String id, String token, Set<Intent> intents, ServerCommandSource source) {
		this.id = id;
		try {
			DiscordApiBuilder apiBuilder = new DiscordApiBuilder();
			apiBuilder.setToken(token);
			apiBuilder.setAllIntentsWhere(intent -> !intent.isPrivileged() || intents.contains(intent));
			CompletableFuture<DiscordApi> cf = apiBuilder.login();
			cf.orTimeout(10, TimeUnit.SECONDS);
			api = cf.get();
			
			String msg = "Bot " + id + " sucessfully logged in";
			if(intents.size() != 0) {
				msg += " with intents " + intents.stream().map(Enum::toString).collect(Collectors.joining(","));
			}

			MutableText text = Text.literal(msg);

			if(source != null) source.sendFeedback(() -> text,false);
			LOGGER.info(msg);
			
			api.getListeners().keySet().forEach(globallyAttachableListener -> api.removeListener(globallyAttachableListener));
			api.addListener(new DiscarpetEventsListener(this));
		} catch (CompletionException | InterruptedException | ExecutionException ce) {
			String error = "Could not login bot " + id;
			LOGGER.warn(error,ce);
			if(source != null) source.sendFeedback(() -> Text.literal(error).formatted(Formatting.RED),false);
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
