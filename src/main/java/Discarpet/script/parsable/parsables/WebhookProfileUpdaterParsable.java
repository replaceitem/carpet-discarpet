package Discarpet.script.parsable.parsables;

import Discarpet.script.parsable.Applicable;
import Discarpet.script.parsable.Optional;
import Discarpet.script.parsable.ParsableClass;
import carpet.script.exception.InternalExpressionException;
import org.javacord.api.entity.webhook.WebhookUpdater;

import java.net.MalformedURLException;
import java.net.URL;

@ParsableClass(name = "webhook_profile_updater")
public class WebhookProfileUpdaterParsable implements Applicable<WebhookUpdater> {
    
    @Optional String name;
    @Optional String avatar;
    @Optional String reason;

    @Override
    public void apply(WebhookUpdater webhookUpdater) {
        webhookUpdater.setName(name);
        try {
            webhookUpdater.setAvatar(new URL(avatar));
        } catch (MalformedURLException e) {
            throw new InternalExpressionException("Invalid avatar URL: " + e);
        }
        webhookUpdater.setAuditLogReason(reason);
    }
}
