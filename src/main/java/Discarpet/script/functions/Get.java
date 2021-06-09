package Discarpet.script.functions;

import Discarpet.Bot;
import carpet.script.Context;
import carpet.script.annotation.ScarpetFunction;

import static Discarpet.Discarpet.*;

import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class Get {
	@ScarpetFunction
	public String dc_get_display_name(User user, Server server) {
		return server.getDisplayName(user);
	}
	
	@ScarpetFunction
	public User dc_get_bot_user(Context c) {
		Bot bot = getBotInContext(c);
		if (bot == null) scarpetNoBotException("dc_get_bot_user");
		return bot.getApi().getYourself();
	}
}
