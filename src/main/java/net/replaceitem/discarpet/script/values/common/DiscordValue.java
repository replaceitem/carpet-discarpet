package net.replaceitem.discarpet.script.values.common;

import carpet.script.exception.InternalExpressionException;
import carpet.script.value.Value;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

import java.util.Objects;

public abstract class DiscordValue<T> extends Value {
    
    protected final T delegate;
    
    protected DiscordValue(T delegate) {
        this.delegate = Objects.requireNonNull(delegate, this::getTypeString);
    }
    
    public T getDelegate() {
        return delegate;
    }
    
    public Value getProperty(String property) {
        throw new InternalExpressionException("Unknown feature for " + getTypeString() + ": " + property);
    }
    
    @Override
    public Value in(Value value1) {
        return this.getProperty(value1.getString());
    }

    protected abstract String getDiscordTypeString();

    @Override
    public String getTypeString() {
        return "dc_" + this.getDiscordTypeString();
    }

    @Override
    public String getString() {
        return delegate.toString();
    }

    @Override
    public boolean getBoolean() {
        return true;
    }

    @Override
    public Tag toTag(boolean force, RegistryAccess regs) {
        return StringTag.valueOf(getString());
    }
}
