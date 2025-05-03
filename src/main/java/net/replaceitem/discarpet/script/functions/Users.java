package net.replaceitem.discarpet.script.functions;

import carpet.script.annotation.ScarpetFunction;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.ListValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.RoleValue;

import java.awt.*;
import java.time.Instant;
import java.util.Optional;

@SuppressWarnings({"unused", "OptionalUsedAsFieldOrParameterType"})
public class Users {
    @ScarpetFunction
    public String dc_get_display_name(User user, Guild server) {
        Member member = server.getMember(user);
        if(member == null) return null;
        return member.getEffectiveName();
    }

    @ScarpetFunction(maxParams = 4)
    public void dc_set_nickname(User user, Guild server, String name, String... reason) {
        Member member = server.getMember(user);
        if(member == null) throw new InternalExpressionException("Unknown member");
        ValueUtil.awaitRest(server.modifyNickname(member, name).reason(ValueUtil.optionalArg(reason)),"Error updating nickname");
    }

    @ScarpetFunction(maxParams = 3)
    public void dc_add_role(User user, Role role, String... reason) {
        Member member = role.getGuild().getMember(user);
        if(member == null) throw new InternalExpressionException("Unknown member");
        ValueUtil.awaitRest(role.getGuild().addRoleToMember(user, role).reason(ValueUtil.optionalArg(reason)), "Could not add role to user");
    }

    @ScarpetFunction(maxParams = 3)
    public void dc_remove_role(User user, Role role, String... reason) {
        Member member = role.getGuild().getMember(user);
        if(member == null) throw new InternalExpressionException("Unknown member");
        ValueUtil.awaitRest(role.getGuild().removeRoleFromMember(user, role).reason(ValueUtil.optionalArg(reason)), "Could not remove role to user");
    }

    @ScarpetFunction
    public Value dc_get_user_roles(User user, Guild server) {
        Member member = server.getMember(user);
        if(member == null) return Value.NULL;
        return ListValue.wrap(member.getRoles().stream().map(RoleValue::of));
    }

    @ScarpetFunction
    public Value dc_get_user_color(User user, Guild server) {
        Member member = server.getMember(user);
        if(member == null) return Value.NULL;
        Color color = member.getColor();
        return color == null ? Value.NULL : ValueUtil.colorToValue(color);
    }

    @ScarpetFunction
    public Value dc_get_timeout(User user, Guild server) {
        Member member = server.getMember(user);
        if(member == null) return Value.NULL;
        return ValueUtil.ofTime(member.getTimeOutEnd());
    }
    
    @ScarpetFunction(maxParams = 4)
    public void dc_set_timeout(User user, Guild server, long epochMilli, Optional<String> reason) {
        ValueUtil.awaitRest(server.timeoutUntil(user, Instant.ofEpochMilli(epochMilli)), "Could not timeout user");
    }
}
