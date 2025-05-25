package net.replaceitem.discarpet.script.schema.schemas;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.managers.Presence;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import org.jetbrains.annotations.Nullable;

@SchemaClass(name = "presence_updater")
public class PresenceUpdaterSchema {
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
