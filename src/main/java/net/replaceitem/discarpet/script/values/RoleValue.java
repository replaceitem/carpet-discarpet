package net.replaceitem.discarpet.script.values;

import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.common.Deletable;
import net.replaceitem.discarpet.script.values.common.DiscordValue;
import net.replaceitem.discarpet.script.values.common.Renamable;
import carpet.script.value.BooleanValue;
import carpet.script.value.ListValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.entity.permission.Role;

public class RoleValue extends DiscordValue<Role> implements Deletable, Renamable {
    public RoleValue(Role role) {
        super(role);
    }

    @Override
    protected String getDiscordTypeString() {
        return "role";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "name" -> StringValue.of(delegate.getName());
            case "id" -> StringValue.of(delegate.getIdAsString());
            case "mention_tag" -> StringValue.of(delegate.getMentionTag());
            case "color" -> ValueUtil.colorToValue(ValueUtil.unpackOptional(delegate.getColor()));
            case "position" -> NumericValue.of(delegate.getPosition());
            case "server" -> ServerValue.of(delegate.getServer());
            case "users" -> ListValue.wrap(delegate.getUsers().stream().map(UserValue::new));
            case "displayed_separately" -> BooleanValue.of(delegate.isDisplayedSeparately());
            case "is_everyone_value" -> BooleanValue.of(delegate.isEveryoneRole());
            case "managed" -> BooleanValue.of(delegate.isManaged());
            case "mentionable" -> BooleanValue.of(delegate.isMentionable());
            default -> super.getProperty(property);
        };
    }

    @Override
    public void delete(String reason) {
        ValueUtil.awaitFuture(delegate.delete(), "Failed to delete " + this.getTypeString());
    }

    @Override
    public void rename(String name) {
        ValueUtil.awaitFuture(delegate.updateName(name), "Could not rename role");
    }
}
