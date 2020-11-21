package Discarpet;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.script.CarpetExpression;
import carpet.settings.ParsedRule;
import carpet.settings.SettingsManager;
import carpet.utils.Messenger;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Discarpet implements CarpetExtension, ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Discarpet");

	public static boolean discordEnabled = false;
	public static Bot discordBot;
	public static SettingsManager mySettingManager;

	@Override
	public void onInitialize() {
		Discarpet.LOGGER.info("Discarpet loaded");
		mySettingManager = new SettingsManager("1.0", "discarpet", "Discarpet");
		CarpetServer.manageExtension(this);
	}


	@Override
	public void onGameStarted() {
		mySettingManager.parseSettingsClass(Settings.class);
	}

	public static void ruleObserver(ServerCommandSource source, ParsedRule<?> rule) {
		switch (rule.name) {
	    	case "botToken":
	    		Messenger.m(source, "l Bot token changed, make sure to restart the game to apply changes");
	    	case "inviteLink":
	    		if (rule.getBoolValue()) {
	    			String invite = discordBot.getInvite();
	    			if (invite != null) {
	    				/*
						Text clickText = Texts.bracketed((new LiteralText("Click here to get invite link for the bot")).styled((style -> {

							style.withColor(Formatting.BLUE).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, invite));
							//return style.withColor(Formatting.GREEN).withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, String.valueOf(l))).withHoverEvent(new HoverEvent(net.minecraft.text.HoverEvent.Action.SHOW_TEXT, new TranslatableText("chat.copy.click"))).withInsertion(String.valueOf(l));
							return style;
						})));
	    				 */
	    				Text text = ((new LiteralText("Click here to get the invite link for the bot")).styled((style) -> {
	    					return style.withColor(Formatting.BLUE).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, invite)).withHoverEvent(new HoverEvent(net.minecraft.text.HoverEvent.Action.SHOW_TEXT, new LiteralText("Click to open the invite link"))).withInsertion(invite);
	    				}));
	    				//serverCommandSource.sendFeedback(new TranslatableText("commands.seed.success", new Object[]{text}), false);

	    				source.sendFeedback(text, true);
	    				Settings.inviteLink = false;
	    		}
	    	}
		}
	}
	@Override
	public void onServerLoaded(MinecraftServer server) {
		mySettingManager.attachServer(server);
		discordBot = new Bot(Settings.botToken);
		if (discordBot != null) {
			discordEnabled = true;
		}
	}

	@Override
	public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
		mySettingManager.registerCommand(dispatcher);
	}

	@Override
	public void scarpetApi(CarpetExpression expression) {
		DiscordFunctions.apply(expression.getExpr());
	}
	
	

}
