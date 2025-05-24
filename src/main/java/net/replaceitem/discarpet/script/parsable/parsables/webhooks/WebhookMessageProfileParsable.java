package net.replaceitem.discarpet.script.parsable.parsables.webhooks;

import net.dv8tion.jda.api.requests.restaction.WebhookMessageCreateAction;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import org.jetbrains.annotations.Nullable;

@ParsableClass(name = "webhook_message_profile")
public class WebhookMessageProfileParsable {
    
    @OptionalField @Nullable
    String name;
    @OptionalField @Nullable
    String avatar;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void apply(WebhookMessageCreateAction<?> webhookMessageCreateAction) {
        if(name != null) webhookMessageCreateAction.setUsername(name);
        if(avatar != null) webhookMessageCreateAction.setAvatarUrl(avatar);
    }
}
