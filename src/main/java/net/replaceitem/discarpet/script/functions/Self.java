package net.replaceitem.discarpet.script.functions;

import carpet.script.Context;
import carpet.script.annotation.ScarpetFunction;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.Value;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.User;
import net.replaceitem.discarpet.Discarpet;
import net.replaceitem.discarpet.config.Bot;
import net.replaceitem.discarpet.script.parsable.Parser;
import net.replaceitem.discarpet.script.parsable.parsables.PresenceUpdaterParsable;
import net.replaceitem.discarpet.script.util.ValueUtil;

@SuppressWarnings("unused")
public class Self {
    @ScarpetFunction
    public User dc_get_bot_user(Context c) {
        Bot bot = Discarpet.getBotInContext(c,"dc_get_bot_user");
        return bot.getJda().getSelfUser();
    }

    @ScarpetFunction
    public void dc_set_activity(Context ctx, String activityName, String text) {
        // TODO move activity param to parsable and add url to it
        Bot bot = Discarpet.getBotInContext(ctx,"dc_set_activity");
        Activity.ActivityType type = ValueUtil.getEnum(Activity.ActivityType.class, activityName).orElseThrow(() -> new InternalExpressionException("Invalid activity type '" + activityName + "'"));
        bot.getJda().getPresence().setActivity(Activity.of(type, text));
    }

    @ScarpetFunction
    public void dc_set_status(Context ctx, String statusName) {
        Bot bot = Discarpet.getBotInContext(ctx,"dc_set_status");
        OnlineStatus onlineStatus = ValueUtil.getEnum(OnlineStatus.class, statusName).orElseThrow(() -> new InternalExpressionException("Invalid user status '" + statusName + "'"));
        bot.getJda().getPresence().setStatus(onlineStatus);
    }
    
    @ScarpetFunction
    public void dc_update_presence(Context ctx, Value presence) {
        Bot bot = Discarpet.getBotInContext(ctx,"dc_set_status");
        Parser.parseType(presence, PresenceUpdaterParsable.class).apply(bot.getJda().getPresence());
    }
}
