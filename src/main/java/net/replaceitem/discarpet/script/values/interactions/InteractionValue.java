package net.replaceitem.discarpet.script.values.interactions;

import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.ChannelValue;
import net.replaceitem.discarpet.script.values.MemberValue;
import net.replaceitem.discarpet.script.values.ServerValue;
import net.replaceitem.discarpet.script.values.UserValue;
import net.replaceitem.discarpet.script.values.common.DiscordValue;

public abstract class InteractionValue<T extends GenericInteractionCreateEvent> extends DiscordValue<T> {
    public InteractionValue(T value) {
        super(value);
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "id" -> StringValue.of(delegate.getId());
            case "channel" -> ChannelValue.of(delegate.getChannel());
            case "user" -> UserValue.of(delegate.getUser());
            case "member" -> MemberValue.of(delegate.getMember());
            case "token" -> StringValue.of(delegate.getToken());
            case "server" -> ServerValue.of(delegate.getGuild());
            case "locale" -> StringValue.of(delegate.getUserLocale().getLocale());
            case "creation_timestamp" -> ValueUtil.ofTime(delegate.getTimeCreated());
            default -> super.getProperty(property);
        };
    }
    
    public T getBase() {
        return this.delegate;
    }
}
