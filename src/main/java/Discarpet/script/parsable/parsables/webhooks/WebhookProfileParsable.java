package Discarpet.script.parsable.parsables.webhooks;

import Discarpet.Discarpet;
import Discarpet.script.parsable.Applicable;
import Discarpet.script.parsable.Optional;
import Discarpet.script.parsable.ParsableClass;
import Discarpet.script.util.ScarpetGraphicsDependency;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.Value;
import org.javacord.api.entity.webhook.WebhookBuilder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

@ParsableClass(name = "webhook_profile")
public class WebhookProfileParsable implements Applicable<WebhookBuilder> {
    
    String name;
    @Optional Value avatar;
    @Optional String reason;

    @Override
    public void apply(WebhookBuilder webhookBuilder) {
        webhookBuilder.setName(name);
        if(avatar != null) setAvatar(webhookBuilder);
        webhookBuilder.setAuditLogReason(reason);
    }

    private void setAvatar(WebhookBuilder webhookBuilder) {
        if(Discarpet.isScarpetGraphicsInstalled() && ScarpetGraphicsDependency.isPixelAccessible(avatar)) {
            BufferedImage bufferedImage = ScarpetGraphicsDependency.getImageFromValue(avatar);
            webhookBuilder.setAvatar(bufferedImage);
            return;
        }
        String iconString = avatar.getString();
        File file = new File(iconString);
        if(file.exists()) {
            webhookBuilder.setAvatar(file);
            return;
        }
        try {
            webhookBuilder.setAvatar(new URL(iconString));
        } catch (MalformedURLException e) {
            throw new InternalExpressionException("Invalid URL/File provided for 'avatar'");
        }
    }
}
