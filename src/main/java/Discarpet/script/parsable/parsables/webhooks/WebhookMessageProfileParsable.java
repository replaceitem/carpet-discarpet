package Discarpet.script.parsable.parsables.webhooks;

import Discarpet.script.parsable.Applicable;
import Discarpet.script.parsable.Optional;
import Discarpet.script.parsable.ParsableClass;
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
