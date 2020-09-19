package scarpet.discord;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.settings.SettingsManager;
import carpet.utils.Messenger;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;


public class Discarpet implements CarpetExtension {
	public static void noop() {
	}

	public static boolean discordEnabled = false;
	public static Bot discordBot;
	public static Chat minecraftChat;
	private static SettingsManager mySettingManager;
	public static LiteralText customHeader = new LiteralText("");
	public static LiteralText customFooter = new LiteralText("");
	public static boolean updateTabHeader = false;

	static {
		mySettingManager = new SettingsManager("1.0", "discarpet", "Discarpet");
		CarpetServer.manageExtension(new Discarpet());
	}

	@Override
	public void onGameStarted() {
		//System.out.println("Game started! (Test log) -----------------------------------------------------------------------");
		mySettingManager.parseSettingsClass(Settings.class);

		Settings.inviteLink = false;
		Settings.clearBuffer = false;

		// set-up a snooper to observe how rules are changing in carpet
		CarpetServer.settingsManager.addRuleObserver((serverCommandSource, currentRuleState, originalUserTest) ->
		{
			switch (currentRuleState.name) {
				case "botToken":
					Messenger.m(serverCommandSource, "l Bot token changed, make sure to restart the game to apply changes");
				case "inviteLink":
					if (currentRuleState.getBoolValue()) {
						String invite = this.discordBot.getInvite();
						if (invite != null) {
							Text clickText = Texts.bracketed(new LiteralText("Click here to get invite link for the bot")).styled((style -> {
								style.withColor(Formatting.BLUE).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, invite));
								return style;
							}));
							serverCommandSource.sendFeedback(clickText, true);
							Settings.inviteLink = false;
						}
					}
				case "clearBuffer":
					if (currentRuleState.getBoolValue()) {
						Settings.clearBuffer = false;
						discordBot.messageBuffer.clear();
						minecraftChat.chatBuffer.clear();
						Messenger.m(serverCommandSource, "l Cleared chat and bot buffer");
					}
				default:
			}
		});


	}

	@Override
	public void onServerLoaded(MinecraftServer server) {
		discordBot = new Bot(Settings.botToken);
		minecraftChat = new Chat();
		if (discordBot != null) {
			discordEnabled = true;
		}
	}

	@Override
	public void onTick(MinecraftServer server) {
		// no need to add this.
	}

	@Override
	public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
		//ExampleCommand.register(dispatcher);
	}

	@Override
	public SettingsManager customSettingsManager() {
		// this will ensure that our settings are loaded properly when world loads
		return mySettingManager;
	}

	@Override
	public void onPlayerLoggedIn(ServerPlayerEntity player) {
		//
	}

	@Override
	public void onPlayerLoggedOut(ServerPlayerEntity player) {
		//
	}
}
