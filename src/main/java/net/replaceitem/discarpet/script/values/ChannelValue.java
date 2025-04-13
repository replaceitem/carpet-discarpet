package net.replaceitem.discarpet.script.values;

import carpet.script.value.BooleanValue;
import carpet.script.value.ListValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.attribute.IAgeRestrictedChannel;
import net.dv8tion.jda.api.entities.channel.attribute.IWebhookContainer;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.requests.ErrorResponse;
import net.dv8tion.jda.internal.entities.channel.mixin.attribute.ITopicChannelMixin;
import net.replaceitem.discarpet.script.exception.DiscordThrowables;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.common.MessageableValue;
import net.replaceitem.discarpet.script.values.common.Renamable;

import java.util.Optional;

public class ChannelValue extends MessageableValue<Channel> implements Renamable {
    public ChannelValue(Channel channel) {
        super(channel);
    }

    @Override
    protected String getDiscordTypeString() {
        return "channel";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "name" -> StringValue.of(delegate.getName().isEmpty() ? null : delegate.getName());
            // TODO convert types?
            case "type" -> StringValue.of(delegate.getType().toString());
            case "topic" -> StringValue.of(delegate instanceof ITopicChannelMixin<?> topicChannelMixin ? topicChannelMixin.getTopic() : null);
            case "id" -> StringValue.of(delegate.getId());
            case "mention_tag" -> StringValue.of(delegate.getAsMention());
            case "server" -> ServerValue.of(delegate instanceof GuildChannel guildChannel ? guildChannel.getGuild() : null);
            case "webhooks" -> delegate instanceof IWebhookContainer webhookContainer ?
                    ListValue.wrap(
                            ValueUtil.awaitRest(webhookContainer.retrieveWebhooks(), "Error getting webhooks from channel")
                                    .stream().map(WebhookValue::of)) :
                    Value.NULL;
            case "nsfw" -> BooleanValue.of(delegate instanceof IAgeRestrictedChannel ageRestrictedChannel && ageRestrictedChannel.isNSFW());
            default -> super.getProperty(property);
        };
    }

    @Override
    public void rename(String name) {
        if(!(delegate instanceof GuildChannel guildChannel)) throw DiscordThrowables.genericCode(ErrorResponse.INVALID_CHANNEL_TYPE);
        ValueUtil.awaitRest(guildChannel.getManager().setName(name), "Could not rename channel");
    }

    @Override
    public Optional<MessageConsumer> getMessageable() {
        return delegate instanceof MessageChannel messageable ? Optional.of(messageable::sendMessage) : Optional.empty();
    }
}
