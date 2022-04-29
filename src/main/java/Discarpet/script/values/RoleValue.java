package Discarpet.script.values;

import Discarpet.script.util.ValueUtil;
import Discarpet.script.values.common.Deletable;
import Discarpet.script.values.common.DiscordValue;
import carpet.script.value.BooleanValue;
import carpet.script.value.ListValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.entity.permission.Role;

public class RoleValue extends DiscordValue<Role> implements Deletable {
    public RoleValue(Role role) {
        super("role",role);
    }
    
    public Value getProperty(String property) {
        return switch (property) {
            case "name" -> StringValue.of(value.getName());
            case "id" -> StringValue.of(value.getIdAsString());
            case "mention_tag" -> StringValue.of(value.getMentionTag());
            case "color" -> ValueUtil.colorToValue(ValueUtil.unpackOptional(value.getColor()));
            case "position" -> NumericValue.of(value.getPosition());
            case "server" -> ServerValue.of(value.getServer());
            case "users" -> ListValue.wrap(value.getUsers().stream().map(UserValue::new));
            case "displayed_separately" -> BooleanValue.of(value.isDisplayedSeparately());
            case "is_everyone_value" -> BooleanValue.of(value.isEveryoneRole());
            case "managed" -> BooleanValue.of(value.isManaged());
            case "mentionable" -> BooleanValue.of(value.isMentionable());
            default -> Value.NULL;
        };
    }

    @Override
    public boolean delete() {
        return ValueUtil.awaitFutureBoolean(value.delete(), "Failed to delete " + this.getTypeString());
    }
}
