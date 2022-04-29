package Discarpet.script.values;

import Discarpet.script.values.common.MessageableValue;
import carpet.script.value.ListValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.entity.Mentionable;
import org.javacord.api.entity.Nameable;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.channel.ServerTextChannel;

public class ChannelValue extends MessageableValue<Channel> {
    public ChannelValue(Channel channel) {
        super("channel",channel);
    }
    
    public Value getProperty(String property) {
        return switch (property) {
            case "name" -> StringValue.of(value instanceof Nameable nameableChannel ? nameableChannel.getName() : null);
            case "type" -> StringValue.of(value.getType().toString());
            case "topic" -> StringValue.of(value instanceof ServerTextChannel serverTextChannel ? serverTextChannel.getTopic() : null);
            case "id" -> StringValue.of(value.getIdAsString());
            case "mention_tag" -> StringValue.of(value instanceof Mentionable mentionableChannel ? mentionableChannel.getMentionTag() : null);
            case "server" -> new ServerValue(value instanceof ServerChannel serverChannel ? serverChannel.getServer() : null);
            case "webhooks" -> value instanceof ServerTextChannel serverTextChannel ? ListValue.wrap(serverTextChannel.getWebhooks().join().stream().map(WebhookValue::of)) : Value.NULL;
            default -> Value.NULL;
        };
    }
}
