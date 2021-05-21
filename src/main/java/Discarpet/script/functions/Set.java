package Discarpet.script.functions;

import Discarpet.Bot;
import carpet.script.Context;
import carpet.script.Expression;
import carpet.script.annotation.ScarpetFunction;
import carpet.script.value.Value;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.user.UserStatus;

import static Discarpet.Discarpet.*;

public class Set {
	
	@ScarpetFunction
	public boolean dc_set_channel_topic(ServerTextChannel channel, String str) {
		channel.updateTopic(str);
		return true;
	}
	
	@ScarpetFunction
	public boolean dc_set_activity(Context ctx, String activity, String text) {
		Bot bot = getBotInContext(ctx);
		if (bot == null) scarpetNoBotException("dc_set_activity");
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
		Bot bot = getBotInContext(ctx);
        if (bot == null) scarpetNoBotException("dc_set_status");
        
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
