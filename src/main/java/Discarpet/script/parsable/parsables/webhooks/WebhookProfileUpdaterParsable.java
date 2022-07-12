package Discarpet.script.parsable.parsables.webhooks;

import Discarpet.Discarpet;
import Discarpet.script.parsable.Applicable;
import Discarpet.script.parsable.Optional;
import Discarpet.script.parsable.ParsableClass;
import Discarpet.script.util.ScarpetGraphicsDependency;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.Value;
import org.javacord.api.entity.webhook.WebhookUpdater;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

@ParsableClass(name = "webhook_profile_updater")
public class WebhookProfileUpdaterParsable implements Applicable<WebhookUpdater> {
    
    @Optional String name;
    @Optional Value avatar;
    @Optional String reason;

    @Override
    public void apply(WebhookUpdater webhookUpdater) {
        webhookUpdater.setName(name);
        setAvatar(webhookUpdater);
        webhookUpdater.setAuditLogReason(reason);
    }

    private void setAvatar(WebhookUpdater webhookUpdater) {
        if(Discarpet.isScarpetGraphicsInstalled() && ScarpetGraphicsDependency.isPixelAccessible(avatar)) {
            BufferedImage bufferedImage = ScarpetGraphicsDependency.getImageFromValue(avatar);
            webhookUpdater.setAvatar(bufferedImage);
            return;
        }
        String iconString = avatar.getString();
        File file = new File(iconString);
        if(file.exists()) {
            webhookUpdater.setAvatar(file);
            return;
        }
        try {
            webhookUpdater.setAvatar(new URL(iconString));
        } catch (MalformedURLException e) {
            throw new InternalExpressionException("Invalid URL/File provided for 'avatar'");
        }
    }
}
