package net.replaceitem.discarpet.script.parsable.parsables.webhooks;

import net.replaceitem.discarpet.script.parsable.Applicable;
import net.replaceitem.discarpet.script.parsable.Optional;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import carpet.script.exception.InternalExpressionException;
import org.javacord.api.entity.message.WebhookMessageBuilder;

import java.net.MalformedURLException;
import java.net.URL;

@ParsableClass(name = "webhook_message_profile")
public class WebhookMessageProfileParsable implements Applicable<WebhookMessageBuilder> {
    
    @Optional String name;
    @Optional String avatar;

    @Override
    public void apply(WebhookMessageBuilder webhookMessageBuilder) {
        webhookMessageBuilder.setDisplayName(name);
        if(avatar != null) {
            try {
                webhookMessageBuilder.setDisplayAvatar(new URL(avatar));
            } catch (MalformedURLException e) {
                throw new InternalExpressionException("Invalid avatar URL: " + e);
            }
        }
    }
}
