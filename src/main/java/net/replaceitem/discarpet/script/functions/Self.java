package net.replaceitem.discarpet.script.functions;

import carpet.script.Context;
import carpet.script.annotation.ScarpetFunction;
import carpet.script.exception.InternalExpressionException;
import net.replaceitem.discarpet.Discarpet;
import net.replaceitem.discarpet.Util;
import net.replaceitem.discarpet.config.Bot;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.user.User;
import org.javacord.api.entity.user.UserStatus;

@SuppressWarnings("unused")
public class Self {
    @ScarpetFunction
    public User dc_get_bot_user(Context c) {
        Bot bot = Discarpet.getBotInContext(c,"dc_get_bot_user");
        return bot.getApi().getYourself();
    }


    @ScarpetFunction
    public void dc_set_activity(Context ctx, String activityName, String text) {
        Bot bot = Discarpet.getBotInContext(ctx,"dc_set_activity");
        ActivityType activityType = Util.activityTypeByName(activityName).orElseThrow(() -> new InternalExpressionException("Invalid activity type '" + activityName + "'"));
        bot.getApi().updateActivity(activityType, text);
    }

    @ScarpetFunction
    public void dc_set_status(Context ctx, String statusName) {
        Bot bot = Discarpet.getBotInContext(ctx,"dc_set_status");
        UserStatus status = Util.statusByName(statusName).orElseThrow(() -> new InternalExpressionException("Invalid user status '" + statusName + "'"));
        bot.getApi().updateStatus(status);
    }
}
