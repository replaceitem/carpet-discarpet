package Discarpet.script.values;

import Discarpet.script.util.ValueUtil;
import carpet.script.value.BooleanValue;
import carpet.script.value.ListValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.minecraft.nbt.NbtElement;
import org.javacord.api.entity.permission.Role;

import java.util.Optional;

public class RoleValue extends Value {

    private final Role role;

    public RoleValue(Role role) {
        this.role = role;
    }

    public static Value of(Role role) {
        if(role == null) return Value.NULL;
        return new RoleValue(role);
    }
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static Value of(Optional<? extends Role> optionalRole){
        return of(ValueUtil.unpackOptional(optionalRole));
    }

    public Role getRole() {
        return role;
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "name" -> StringValue.of(role.getName());
            case "id" -> StringValue.of(role.getIdAsString());
            case "mention_tag" -> StringValue.of(role.getMentionTag());
            case "color" -> ValueUtil.colorToValue(ValueUtil.unpackOptional(role.getColor()));
            case "position" -> NumericValue.of(role.getPosition());
            case "server" -> ServerValue.of(role.getServer());
            case "users" -> ListValue.wrap(role.getUsers().stream().map(UserValue::new));
            case "displayed_separately" -> BooleanValue.of(role.isDisplayedSeparately());
            case "is_everyone_role" -> BooleanValue.of(role.isEveryoneRole());
            case "managed" -> BooleanValue.of(role.isManaged());
            case "mentionable" -> BooleanValue.of(role.isMentionable());
            default -> Value.NULL;
        };
    }


    @Override
    public Value in(Value value1) {
        return getProperty(value1.getString());
    }

    @Override
    public String getTypeString() {
        return "dc_role";
    }

    @Override
    public String getString() {
        return role.toString();
    }

    @Override
    public boolean getBoolean() {
        return true;
    }

    @Override
    public NbtElement toTag(boolean b) {
        return null;
    }
}
