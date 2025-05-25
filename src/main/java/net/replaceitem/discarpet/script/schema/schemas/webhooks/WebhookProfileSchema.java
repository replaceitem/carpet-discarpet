package net.replaceitem.discarpet.script.schema.schemas.webhooks;

import net.dv8tion.jda.api.requests.restaction.WebhookAction;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.schemas.FileSchema;
import org.jetbrains.annotations.Nullable;

@SchemaClass(name = "webhook_profile")
public class WebhookProfileSchema {
    
    String name;
    @OptionalField @Nullable
    FileSchema.AbstractFile avatar;
    @OptionalField @Nullable
    String reason;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void apply(WebhookAction webhookAction) {
        webhookAction.setName(name);
        if(avatar != null) webhookAction.setAvatar(avatar.asIcon());
        webhookAction.reason(reason);
    }
}
