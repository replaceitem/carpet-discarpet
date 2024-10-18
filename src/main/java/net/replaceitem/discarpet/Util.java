package net.replaceitem.discarpet;

import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.user.UserStatus;

import java.util.Optional;

public class Util {
    public static Optional<ActivityType> activityTypeByName(String name) {
        for(ActivityType type : ActivityType.values()) {
            if(type.toString().equalsIgnoreCase(name)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }
    
    public static Optional<UserStatus> statusByName(String name) {
        for (UserStatus status : UserStatus.values()) {
            if (status.getStatusString().equalsIgnoreCase(name)) {
                return Optional.of(status);
            }
        }
        return Optional.empty();
    }
}
