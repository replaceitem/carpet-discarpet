package net.replaceitem.discarpet.script.functions;

import carpet.script.Context;
import carpet.script.annotation.ScarpetFunction;
import carpet.script.value.Value;
import net.dv8tion.jda.api.entities.User;
import net.replaceitem.discarpet.Discarpet;
import net.replaceitem.discarpet.config.Bot;
import net.replaceitem.discarpet.script.parsable.Parser;
import net.replaceitem.discarpet.script.parsable.parsables.PresenceUpdaterParsable;

@SuppressWarnings("unused")
public class Self {
    @ScarpetFunction
    public User dc_get_bot_user(Context c) {
        Bot bot = Discarpet.getBotInContext(c,"dc_get_bot_user");
        return bot.getJda().getSelfUser();
    }

    @ScarpetFunction
    public void dc_update_presence(Context ctx, Value presence) {
        Bot bot = Discarpet.getBotInContext(ctx,"dc_set_status");
        Parser.parseType(ctx, presence, PresenceUpdaterParsable.class).apply(bot.getJda().getPresence());
    }
}
