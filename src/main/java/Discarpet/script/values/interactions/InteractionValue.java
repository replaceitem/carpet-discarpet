package Discarpet.script.values.interactions;

import Discarpet.script.values.ChannelValue;
import Discarpet.script.values.ServerValue;
import Discarpet.script.values.UserValue;
import Discarpet.script.values.common.DiscordValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.interaction.InteractionBase;

public abstract class InteractionValue<T extends InteractionBase> extends DiscordValue<T> {
    public InteractionValue(String typeName, T value) {
        super(typeName, value);
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "id" -> StringValue.of(delegate.getIdAsString());
            case "channel" -> ChannelValue.of(delegate.getChannel());
            case "user" -> UserValue.of(delegate.getUser());
            case "token" -> StringValue.of(delegate.getToken());
            case "server" -> ServerValue.of(delegate.getServer());
            case "locale" -> StringValue.of(delegate.getLocale().getLocaleCode());
            case "creation_timestamp" -> NumericValue.of(delegate.getCreationTimestamp().toEpochMilli());
            default -> super.getProperty(property);
        };
    }
    
    public T getBase() {
        return this.delegate;
    }
}
