package Discarpet.script.values;

import carpet.script.value.ListValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.minecraft.nbt.NbtElement;
import org.javacord.api.entity.user.User;

import java.util.List;

public class UserValue extends Value {

    private final User user;

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

    public Value setProperty(String property, List<Value> values) {
        switch (property) {
            case "nickname":
                if(values.size() < 2) return Value.NULL;
                if(!(values.get(0) instanceof ServerValue)) return Value.NULL;
                user.updateNickname(((ServerValue) values.get(0)).getServer(), values.get(1).getString());
                return Value.TRUE;
            default:
                return Value.NULL;
        }
    }

    @Override
    public Value in(Value value1) {
        if(value1 instanceof ListValue) {
            if(value1.length() < 2) {
                return Value.NULL;
            } else {
                List<Value> args = ((ListValue) value1).getItems();
                System.out.println("args = " + args);
                String property = args.get(0).getString();
                args = args.subList(1,args.size());
                System.out.println("args = " + args);
                return setProperty(property,args);
            }
        }
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
    public NbtElement toTag(boolean b) {
        return null;
    }

	public User getUser() {
		return user;
	}
}
