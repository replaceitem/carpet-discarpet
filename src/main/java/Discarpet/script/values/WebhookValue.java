package Discarpet.script.values;

import Discarpet.script.util.ValueUtil;
import Discarpet.script.values.common.Deletable;
import Discarpet.script.values.common.MessageableValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.entity.webhook.IncomingWebhook;
import org.javacord.api.entity.webhook.Webhook;

public class WebhookValue extends MessageableValue<Webhook> implements Deletable {
    public WebhookValue(Webhook webhook) {
        super("webhook",webhook);
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "id" -> StringValue.of(value.getIdAsString());
            case "channel" -> ChannelValue.of(value.getChannel());
            case "type" -> StringValue.of(value.getType().toString());
            case "token" -> StringValue.of(value instanceof IncomingWebhook incomingWebhook ? incomingWebhook.getToken() : null);
            case "url" -> StringValue.of(value instanceof IncomingWebhook incomingWebhook ? incomingWebhook.getUrl().getPath() : null);
            default -> Value.NULL;
        };
    }

    @Override
    public boolean delete() {
        return ValueUtil.awaitFutureBoolean(value.delete(), "Failed to delete " + this.getTypeString());
    }
}
