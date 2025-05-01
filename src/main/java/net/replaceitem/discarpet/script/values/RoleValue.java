package net.replaceitem.discarpet.script.values;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.requests.RestAction;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.common.Deletable;
import net.replaceitem.discarpet.script.values.common.DiscordValue;
import net.replaceitem.discarpet.script.values.common.Renamable;
import carpet.script.value.BooleanValue;
import carpet.script.value.ListValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.jetbrains.annotations.Nullable;

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
            case "id" -> StringValue.of(delegate.getId());
            case "mention_tag" -> StringValue.of(delegate.getAsMention());
            case "color" -> ValueUtil.colorToValue(delegate.getColor());
            case "position" -> NumericValue.of(delegate.getPosition());
            case "server" -> ServerValue.of(delegate.getGuild());
            // TODO add ~members too
            case "users" -> ListValue.wrap(delegate.getGuild().getMembersWithRoles(delegate).stream().map(Member::getUser).map(UserValue::new));
            case "displayed_separately" -> BooleanValue.of(delegate.isHoisted());
            case "is_everyone_value" -> BooleanValue.of(delegate.getIdLong() == delegate.getGuild().getIdLong());
            case "managed" -> BooleanValue.of(delegate.isManaged());
            case "mentionable" -> BooleanValue.of(delegate.isMentionable());
            default -> super.getProperty(property);
        };
    }

    @Override
    public RestAction<?> delete(@Nullable String reason) {
        return delegate.delete().reason(reason);
    }

    @Override
    public RestAction<?> rename(String name) {
        return delegate.getManager().setName(name);
    }
}
