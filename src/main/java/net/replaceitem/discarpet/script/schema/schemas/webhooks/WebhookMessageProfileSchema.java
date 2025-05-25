package net.replaceitem.discarpet.script.schema.schemas.webhooks;

import net.dv8tion.jda.api.requests.restaction.WebhookMessageCreateAction;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import org.jetbrains.annotations.Nullable;

@SchemaClass(name = "webhook_message_profile")
public class WebhookMessageProfileSchema {
    
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
