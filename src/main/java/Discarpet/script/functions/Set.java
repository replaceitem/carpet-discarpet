package Discarpet.script.functions;

import Discarpet.script.values.ChannelValue;
import Discarpet.Bot;
import carpet.script.Expression;
import carpet.script.value.Value;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.user.UserStatus;

import static Discarpet.Discarpet.*;
import static carpet.script.LazyValue.*;

public class Set {
    public static void apply(Expression expr) {

        expr.addLazyFunction("dc_set_channel_topic", 2, (c, t, lv) -> {
            Value channelValue = lv.get(0).evalValue(c);
            if(!(channelValue instanceof ChannelValue)) scarpetException("dc_set_channel_topic","channel",0);
            ((ChannelValue) channelValue).channel.updateTopic(lv.get(1).evalValue(c).getString());
            return TRUE;
        });

        expr.addLazyFunction("dc_set_activity", 2, (c, t, lv) -> {
            Bot b = getBotInContext(c);
            if(b==null) scarpetNoBotException("dc_set_activity");

            String actitvity = lv.get(0).evalValue(c).getString();
            ActivityType activityType = null;
            for(ActivityType type : ActivityType.values()) {
                if(type.toString().equalsIgnoreCase(actitvity)) {
                    activityType = type;
                    break;
                }
            }

            if(activityType == null) return FALSE;
            String text = lv.get(1).evalValue(c).getString();
            b.api.updateActivity(activityType,text);
            return TRUE;
        });

        expr.addLazyFunction("dc_set_status", 1, (c, t, lv) -> {
            Bot b = getBotInContext(c);
            if(b==null) scarpetNoBotException("dc_set_status");

            String status = lv.get(0).evalValue(c).getString();

            UserStatus userStatus = null;

            for (UserStatus s : UserStatus.values()) {
                if (s.getStatusString().equalsIgnoreCase(status)) {
                    userStatus = s;
                    break;
                }
            }
            if(userStatus == null) return FALSE;
            b.api.updateStatus(userStatus);
            return TRUE;
        });
    }
}
