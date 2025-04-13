package net.replaceitem.discarpet.script.values;

import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.entities.Webhook;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.common.Deletable;
import net.replaceitem.discarpet.script.values.common.MessageableValue;
import net.replaceitem.discarpet.script.values.common.Renamable;

import java.util.Optional;

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
            case "id" -> StringValue.of(delegate.getId());
            case "channel" -> ChannelValue.of(delegate.getChannel());
            // TODO migrate types?
            case "type" -> StringValue.of(delegate.getType().toString());
            case "token" -> StringValue.of(delegate.getToken());
            case "url" -> StringValue.of(delegate.getUrl());
            default -> super.getProperty(property);
        };
    }

    @Override
    public void delete(String reason) {
        ValueUtil.awaitRest(delegate.delete().reason(reason), "Failed to delete " + this.getTypeString());
    }

    @Override
    public void rename(String name) {
        ValueUtil.awaitRest(delegate.getManager().setName(name), "Could not rename webhook");
    }

    @Override
    public Optional<MessageConsumer> getMessageable() {
        return Optional.of(delegate::sendMessage);
    }
}
