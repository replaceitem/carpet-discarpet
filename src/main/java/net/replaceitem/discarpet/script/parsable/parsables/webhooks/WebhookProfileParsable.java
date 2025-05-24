package net.replaceitem.discarpet.script.parsable.parsables.webhooks;

import net.dv8tion.jda.api.requests.restaction.WebhookAction;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.parsables.FileParsable;
import org.jetbrains.annotations.Nullable;

@ParsableClass(name = "webhook_profile")
public class WebhookProfileParsable {
    
    String name;
    @OptionalField @Nullable
    FileParsable.AbstractFile avatar;
    @OptionalField @Nullable
    String reason;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void apply(WebhookAction webhookAction) {
        webhookAction.setName(name);
        if(avatar != null) webhookAction.setAvatar(avatar.asIcon());
        webhookAction.reason(reason);
    }
}
