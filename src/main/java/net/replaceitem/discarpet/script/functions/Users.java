package net.replaceitem.discarpet.script.functions;

import carpet.script.annotation.ScarpetFunction;
import carpet.script.value.ListValue;
import carpet.script.value.NumericValue;
import carpet.script.value.Value;
import net.replaceitem.discarpet.script.values.RoleValue;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.time.Instant;
import java.util.Optional;

import static net.replaceitem.discarpet.script.util.ValueUtil.*;

@SuppressWarnings("unused")
public class Users {
    @ScarpetFunction
    public String dc_get_display_name(User user, Server server) {
        return server.getDisplayName(user);
    }

    @ScarpetFunction(maxParams = 4)
    public void dc_set_nickname(User user, Server server, String name, String... reason) {
        awaitFuture(user.updateNickname(server, name, optionalArg(reason).orElse(null)),"Error updating nickname");
    }

    @ScarpetFunction(maxParams = 3)
    public void dc_add_role(User user, Role role, String... reason) {
        awaitFuture(user.addRole(role, optionalArg(reason).orElse(null)),"Could not add role to user");
    }

    @ScarpetFunction(maxParams = 3)
    public void dc_remove_role(User user, Role role, String... reason) {
        awaitFuture(user.removeRole(role, optionalArg(reason).orElse(null)),"Could not remove role from user");
    }

    @ScarpetFunction
    public ListValue dc_get_user_roles(User user, Server server) {
        return ListValue.wrap(user.getRoles(server).stream().map(RoleValue::of));
    }

    @ScarpetFunction
    public Value dc_get_user_color(User user, Server server) {
        return colorToValue(unpackOptional(user.getRoleColor(server)));
    }

    @ScarpetFunction(maxParams = 4)
    public Value dc_timeout(User user, Server server, Value... values) {
        Optional<Value> timestampValue = optionalArg(values, 0);
        if(timestampValue.isEmpty()) return NumericValue.of(user.getActiveTimeout(server).map(Instant::toEpochMilli).orElse(null));
        long timestamp = NumericValue.asNumber(timestampValue.get()).getLong();
        String reason = optionalArg(values, 1).map(Value::getString).orElse(null);
        awaitFuture(user.timeout(server, Instant.ofEpochMilli(timestamp), reason), "Could not timeout user");
        return Value.NULL;
    }
}
