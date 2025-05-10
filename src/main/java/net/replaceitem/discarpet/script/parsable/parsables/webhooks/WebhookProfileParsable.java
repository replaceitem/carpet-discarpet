package net.replaceitem.discarpet.script.parsable.parsables.webhooks;

import carpet.script.value.Value;
import net.dv8tion.jda.api.requests.restaction.WebhookAction;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.util.FileUtil;
import org.jetbrains.annotations.Nullable;

@ParsableClass(name = "webhook_profile")
public class WebhookProfileParsable {
    
    String name;
    @OptionalField @Nullable
    Value avatar;
    @OptionalField @Nullable
    String reason;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void apply(WebhookAction webhookAction) {
        webhookAction.setName(name);
        if(avatar != null && !avatar.isNull()) webhookAction.setAvatar(FileUtil.iconFromValue(avatar));
        webhookAction.reason(reason);
    }
}
