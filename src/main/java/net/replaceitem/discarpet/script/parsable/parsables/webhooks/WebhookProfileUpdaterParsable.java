package net.replaceitem.discarpet.script.parsable.parsables.webhooks;

import net.dv8tion.jda.api.managers.WebhookManager;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.parsables.FileParsable;
import org.jetbrains.annotations.Nullable;

@ParsableClass(name = "webhook_profile_updater")
public class WebhookProfileUpdaterParsable {
    
    @OptionalField @Nullable
    String name;
    @OptionalField @Nullable
    FileParsable.AbstractFile avatar;
    @OptionalField @Nullable
    String reason;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void apply(WebhookManager webhookManager) {
        if(name != null) webhookManager.setName(name);
        if(avatar != null) webhookManager.setAvatar(avatar.asIcon());
        webhookManager.reason(reason);
    }
}
