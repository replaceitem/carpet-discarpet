package Discarpet.script.functions;

import Discarpet.script.values.RoleValue;
import carpet.script.annotation.ScarpetFunction;
import carpet.script.value.BooleanValue;
import carpet.script.value.ListValue;
import carpet.script.value.NumericValue;
import carpet.script.value.Value;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.time.Instant;

import static Discarpet.script.util.ValueUtil.*;

@SuppressWarnings("unused")
public class Users {
    @ScarpetFunction
    public String dc_get_display_name(User user, Server server) {
        return server.getDisplayName(user);
    }

    @ScarpetFunction(maxParams = 4)
    public boolean dc_set_nickname(User user, Server server, String name, String... reason) {
        return awaitFutureBoolean(user.updateNickname(server, name, optionalArg(reason)),"Error updating nickname");
    }

    @ScarpetFunction(maxParams = 3)
    public boolean dc_add_role(User user, Role role, String... reason) {
        return awaitFutureBoolean(user.addRole(role, optionalArg(reason)),"Could not add role to user");
    }

    @ScarpetFunction(maxParams = 3)
    public boolean dc_remove_role(User user, Role role, String... reason) {
        return awaitFutureBoolean(user.removeRole(role, optionalArg(reason)),"Could not add role to user");
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
        Value timestampValue = optionalArg(values, 0);
        if(timestampValue == null) return NumericValue.of(user.getActiveTimeout(server).map(Instant::toEpochMilli).orElse(null));
        long timestamp = NumericValue.asNumber(timestampValue).getLong();
        Value reasonValue = optionalArg(values, 1);
        String reason = reasonValue == null ? null : reasonValue.getString();
        return BooleanValue.of(awaitFutureBoolean(user.timeout(server, Instant.ofEpochMilli(timestamp), reason), "Could not timeout user"));
    }
}
