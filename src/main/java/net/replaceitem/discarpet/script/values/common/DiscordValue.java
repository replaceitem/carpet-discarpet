package net.replaceitem.discarpet.script.values.common;

import carpet.script.value.Value;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.DynamicRegistryManager;
import net.replaceitem.discarpet.script.util.ValueConversions;
import net.replaceitem.discarpet.script.util.ValueUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public abstract class DiscordValue<T> extends Value {
    
    protected final T delegate;
    
    protected DiscordValue(T delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }
    
    public T getDelegate() {
        return delegate;
    }
    
    public Value getProperty(String property) {
        return NULL;
    }

    public static Value of(@Nullable Object object) {
        return ValueConversions.toValue(object);
    }
    
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static <V> Value of(Optional<? extends V> optional){
        return of(ValueUtil.unpackOptional(optional));
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
    public NbtElement toTag(boolean force, DynamicRegistryManager regs) {
        return NbtString.of(getString());
    }
}
