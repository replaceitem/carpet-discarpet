package net.replaceitem.discarpet.script.parsable.parsables;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.managers.Presence;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import org.jetbrains.annotations.Nullable;

@ParsableClass(name = "presence_updater")
public class PresenceUpdaterParsable {
    @OptionalField @Nullable
    OnlineStatus status;
    @OptionalField
    Activity.ActivityType activity_type = Activity.ActivityType.PLAYING;
    @OptionalField @Nullable
    String activity_name;
    @OptionalField @Nullable
    String activity_url;
    
    public @Nullable Activity getActivity() {
        if(activity_name == null) return null;
        return Activity.of(activity_type, activity_name, activity_url);
    }
    
    public void apply(Presence presence) {
        Activity activity = getActivity();
        presence.setPresence(status, activity);
    }
}
