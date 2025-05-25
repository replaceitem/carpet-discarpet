package net.replaceitem.discarpet.script.schema.schemas.webhooks;

import net.dv8tion.jda.api.managers.WebhookManager;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.schemas.FileSchema;
import org.jetbrains.annotations.Nullable;

@SchemaClass(name = "webhook_profile_updater")
public class WebhookProfileUpdaterSchema {
    
    @OptionalField @Nullable
    String name;
    @OptionalField @Nullable
    FileSchema.AbstractFile avatar;
    @OptionalField @Nullable
    String reason;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void apply(WebhookManager webhookManager) {
        if(name != null) webhookManager.setName(name);
        if(avatar != null) webhookManager.setAvatar(avatar.asIcon());
        webhookManager.reason(reason);
    }
}
