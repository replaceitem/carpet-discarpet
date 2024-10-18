package net.replaceitem.discarpet.script.values;

import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.common.Deletable;
import net.replaceitem.discarpet.script.values.common.MessageableValue;
import net.replaceitem.discarpet.script.values.common.Renamable;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.entity.webhook.IncomingWebhook;
import org.javacord.api.entity.webhook.Webhook;

public class WebhookValue extends MessageableValue<Webhook> implements Deletable, Renamable {
    public WebhookValue(Webhook webhook) {
        super(webhook);
    }

    @Override
    protected String getDiscordTypeString() {
        return "webhook";
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
    public void delete(String reason) {
        ValueUtil.awaitFuture(delegate.delete(reason), "Failed to delete " + this.getTypeString());
    }

    @Override
    public void rename(String name) {
        ValueUtil.awaitFuture(delegate.updateName(name), "Could not rename webhook");
    }
}
