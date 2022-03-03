package Discarpet.script.values;

import Discarpet.script.util.ValueUtil;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.minecraft.nbt.NbtElement;
import org.javacord.api.entity.message.Messageable;
import org.javacord.api.entity.webhook.IncomingWebhook;
import org.javacord.api.entity.webhook.Webhook;

import java.util.Optional;

public class WebhookValue extends Value implements MessageableValue {

    private final Webhook webhook;

    public WebhookValue(Webhook webhook) {
        this.webhook = webhook;
    }

    public static Value of(Webhook webhook) {
        if(webhook == null) return Value.NULL;
        return new WebhookValue(webhook);
    }
    
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static Value of(Optional<? extends Webhook> optionalWebhook) {
        return of(ValueUtil.unpackOptional(optionalWebhook));
    }

    public Webhook getWebhook() {
        return webhook;
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "id" -> StringValue.of(webhook.getIdAsString());
            case "channel" -> ChannelValue.of(webhook.getChannel());
            case "type" -> StringValue.of(webhook.getType().toString());
            case "token" -> StringValue.of(webhook instanceof IncomingWebhook incomingWebhook ? incomingWebhook.getToken() : null);
            case "url" -> StringValue.of(webhook instanceof IncomingWebhook incomingWebhook ? incomingWebhook.getUrl().getPath() : null);
            default -> Value.NULL;
        };
    }


    @Override
    public Messageable getMessageable() {
        return webhook instanceof Messageable messageableWebhook ? messageableWebhook : null;
    }

    @Override
    public Value in(Value value1) {
        return getProperty(value1.getString());
    }

    @Override
    public String getTypeString() {
        return "dc_webhook";
    }

    @Override
    public String getString() {
        return webhook.toString();
    }

    @Override
    public boolean getBoolean() {
        return true;
    }

    @Override
    public NbtElement toTag(boolean b) {
        return null;
    }
}
