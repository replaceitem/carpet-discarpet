package Discarpet;

import Discarpet.commands.DiscarpetCommand;
import Discarpet.script.events.ChatEvents;
import Discarpet.script.events.DiscordEvents;
import Discarpet.script.functions.Get;
import Discarpet.script.functions.Embeds;
import Discarpet.script.functions.Sending;
import Discarpet.script.functions.Set;
import Discarpet.script.functions.ValueFromId;
import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.script.CarpetExpression;
import carpet.script.CarpetScriptHost;
import carpet.script.Context;
import carpet.script.ScriptHost;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;


public class Discarpet implements CarpetExtension, ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Discarpet");

	public static Map<String,Bot> discordBots = new HashMap<>();

	@Override
	public void onInitialize() {
		Discarpet.LOGGER.info("Discarpet loaded");
		CarpetServer.manageExtension(this);
	}


	@Override
	public void onGameStarted() {

	}

	@Override
	public void onServerLoaded(MinecraftServer server) {
		loadBots(null);
	}

	@Override
	public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
		DiscarpetCommand.register(dispatcher);
	}

	@Override
	public void scarpetApi(CarpetExpression expression) {
		DiscordEvents.noop();
		ChatEvents.noop();

		Set.apply(expression.getExpr());
		Get.apply(expression.getExpr());
		Sending.apply(expression.getExpr());
		ValueFromId.apply(expression.getExpr());
		Embeds.apply(expression.getExpr());
	}


	public static void loadBots(ServerCommandSource source) {
		if(source != null) {
			try {
				source.getPlayer();
			} catch (CommandSyntaxException e) {
				source = null;
			}
		}
		discordBots.clear();
		try {
			Path configDir = FabricLoader.getInstance().getConfigDir().normalize();
			Files.createDirectories(configDir);
			Path configFile = configDir.resolve("discarpet.json").normalize();
			boolean created = Config.load(configFile.toFile());
			if(created) {
				Discarpet.LOGGER.warn("[Discarpet] No config file present, generating empty file. To specify your bots, edit config/discarpet.json");
			}
			Config.getInstance().toFile(configFile.toFile());
			if(created) return;
			for (BotConfig s : Config.getInstance().BOTS) {
				Bot bot = new Bot(s.BOT_ID,s.BOT_TOKEN,source);
				if(bot.getApi() == null) continue;
				discordBots.put(s.BOT_ID, bot);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static Bot getBotInContext(Context c) {
		return getBotInHost(c.host);
	}

	public static Bot getBotInHost(ScriptHost h) {
		if(h == null) return null;
		if(((CarpetScriptHost) h).appConfig == null) return null;
		Value botKeyValue = ((CarpetScriptHost) h).appConfig.get(StringValue.of("bot"));
		if (botKeyValue == null) return null;
		String key = botKeyValue.getString();
		if (key == null) return null;
		return discordBots.get(key);
	}

	public static void scarpetException(String function, String type, int parameter) {
		if(parameter < 0 || parameter > 8) throw new InternalExpressionException("If you read this, replaceitem messed up very badly, and you should contact him through Discord asap (see github page of Discarpet)");
		throw new InternalExpressionException("'" + function + "' requires a " + type + " as the " + numericWords[parameter] + " argument");
	}

	public static void scarpetNoBotException(String function) {
		throw new InternalExpressionException(function + " requires a valid bot to be set in in the app config");
	}

	//writing this made me realize how lazy i am
	static final private String[] numericWords = {"first","second","third","fourth","fifth","sixth","seventh","eighth","ninth"};

}
