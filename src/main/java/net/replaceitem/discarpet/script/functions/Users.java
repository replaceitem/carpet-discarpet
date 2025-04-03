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

@SuppressWarnings({"unused", "OptionalUsedAsFieldOrParameterType"})
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

    @ScarpetFunction
    public Value dc_get_timeout(User user, Server server) {
        return NumericValue.of(user.getActiveTimeout(server).map(Instant::toEpochMilli).orElse(null));
    }
    
    @ScarpetFunction(maxParams = 4)
    public void dc_set_timeout(User user, Server server, long epochMilli, Optional<String> reason) {
        awaitFuture(user.timeout(server, Instant.ofEpochMilli(epochMilli), reason.orElse(null)), "Could not timeout user");
    }
}
