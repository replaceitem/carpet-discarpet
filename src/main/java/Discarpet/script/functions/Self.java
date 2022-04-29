package Discarpet.script.functions;

import Discarpet.config.Bot;
import carpet.script.Context;
import carpet.script.annotation.ScarpetFunction;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.user.User;
import org.javacord.api.entity.user.UserStatus;

import static Discarpet.Discarpet.getBotInContext;

@SuppressWarnings("unused")
public class Self {
    @ScarpetFunction
    public User dc_get_bot_user(Context c) {
        Bot bot = getBotInContext(c,"dc_get_bot_user");
        return bot.getApi().getYourself();
    }


    @ScarpetFunction
    public boolean dc_set_activity(Context ctx, String activity, String text) {
        Bot bot = getBotInContext(ctx,"dc_set_activity");
        ActivityType activityType = null;
        for(ActivityType type : ActivityType.values()) {
            if(type.toString().equalsIgnoreCase(activity)) {
                activityType = type;
                break;
            }
        }
        if (activityType == null) return false;
        bot.api.updateActivity(activityType, text);
        return true;
    }

    @ScarpetFunction
    public boolean dc_set_status(Context ctx, String status) {
        Bot bot = getBotInContext(ctx,"dc_set_status");
        UserStatus userStatus = null;

        for (UserStatus s : UserStatus.values()) {
            if (s.getStatusString().equalsIgnoreCase(status)) {
                userStatus = s;
                break;
            }
        }
        if (userStatus == null) return false;
        bot.api.updateStatus(userStatus);
        return false;
    }
}
