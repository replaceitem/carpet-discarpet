package Discarpet.script.parsable.parsables;

import Discarpet.script.parsable.Applicable;
import Discarpet.script.parsable.Optional;
import Discarpet.script.parsable.ParsableClass;
import carpet.script.exception.InternalExpressionException;
import org.javacord.api.entity.webhook.WebhookBuilder;

import java.net.MalformedURLException;
import java.net.URL;

@ParsableClass(name = "webhook_profile")
public class WebhookProfileParsable implements Applicable<WebhookBuilder> {
    
    String name;
    @Optional String avatar;
    @Optional String reason;

    @Override
    public void apply(WebhookBuilder webhookBuilder) {
        webhookBuilder.setName(name);
        try {
            webhookBuilder.setAvatar(new URL(avatar));
        } catch (MalformedURLException e) {
            throw new InternalExpressionException("Invalid avatar URL: " + e);
        }
        webhookBuilder.setAuditLogReason(reason);
    }
}
