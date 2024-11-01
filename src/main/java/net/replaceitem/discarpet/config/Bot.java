package net.replaceitem.discarpet.config;

import net.replaceitem.discarpet.script.events.DiscarpetEventsListener;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.intent.Intent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Bot {
	private final DiscordApi api;
	private final String id;
	
	public DiscordApi getApi() {
		return api;
	}

	public String getId() {
		return id;
	}
	
	public static CompletableFuture<Bot> create(String id, String token, Set<Intent> intents) {
		DiscordApiBuilder apiBuilder = new DiscordApiBuilder();
		apiBuilder.setToken(token);
		apiBuilder.setAllIntentsWhere(intent -> !intent.isPrivileged() || intents.contains(intent));
		CompletableFuture<DiscordApi> cf = apiBuilder.login();
		cf.orTimeout(10, TimeUnit.SECONDS);
		return cf.thenApply(discordApi -> new Bot(id, discordApi));
	}

	public Bot(String id, DiscordApi api) {
		this.id = id;
		this.api = api;
		this.api.addListener(new DiscarpetEventsListener(this));
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Bot bot && this.id.equals(bot.id);
	}

	public String getInvite() {
		return api.createBotInvite();
	}

	public CompletableFuture<Void> disconnect() {
		return this.api.disconnect();
	}
}
