package Discarpet.script.values.common;

import org.javacord.api.interaction.InteractionBase;

public abstract class InteractionValue<T extends InteractionBase> extends DiscordValue<T> {
    public InteractionValue(String typeName, T value) {
        super(typeName, value);
    }
    
    public T getBase() {
        return this.value;
    }
}
