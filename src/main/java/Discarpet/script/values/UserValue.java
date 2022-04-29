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
            case "name" -> StringValue.of(value.getName());
            case "mention_tag" -> StringValue.of(value.getMentionTag());
            case "discriminated_name" -> StringValue.of(value.getDiscriminatedName());
            case "id" -> StringValue.of(value.getIdAsString());
            case "avatar" -> StringValue.of(value.getAvatar().getUrl().toString());
            case "is_bot" -> BooleanValue.of(value.isBot());
            case "is_self" -> BooleanValue.of(value.isYourself());
            case "private_channel" -> ChannelValue.of(ValueUtil.awaitFuture(value.openPrivateChannel(),"Error opening private channel with user"));
            default -> Value.NULL;
        };
    }
}
