package Discarpet.script.functions;

import Discarpet.script.util.ValueUtil;
import Discarpet.script.values.RoleValue;
import carpet.script.annotation.ScarpetFunction;
import carpet.script.value.ListValue;
import carpet.script.value.Value;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class Users {
    @ScarpetFunction
    public String dc_get_display_name(User user, Server server) {
        return server.getDisplayName(user);
    }

    @ScarpetFunction
    public boolean dc_set_nickname(User user, Server server, String name) {
        return ValueUtil.awaitFutureBoolean(user.updateNickname(server, name),"Error updating nickname");
    }

    @ScarpetFunction
    public boolean dc_add_role(User user, Role role, String reason) {
        return ValueUtil.awaitFutureBoolean(user.addRole(role, reason),"Could not add role to user");
    }

    @ScarpetFunction
    public boolean dc_remove_role(User user, Role role, String reason) {
        return ValueUtil.awaitFutureBoolean(user.removeRole(role, reason),"Could not add role to user");
    }

    @ScarpetFunction
    public ListValue dc_get_user_roles(User user, Server server) {
        return ListValue.wrap(user.getRoles(server).stream().map(RoleValue::of));
    }

    @ScarpetFunction
    public Value dc_get_user_color(User user, Server server) {
        return ValueUtil.colorToValue(ValueUtil.unpackOptional(user.getRoleColor(server)));
    }
}
