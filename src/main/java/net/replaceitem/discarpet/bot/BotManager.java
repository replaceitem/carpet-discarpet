package net.replaceitem.discarpet.bot;

import net.dv8tion.jda.api.requests.GatewayIntent;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.replaceitem.discarpet.Discarpet;
import net.replaceitem.discarpet.config.BotConfig;
import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class BotManager {
    private final Map<String, Bot> bots = Collections.synchronizedMap(new HashMap<>());

    public void loadBots(@Nullable CommandSourceStack source) {
        CompletableFuture.runAsync(() -> {
            CompletableFuture.allOf(
                    bots.values().stream()
                            .map(Bot::disconnect)
                            .toArray(CompletableFuture[]::new)
            ).join();
            bots.clear();
            var config = Discarpet.configManager;
            if(config == null) return;
            CompletableFuture.allOf(
                    config.getConfig().BOTS.stream()
                            .filter(Objects::nonNull)
                            .map(botConfig ->
                                    loadBot(botConfig, source).thenAccept(bot -> bots.put(bot.id(), bot))
                            )
                            .toArray(CompletableFuture[]::new)
            ).join();
        });
    }

    private CompletableFuture<Bot> loadBot(BotConfig botConfig, @Nullable CommandSourceStack source) {
        String botId = botConfig.BOT_ID;
        try {
            var cf = BotFactory.createFromConfig(botConfig);
            cf.exceptionally(throwable -> {
                String error = "Could not login bot " + botId;
                Discarpet.LOGGER.error(error, throwable);
                if(source != null) source.sendFailure(Component.literal(error + ": " + throwable.getMessage()));
                //noinspection DataFlowIssue
                return null;
            });
            cf.thenAccept(bot -> {
                String msg = "Bot " + botId + " sucessfully logged in";
                Set<GatewayIntent> intents = bot.getIntents();
                if(!intents.isEmpty()) {
                    msg += " with intents " + intents.stream().map(Enum::toString).collect(Collectors.joining(","));
                }
                MutableComponent text = Component.literal(msg).withStyle(style -> style.withColor(ChatFormatting.GREEN));
                if(source != null) source.sendSuccess(() -> text,false);
                Discarpet.LOGGER.info(msg);
            });
            return cf;
        } catch (Exception e) {
            String error = "Could not create bot " + botId;
            Discarpet.LOGGER.error(error, e);
            if(source != null) source.sendFailure(Component.literal(error + ": " + e.getMessage()));
            return CompletableFuture.failedFuture(e);
        }
    }

    public void disconnectAll() {
        Discarpet.LOGGER.info("Disconnecting all Discord bots");
        CompletableFuture.allOf(bots.values().stream().map(Bot::disconnect).toArray(CompletableFuture[]::new)).thenRun(() ->
                Discarpet.LOGGER.info("All bots disconnected")
        );
    }

    @Nullable
    public Bot getBot(String key) {
        return bots.get(key);
    }

    public Set<String> getBotIds() {
        return bots.keySet();
    }
}
