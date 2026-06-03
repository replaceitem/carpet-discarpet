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
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.replaceitem.discarpet.bot.Bot;
import net.replaceitem.discarpet.bot.BotManager;
import net.replaceitem.discarpet.commands.DiscarpetCommand;
import net.replaceitem.discarpet.config.ConfigManager;
import net.replaceitem.discarpet.script.events.DiscordEvents;
import net.replaceitem.discarpet.script.events.MiscEvents;
import net.replaceitem.discarpet.script.util.Registration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jspecify.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Discarpet implements CarpetExtension, ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Discarpet");
    @Nullable
	public static ConfigManager configManager;
    public static BotManager botManager = new BotManager();

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
		Registration.registerSchemas();
	}

	@Override
	public void onServerClosed(MinecraftServer server) {
        botManager.disconnectAll();
	}

	@Override
	public void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext) {
		DiscarpetCommand.register(dispatcher);
	}

    public static ConfigManager getConfigManager() {
        return Objects.requireNonNull(configManager, "Config is accessed before it is initialized");
    }

	public static void loadConfig(@Nullable CommandSourceStack source) {
        boolean newlyCreatedConfig = getConfigManager().loadAndUpdate();
		if(newlyCreatedConfig) {
			Discarpet.LOGGER.info("No Discarpet configuration file found, creating one. Edit config/discarpet.json to add your bots");
			return;
		}
		botManager.loadBots(source);
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

    @Nullable
	public static Bot getBotInHost(@Nullable ScriptHost h) {
		if(h == null) return null;
		Value botKeyValue = ((CarpetScriptHost) h).appConfig.get(StringValue.of("bot"));
		if (botKeyValue == null) return null;
		String key = botKeyValue.getString();
		return botManager.getBot(key);
	}

	public static InternalExpressionException scarpetNoBotException(String function) {
		return new InternalExpressionException(function + " requires a valid bot to be set in in the app config");
	}
}
