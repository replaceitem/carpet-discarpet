package net.replaceitem.discarpet;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.script.CarpetScriptHost;
import carpet.script.Context;
import carpet.script.ScriptHost;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import com.mojang.brigadier.CommandDispatcher;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.replaceitem.discarpet.commands.DiscarpetCommand;
import net.replaceitem.discarpet.config.Bot;
import net.replaceitem.discarpet.config.BotConfig;
import net.replaceitem.discarpet.config.ConfigManager;
import net.replaceitem.discarpet.script.events.DiscordEvents;
import net.replaceitem.discarpet.script.events.MiscEvents;
import net.replaceitem.discarpet.script.util.Registration;
import net.replaceitem.discarpet.script.util.ValueUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class Discarpet implements CarpetExtension, ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Discarpet");
	protected static ConfigManager configManager;

	public static Map<String, Bot> discordBots = Collections.synchronizedMap(new HashMap<>());

	@Override
	public void onInitialize() {
		try {
			Path configDir = FabricLoader.getInstance().getConfigDir().normalize();
			Files.createDirectories(configDir);
			File configFile = configDir.resolve("discarpet.json").normalize().toFile();
			configManager = new ConfigManager(configFile);
		} catch (IOException e) {
			Discarpet.LOGGER.error("Error loading Discarpet configuration file");
		}
		
		loadConfig(null);
		
		CarpetServer.manageExtension(this);
		Discarpet.LOGGER.info("Discarpet loaded");
	}


	@Override
	public void onGameStarted() {
		DiscordEvents.noop();
		MiscEvents.noop();
		Registration.registerValueCasters();
		Registration.registerDiscordValues();
		Registration.registerMisc();
		Registration.registerFunctions();
		Registration.registerParsables();
	}

	@Override
	public void onServerClosed(MinecraftServer server) {
		LOGGER.info("Disconnecting all Discord bots");
		discordBots.forEach((s, bot) -> bot.disconnect());
	}

	@Override
	public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandBuildContext) {
		DiscarpetCommand.register(dispatcher);
	}

	public static void loadConfig(ServerCommandSource source) {
		boolean newlyCreatedConfig = configManager.loadAndUpdate();
		if(newlyCreatedConfig) {
			Discarpet.LOGGER.info("No Discarpet configuration file found, creating one. Edit config/discarpet.json to add your bots");
			return;
		}
		loadBots(source);
	}

	public static void loadBots(ServerCommandSource source) {
		CompletableFuture.allOf(
				discordBots.values().stream()
						.map(bot -> bot == null ? null : bot.disconnect())
						.filter(Objects::nonNull)
						.toArray(CompletableFuture[]::new)
		);
		discordBots.clear();

		for (BotConfig botConfig : configManager.getConfig().BOTS) {
			if(botConfig == null) continue;
			String botId = botConfig.BOT_ID;
            try {
                Set<GatewayIntent> intents = botConfig.INTENTS.stream()
                        .map(s -> ValueUtil.getEnum(GatewayIntent.class, s)
								.orElseThrow(() -> new IllegalArgumentException("Unknown intent: " + s))
						)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());
				
				MemberCachePolicy memberCachePolicy = switch (botConfig.MEMBER_CACHE_POLICY.toLowerCase()) {
					case "all" -> MemberCachePolicy.ALL;
					case "online" -> MemberCachePolicy.ONLINE;
					case "voice" -> MemberCachePolicy.VOICE;
					case "pending" -> MemberCachePolicy.PENDING;
					case "booster" -> MemberCachePolicy.BOOSTER;
					case "owner" -> MemberCachePolicy.OWNER;
					case "none" -> MemberCachePolicy.NONE;
                    default -> throw new IllegalArgumentException("Unknown member cache policy: " + botConfig.MEMBER_CACHE_POLICY);
                };

                CompletableFuture<Bot> botCompletableFuture = Bot.create(botId, botConfig.BOT_TOKEN, intents, memberCachePolicy);
                botCompletableFuture.exceptionally(throwable -> {
                    String error = "Could not login bot " + botId;
                    LOGGER.warn(error, throwable);
                    if(source != null) source.sendFeedback(() -> Text.literal(error + ": " + throwable.getMessage()).formatted(Formatting.RED),false);
                    return null;
                });
                botCompletableFuture.thenAccept(bot -> {
                    if(bot == null) return;
                    discordBots.put(bot.getId(), bot);
                    String msg = "Bot " + botId + " sucessfully logged in";
                    if(!intents.isEmpty()) {
                        msg += " with intents " + intents.stream().map(Enum::toString).collect(Collectors.joining(","));
                    }
                    MutableText text = Text.literal(msg).styled(style -> style.withColor(Formatting.GREEN));
                    if(source != null) source.sendFeedback(() -> text,false);
                    LOGGER.info(msg);
                });
            } catch (Exception e) {
				String error = "Could not create bot " + botId;
				LOGGER.warn(error, e);
				if(source != null) source.sendFeedback(() -> Text.literal(error + ": " + e.getMessage()).formatted(Formatting.RED),false);
            }
        }
	}

	public static boolean isScarpetGraphicsInstalled() {
		return FabricLoader.getInstance().isModLoaded("scarpet-graphics");
	}


	public static Bot getBotInContext(Context c, String source) {
		return getBotInHost(c.host, source);
	}

	public static Bot getBotInHost(ScriptHost h, String source) {
		Bot bot = getBotInHost(h);
		if (bot == null) throw scarpetNoBotException(source);
		return bot;
	}

	public static Bot getBotInHost(ScriptHost h) {
		if(h == null) return null;
		Value botKeyValue = ((CarpetScriptHost) h).appConfig.get(StringValue.of("bot"));
		if (botKeyValue == null) return null;
		String key = botKeyValue.getString();
		return discordBots.get(key);
	}

	public static InternalExpressionException scarpetNoBotException(String function) {
		return new InternalExpressionException(function + " requires a valid bot to be set in in the app config");
	}
}
