package net.replaceitem.discarpet.script.values.common;

import org.javacord.api.entity.message.Messageable;

public abstract class MessageableValue<T> extends DiscordValue<T> {
    public MessageableValue(T value) {
        super(value);
    }
    
    public Messageable getMessageable() {
        return this.delegate instanceof Messageable messageable ? messageable : null;
    }
    
    public boolean isMessageable() {
        return this.delegate instanceof Messageable;
    }
}
