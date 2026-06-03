package net.replaceitem.discarpet.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.replaceitem.discarpet.script.events.DiscarpetEventsListener;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public record Bot(String id, JDA jda) {
    public Bot(String id, JDA jda) {
        this.id = id;
        this.jda = jda;
        this.jda.addEventListener(new DiscarpetEventsListener(this));
    }

    public String getInvite() {
        return jda.getInviteUrl();
    }

    public CompletableFuture<Boolean> disconnect() {
        return CompletableFuture.supplyAsync(() -> {
            jda.shutdown();
            try {
                return jda.awaitShutdown(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException("Could not disconnect within timeout", e);
            }
        });
    }

    public Set<GatewayIntent> getIntents() {
        return jda.getGatewayIntents();
    }
}
