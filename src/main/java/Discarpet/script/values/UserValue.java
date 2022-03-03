package Discarpet.script.values;

import Discarpet.script.util.ValueUtil;
import carpet.script.value.BooleanValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.minecraft.nbt.NbtElement;
import org.javacord.api.entity.message.Messageable;
import org.javacord.api.entity.user.User;

import java.util.Optional;

public class UserValue extends Value implements MessageableValue {

    private final User user;

    public UserValue(User user) {
        this.user = user;
    }

    public static Value of(User user) {
        if(user == null) return Value.NULL;
        return new UserValue(user);
    }
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static Value of(Optional<User> optionalUser){
        return of(ValueUtil.unpackOptional(optionalUser));
    }

    @Override
    public Messageable getMessageable() {
        return user;
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "name" -> StringValue.of(user.getName());
            case "mention_tag" -> StringValue.of(user.getMentionTag());
            case "discriminated_name" -> StringValue.of(user.getDiscriminatedName());
            case "id" -> StringValue.of(user.getIdAsString());
            case "avatar" -> StringValue.of(user.getAvatar().getUrl().toString());
            case "is_bot" -> BooleanValue.of(user.isBot());
            case "is_self" -> BooleanValue.of(user.isYourself());
            case "private_channel" -> ChannelValue.of(ValueUtil.awaitFuture(user.openPrivateChannel(),"Error opening private channel with user"));
            default -> Value.NULL;
        };
    }

    @Override
    public Value in(Value value1) {
        return getProperty(value1.getString());
    }

    @Override
    public String getTypeString() {
        return "dc_user";
    }

    @Override
    public String getString() {
        return user.toString();
    }

    @Override
    public boolean getBoolean() {
        return true;
    }

    @Override
    public NbtElement toTag(boolean b) {
        return null;
    }

	public User getUser() {
		return user;
	}
}
