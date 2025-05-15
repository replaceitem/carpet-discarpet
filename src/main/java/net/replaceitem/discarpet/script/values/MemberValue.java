package net.replaceitem.discarpet.script.values;

import carpet.script.value.BooleanValue;
import carpet.script.value.ListValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.entities.Member;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.common.DiscordValue;
import org.jetbrains.annotations.Nullable;

public class MemberValue extends DiscordValue<Member> {

    public MemberValue(Member delegate) {
        super(delegate);
    }
    
    public static Value of(@Nullable Member member) {
        return ValueUtil.ofNullable(member, MemberValue::new);
    }

    @Override
    protected String getDiscordTypeString() {
        return "member";
    }

    @Override
    public Value getProperty(String property) {
        return switch (property) {
            case "user" -> UserValue.of(delegate.getUser());
            case "server" -> ServerValue.of(delegate.getGuild());
            case "color" -> ValueUtil.colorToValue(delegate.getColor());
            case "roles" -> ListValue.wrap(delegate.getRoles().stream().map(RoleValue::of));
            case "nickname" -> StringValue.of(delegate.getNickname());
            case "effective_name" -> StringValue.of(delegate.getEffectiveName());
            case "online_status" -> ValueUtil.ofEnum(delegate.getOnlineStatus());
            case "is_boosting" -> BooleanValue.of(delegate.isBoosting());
            case "is_owner" -> BooleanValue.of(delegate.isOwner());
            case "is_pending" -> BooleanValue.of(delegate.isPending());
            case "timeout_end" -> ValueUtil.ofTime(delegate.getTimeOutEnd());
            case "avatar_url" -> StringValue.of(delegate.getAvatarUrl());
            case "effective_avatar_url" -> StringValue.of(delegate.getEffectiveAvatarUrl());
            default -> super.getProperty(property);
        };
    }
}
