package net.replaceitem.discarpet.config;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.replaceitem.discarpet.script.events.DiscarpetEventsListener;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.intent.Intent;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Bot {
	private final JDA jda;
	private final String id;
	
	public JDA getJda() {
		return jda;
	}

	public String getId() {
		return id;
	}
	
	private static CompletableFuture<JDA> awaitReady(JDABuilder builder) {
		CompletableFuture<JDA> cf = new CompletableFuture<>();
		Thread.startVirtualThread(() -> {
            try {
				cf.complete(builder.build().awaitReady());
			} catch (Exception e) {
				cf.completeExceptionally(e);
			}
        });
		return cf;
	}
	
	private static CompletableFuture<Void> awaitShutdown(JDA jda) {
		CompletableFuture<Void> cf = new CompletableFuture<>();
		Thread.startVirtualThread(() -> {
            try {
				jda.shutdown();
                //noinspection ResultOfMethodCallIgnored
                jda.awaitShutdown(Duration.ofSeconds(5));
				cf.complete(null);
			} catch (Exception e) {
				cf.completeExceptionally(e);
			}
        });
		return cf;
	}
	
	public static CompletableFuture<Bot> create(String id, String token, Set<GatewayIntent> intents) {
		CompletableFuture<JDA> cf = awaitReady(
				JDABuilder.createLight(token).enableIntents(intents)
		);
		cf.orTimeout(10, TimeUnit.SECONDS);
		return cf.thenApply(jda -> new Bot(id, jda));
	}

	public Bot(String id, JDA api) {
		this.id = id;
		this.jda = api;
		this.jda.addListener(new DiscarpetEventsListener(this));
	}
	
	public String getInvite() {
		return jda.getInviteUrl();
	}

	public CompletableFuture<Void> disconnect() {
		return awaitShutdown(jda);
	}
}
