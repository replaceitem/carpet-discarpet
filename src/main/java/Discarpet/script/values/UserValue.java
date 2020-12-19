package Discarpet.script.values;

import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.minecraft.nbt.Tag;
import org.javacord.api.entity.user.User;

public class UserValue extends Value {

    public User user;

    public UserValue(User user) {
        this.user = user;
    }

    public Value getProperty(String property) {
        switch (property) {
            case "name":
                return StringValue.of(user.getName());
            case "mention_tag":
                return StringValue.of(user.getMentionTag());
            case "discriminated_name":
                return StringValue.of(user.getDiscriminatedName());
            case "id":
                return StringValue.of(user.getIdAsString());
            case "avatar":
                return StringValue.of(user.getAvatar().getUrl().toString());
            case "is_bot":
                return new NumericValue(user.isBot());
            case "is_self":
                return new NumericValue(user.isYourself());
            default:
                return Value.NULL;
        }
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
        return user.getDiscriminatedName();
    }

    @Override
    public boolean getBoolean() {
        return false;
    }

    @Override
    public Tag toTag(boolean b) {
        return null;
    }
}
