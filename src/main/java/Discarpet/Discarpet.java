package Discarpet;

import Discarpet.commands.DiscarpetCommand;
import Discarpet.script.events.ChatEvents;
import Discarpet.script.events.DiscordEvents;
import Discarpet.script.functions.Get;
import Discarpet.script.functions.Embeds;
import Discarpet.script.functions.Interactions;
import Discarpet.script.functions.Sending;
import Discarpet.script.functions.Set;
import Discarpet.script.functions.ValueFromId;
import Discarpet.script.values.ChannelValue;
import Discarpet.script.values.MessageValue;
import Discarpet.script.values.ServerValue;
import Discarpet.script.values.SlashCommandInteractionValue;
import Discarpet.script.values.UserValue;
import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.script.CarpetExpression;
import carpet.script.CarpetScriptHost;
import carpet.script.Context;
import carpet.script.ScriptHost;
import carpet.script.annotation.AnnotationParser;
import carpet.script.annotation.OutputConverter;
import carpet.script.annotation.SimpleTypeConverter;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.intent.Intent;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class Discarpet implements CarpetExtension, ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Discarpet");

	public static Map<String,Bot> discordBots = new HashMap<>();

	@Override
	public void onInitialize() {
		DiscordEvents.noop();
		ChatEvents.noop();
		CarpetServer.manageExtension(this);
		Discarpet.LOGGER.info("Discarpet loaded");
	}


	@Override
	public void onGameStarted() {
		SimpleTypeConverter.registerType(UserValue.class, User.class, UserValue::getUser, "user");
		SimpleTypeConverter.registerType(ServerValue.class, Server.class, ServerValue::getServer, "server");
		SimpleTypeConverter.registerType(MessageValue.class, Message.class, MessageValue::getMessage, "message");
		SimpleTypeConverter.registerType(ChannelValue.class, ServerTextChannel.class, ChannelValue::getChannel, "channel");
		SimpleTypeConverter.registerType(SlashCommandInteractionValue.class, SlashCommandInteraction.class, SlashCommandInteractionValue::getSlashCommandInteraction, "slash_command_interaction");
		OutputConverter.registerToValue(User.class, UserValue::new);
		AnnotationParser.parseFunctionClass(Get.class);
		AnnotationParser.parseFunctionClass(Sending.class);
		AnnotationParser.parseFunctionClass(Set.class);
		AnnotationParser.parseFunctionClass(ValueFromId.class);
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
		Sending.apply(expression.getExpr());
		Embeds.apply(expression.getExpr());
		Interactions.apply(expression.getExpr());
	}


	public static void loadBots(ServerCommandSource source) {
		if(source != null) {
			try {
				source.getPlayer();
			} catch (CommandSyntaxException e) {
				source = null;
			}
		}
		discordBots.forEach((s, bot) -> {
			if(bot != null) {
				if(bot.api != null) bot.api.disconnect();
				bot.api = null;
			}
		});
		discordBots.clear();
		try {
			Path configDir = FabricLoader.getInstance().getConfigDir().normalize();
			Files.createDirectories(configDir);
			Path configFile = configDir.resolve("discarpet.json").normalize();
			boolean created = Config.load(configFile.toFile());
			if(created) {
				Discarpet.LOGGER.warn("No config file present, generating empty file. To specify your bots, edit config/discarpet.json");
			}
			Config.getInstance().toFile(configFile.toFile());
			if(created) return;
			for (BotConfig s : Config.getInstance().BOTS) {
				if(s == null) continue;
				HashSet<Intent> intents = new HashSet<>();
				if(s.PRESENCE_INTENT) intents.add(Intent.GUILD_PRESENCES);
				if(s.MEMBER_INTENT) intents.add(Intent.GUILD_MEMBERS);
				Bot bot = new Bot(s.BOT_ID,s.BOT_TOKEN,intents,source);
				if(bot.api != null) discordBots.put(bot.id, bot);
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
