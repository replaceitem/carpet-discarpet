package net.replaceitem.discarpet.script.values;

import net.dv8tion.jda.api.entities.User;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.common.MessageableValue;
import carpet.script.value.BooleanValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;

import java.util.Optional;

public class UserValue extends MessageableValue<User> {
    public UserValue(User user) {
        super(user);
    }

    @Override
    protected String getDiscordTypeString() {
        return "user";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "name" -> StringValue.of(delegate.getName());
            case "mention_tag" -> StringValue.of(delegate.getAsMention());
            case "id" -> StringValue.of(delegate.getId());
            case "avatar" -> StringValue.of(delegate.getAvatarUrl());
            case "is_bot" -> BooleanValue.of(delegate.isBot());
            case "is_self" -> BooleanValue.of(delegate.getIdLong() == delegate.getJDA().getSelfUser().getIdLong());
            case "private_channel" -> ChannelValue.of(ValueUtil.awaitRest(delegate.openPrivateChannel(),"Error opening private channel with user"));
            default -> super.getProperty(property);
        };
    }

    @Override
    public Optional<MessageConsumer> getMessageConsumer() {
        return Optional.of((data) -> delegate.openPrivateChannel().flatMap(privateChannel -> privateChannel.sendMessage(data)));
    }
}
