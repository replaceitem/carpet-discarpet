package Discarpet.script.values;

import Discarpet.script.util.ValueUtil;
import Discarpet.script.values.common.Deletable;
import Discarpet.script.values.common.MessageableValue;
import Discarpet.script.values.common.Renamable;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.entity.webhook.IncomingWebhook;
import org.javacord.api.entity.webhook.Webhook;

public class WebhookValue extends MessageableValue<Webhook> implements Deletable, Renamable {
    public WebhookValue(Webhook webhook) {
        super("webhook",webhook);
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "id" -> StringValue.of(delegate.getIdAsString());
            case "channel" -> ChannelValue.of(delegate.getChannel());
            case "type" -> StringValue.of(delegate.getType().toString());
            case "token" -> StringValue.of(delegate instanceof IncomingWebhook incomingWebhook ? incomingWebhook.getToken() : null);
            case "url" -> StringValue.of(delegate instanceof IncomingWebhook incomingWebhook ? incomingWebhook.getUrl().getPath() : null);
            default -> super.getProperty(property);
        };
    }

    @Override
    public boolean delete(String reason) {
        return ValueUtil.awaitFutureBoolean(delegate.delete(reason), "Failed to delete " + this.getTypeString());
    }

    @Override
    public boolean rename(String name) {
        return ValueUtil.awaitFutureBoolean(delegate.updateName(name), "Could not rename webhook");
    }
}
