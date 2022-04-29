package Discarpet.script.values.common;

import org.javacord.api.entity.message.Messageable;

public abstract class MessageableValue<T> extends DiscordValue<T> {
    public MessageableValue(String typeName, T value) {
        super(typeName, value);
    }
    
    public Messageable getMessageable() {
        return this.value instanceof Messageable messageable ? messageable : null;
    }
}
