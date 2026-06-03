package net.replaceitem.discarpet.bot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.replaceitem.discarpet.Discarpet;
import net.replaceitem.discarpet.config.BotConfig;
import net.replaceitem.discarpet.script.util.EnumUtil;
import net.replaceitem.discarpet.util.InterruptibleFuture;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class BotFactory {
    public static final ExecutorService CONNECTION_POOL = Executors.newVirtualThreadPerTaskExecutor();

    public static CompletableFuture<Bot> createFromConfig(BotConfig botConfig) {
        String botId = botConfig.BOT_ID;
        Set<GatewayIntent> intents = botConfig.INTENTS.stream().map(BotFactory::getIntentFromString).collect(Collectors.toSet());
        MemberCachePolicy memberCachePolicy = getMemberCachePolicyFromString(botConfig.MEMBER_CACHE_POLICY);

        JDABuilder builder = JDABuilder.createDefault(botConfig.BOT_TOKEN)
                .enableIntents(intents)
                .setMemberCachePolicy(memberCachePolicy);

        return InterruptibleFuture.supplyAsyncWithTimeout(() -> {
            var jda = builder.build().awaitReady();
            return new Bot(botId, jda);
        }, Discarpet.getConfigManager().getConfig().CONNECT_TIMEOUT, TimeUnit.SECONDS, CONNECTION_POOL, "Could not login bot within timeout");
    }


    private static GatewayIntent getIntentFromString(String str) {
        return EnumUtil.getEnumOrThrow(GatewayIntent.class, str, "Unknown intent");
    }

    private static MemberCachePolicy getMemberCachePolicyFromString(String str) {
        return switch (str.toLowerCase()) {
            case "all" -> MemberCachePolicy.ALL;
            case "online" -> MemberCachePolicy.ONLINE;
            case "voice" -> MemberCachePolicy.VOICE;
            case "pending" -> MemberCachePolicy.PENDING;
            case "booster" -> MemberCachePolicy.BOOSTER;
            case "owner" -> MemberCachePolicy.OWNER;
            case "none" -> MemberCachePolicy.NONE;
            default -> throw new IllegalArgumentException("Unknown member cache policy: " + str);
        };
    }
}
