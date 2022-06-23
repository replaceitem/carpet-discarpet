package Discarpet.script.values;

import Discarpet.script.util.ValueUtil;
import Discarpet.script.values.common.MessageableValue;
import carpet.script.value.BooleanValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.entity.user.User;

public class UserValue extends MessageableValue<User> {
    public UserValue(User user) {
        super("user",user);
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "name" -> StringValue.of(delegate.getName());
            case "mention_tag" -> StringValue.of(delegate.getMentionTag());
            case "discriminated_name" -> StringValue.of(delegate.getDiscriminatedName());
            case "id" -> StringValue.of(delegate.getIdAsString());
            case "avatar" -> StringValue.of(delegate.getAvatar().getUrl().toString());
            case "is_bot" -> BooleanValue.of(delegate.isBot());
            case "is_self" -> BooleanValue.of(delegate.isYourself());
            case "private_channel" -> ChannelValue.of(ValueUtil.awaitFuture(delegate.openPrivateChannel(),"Error opening private channel with user"));
            default -> Value.NULL;
        };
    }
}
