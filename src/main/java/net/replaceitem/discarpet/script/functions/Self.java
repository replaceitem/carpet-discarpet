package net.replaceitem.discarpet.script.functions;

import net.replaceitem.discarpet.config.Bot;
import carpet.script.Context;
import carpet.script.annotation.ScarpetFunction;
import carpet.script.exception.InternalExpressionException;
import net.replaceitem.discarpet.Discarpet;
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
    public boolean dc_set_activity(Context ctx, String activity, String text) {
        Bot bot = Discarpet.getBotInContext(ctx,"dc_set_activity");
        ActivityType activityType = null;
        for(ActivityType type : ActivityType.values()) {
            if(type.toString().equalsIgnoreCase(activity)) {
                activityType = type;
                break;
            }
        }
        if (activityType == null) return false;
        bot.getApi().updateActivity(activityType, text);
        return true;
    }

    @ScarpetFunction
    public void dc_set_status(Context ctx, String status) {
        Bot bot = Discarpet.getBotInContext(ctx,"dc_set_status");
        UserStatus userStatus = null;

        for (UserStatus s : UserStatus.values()) {
            if (s.getStatusString().equalsIgnoreCase(status)) {
                userStatus = s;
                break;
            }
        }
        if (userStatus == null) throw new InternalExpressionException("Invalid status");
        bot.getApi().updateStatus(userStatus);
    }
}
