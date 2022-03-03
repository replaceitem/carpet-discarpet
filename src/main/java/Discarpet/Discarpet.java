package Discarpet;

import Discarpet.commands.DiscarpetCommand;
import Discarpet.config.Bot;
import Discarpet.config.BotConfig;
import Discarpet.config.ConfigManager;
import Discarpet.script.events.ChatEvents;
import Discarpet.script.events.DiscordEvents;
import Discarpet.script.functions.Interactions;
import Discarpet.script.functions.Messages;
import Discarpet.script.functions.Self;
import Discarpet.script.functions.Channels;
import Discarpet.script.functions.Users;
import Discarpet.script.functions.ValueFromId;
import Discarpet.script.values.ButtonInteractionValue;
import Discarpet.script.values.ChannelValue;
import Discarpet.script.values.EmojiValue;
import Discarpet.script.values.MessageValue;
import Discarpet.script.values.ReactionValue;
import Discarpet.script.values.RoleValue;
import Discarpet.script.values.SelectMenuInteractionValue;
import Discarpet.script.values.ServerValue;
import Discarpet.script.values.SlashCommandInteractionValue;
import Discarpet.script.values.UserValue;
import Discarpet.script.values.WebhookValue;
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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.filter.CompositeFilter;
import org.apache.logging.log4j.core.filter.StringMatchFilter;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.intent.Intent;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.entity.webhook.Webhook;
import org.javacord.api.interaction.ButtonInteraction;
import org.javacord.api.interaction.SelectMenuInteraction;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class Discarpet implements CarpetExtension, ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Discarpet");
	protected static ConfigManager configManager;

	public static Map<String,Bot> discordBots = new HashMap<>();

	@Override
	public void onInitialize() {
		DiscordEvents.noop();
		ChatEvents.noop();

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
		SimpleTypeConverter.registerType(ButtonInteractionValue.class, ButtonInteraction.class, ButtonInteractionValue::getButtonInteraction, "button_interaction");
		SimpleTypeConverter.registerType(ChannelValue.class, Channel.class, ChannelValue::getChannel, "channel");
		SimpleTypeConverter.registerType(EmojiValue.class, Emoji.class, EmojiValue::getEmoji, "emoji");
		SimpleTypeConverter.registerType(MessageValue.class, Message.class, MessageValue::getMessage, "message");
		SimpleTypeConverter.registerType(ReactionValue.class, Reaction.class, ReactionValue::getReaction, "message");
		SimpleTypeConverter.registerType(RoleValue.class, Role.class, RoleValue::getRole, "role");
		SimpleTypeConverter.registerType(SelectMenuInteractionValue.class, SelectMenuInteraction.class, SelectMenuInteractionValue::getSelectMenuInteraction, "message");
		SimpleTypeConverter.registerType(ServerValue.class, Server.class, ServerValue::getServer, "server");
		SimpleTypeConverter.registerType(SlashCommandInteractionValue.class, SlashCommandInteraction.class, SlashCommandInteractionValue::getSlashCommandInteraction, "slash_command_interaction");
		SimpleTypeConverter.registerType(UserValue.class, User.class, UserValue::getUser, "user");
		SimpleTypeConverter.registerType(WebhookValue.class, Webhook.class, WebhookValue::getWebhook, "webhook");

		OutputConverter.registerToValue(ButtonInteraction.class, ButtonInteractionValue::new);
		OutputConverter.registerToValue(Channel.class, ChannelValue::new);
		OutputConverter.registerToValue(Emoji.class, EmojiValue::new);
		OutputConverter.registerToValue(Message.class, MessageValue::new);
		OutputConverter.registerToValue(Reaction.class, ReactionValue::new);
		OutputConverter.registerToValue(Role.class, RoleValue::new);
		OutputConverter.registerToValue(SelectMenuInteraction.class, SelectMenuInteractionValue::new);
		OutputConverter.registerToValue(Server.class, ServerValue::new);
		OutputConverter.registerToValue(SlashCommandInteraction.class, SlashCommandInteractionValue::new);
		OutputConverter.registerToValue(User.class, UserValue::new);
		OutputConverter.registerToValue(Webhook.class, WebhookValue::new);

		AnnotationParser.parseFunctionClass(Channels.class);
		AnnotationParser.parseFunctionClass(Messages.class);
		AnnotationParser.parseFunctionClass(Self.class);
		AnnotationParser.parseFunctionClass(Users.class);
		AnnotationParser.parseFunctionClass(ValueFromId.class);
	}

	@Override
	public void onServerLoaded(MinecraftServer server) {
		
	}

	@Override
	public void onServerClosed(MinecraftServer server) {
		LOGGER.info("Disconnecting all Discord bots");
		discordBots.forEach((s, bot) -> bot.getApi().disconnect());
	}

	@Override
	public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
		DiscarpetCommand.register(dispatcher);
	}

	@Override
	public void scarpetApi(CarpetExpression expression) {
		Interactions.apply(expression.getExpr());
	}

	public static void loadConfig(ServerCommandSource source) {
		if(configManager.loadAndUpdate()) {
			Discarpet.LOGGER.info("No Discarpet configuration file found, creating one. Edit config/discarpet.json to add your bots");
		} else {
			if(configManager.getConfig().DISABLE_RECONNECT_LOGS) {
				// hackfix, if you know a better solution, feel free to open a PR
				String loggerName = "org.javacord.core.util.gateway.DiscordWebSocketAdapter";
				LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
				Configuration configuration = ctx.getConfiguration();
				Filter websocketClosedFilter = new StringMatchFilter.Builder().setMatchString("Websocket closed with reason 'Discord commanded a reconnect (Received opcode 7)' and code COMMANDED_RECONNECT (4999) by client!").setOnMatch(Filter.Result.DENY).setOnMismatch(Filter.Result.NEUTRAL).build();
				Filter reconnect1sFilter = new StringMatchFilter.Builder().setMatchString("Trying to reconnect/resume in 1 seconds!").setOnMatch(Filter.Result.DENY).setOnMismatch(Filter.Result.NEUTRAL).build();
				Filter compositeFilter = CompositeFilter.createFilters(new Filter[]{websocketClosedFilter, reconnect1sFilter});
				configuration.addLoggerFilter((org.apache.logging.log4j.core.Logger) LogManager.getLogger(loggerName),compositeFilter);
				LoggerConfig loggerConfig = LoggerConfig.createLogger(false, Level.INFO, loggerName, "true", new AppenderRef[0], null,configuration, compositeFilter);
				configuration.addLogger(loggerName, loggerConfig);
				ctx.updateLoggers();
			}
			loadBots(source);
		}
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

		for (BotConfig s : configManager.getConfig().BOTS) {
			if(s == null) continue;
			HashSet<Intent> intents = new HashSet<>();
			if(s.PRESENCE_INTENT) intents.add(Intent.GUILD_PRESENCES);
			if(s.MEMBER_INTENT) intents.add(Intent.GUILD_MEMBERS);
			Bot bot = new Bot(s.BOT_ID,s.BOT_TOKEN,intents,source);
			if(bot.api != null) discordBots.put(bot.id, bot);
		}
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
		if(((CarpetScriptHost) h).appConfig == null) return null;
		Value botKeyValue = ((CarpetScriptHost) h).appConfig.get(StringValue.of("bot"));
		if (botKeyValue == null) return null;
		String key = botKeyValue.getString();
		if (key == null) return null;
		return discordBots.get(key);
	}

	public static InternalExpressionException scarpetNoBotException(String function) {
		return new InternalExpressionException(function + " requires a valid bot to be set in in the app config");
	}
}
