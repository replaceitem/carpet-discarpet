package net.replaceitem.discarpet.script.values;

import carpet.script.exception.ThrowStatement;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.entities.WebhookClient;
import net.dv8tion.jda.api.requests.RestAction;
import net.replaceitem.discarpet.script.exception.DiscordThrowables;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.common.Deletable;
import net.replaceitem.discarpet.script.values.common.MessageableValue;
import net.replaceitem.discarpet.script.values.common.Renamable;

import java.util.Optional;

public class WebhookValue extends MessageableValue<WebhookClient<Message>> implements Deletable, Renamable {
    public WebhookValue(WebhookClient<Message> webhook) {
        super(webhook);
    }

    @Override
    protected String getDiscordTypeString() {
        return "webhook";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "id" -> StringValue.of(delegate.getId());
            case "channel" -> delegate instanceof Webhook webhook ? ChannelValue.of(webhook.getChannel()) : Value.NULL;
            case "type" -> delegate instanceof Webhook webhook ? ValueUtil.ofEnum(webhook.getType()) : Value.NULL;
            case "token" -> StringValue.of(delegate.getToken());
            case "url" -> delegate instanceof Webhook webhook ? StringValue.of(webhook.getUrl()) : Value.NULL;
            default -> super.getProperty(property);
        };
    }

    @Override
    public RestAction<?> delete(String reason) {
        if(!(delegate instanceof Webhook webhook)) throw new ThrowStatement("Cannot delete webhook not managed by this bot", DiscordThrowables.DISCORD_EXCEPTION);
        return webhook.delete().reason(reason);
    }

    @Override
    public RestAction<?> rename(String name) {
        if(!(delegate instanceof Webhook webhook)) throw new ThrowStatement("Cannot rename webhook not managed by this bot", DiscordThrowables.DISCORD_EXCEPTION);
        return webhook.getManager().setName(name);
    }

    @Override
    public Optional<MessageConsumer> getMessageConsumer() {
        return Optional.of(delegate::sendMessage);
    }
}
