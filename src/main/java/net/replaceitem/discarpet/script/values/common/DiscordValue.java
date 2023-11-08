package net.replaceitem.discarpet.script.values.common;

import net.replaceitem.discarpet.script.util.ValueUtil;
import carpet.script.annotation.OutputConverter;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.Value;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtString;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public abstract class DiscordValue<T> extends Value {
    
    protected final T delegate;
    
    protected DiscordValue(T delegate) {
        this.delegate = delegate;
    }
    
    public T getDelegate() {
        return delegate;
    }
    
    public Value getProperty(String property) {
        return NULL;
    }

    @SuppressWarnings("unchecked")
    public static Value of(Object object) {
        if(object == null) return Value.NULL;
        Class<Object> toClass = (Class<Object>) findOutputConvertibleInterface(object.getClass());
        if(toClass == null) throw new InternalExpressionException("Could not find an interface for an output converter for DiscordValue.of() and type " + object.getClass());
        OutputConverter<Object> converter = getOutputConverter(toClass);
        if(converter == null) throw new InternalExpressionException("Could not find a suitable output converter for DiscordValue.of() and type " + object.getClass());
        return converter.convert(object);
    }

    private static <V> OutputConverter<V> getOutputConverter(Class<V> inputClass) {
        try {
            return OutputConverter.get(inputClass);
        } catch (NullPointerException npe) {
            return null;
        }
    }
    
    private static Class<?> findOutputConvertibleInterface(Class<?> inputClass) {
        if(getOutputConverter(inputClass) != null) return inputClass;
        Class<?>[] interfaces = inputClass.getInterfaces();
        for (Class<?> interfaceClass : interfaces) {
            Class<?> convertibleInterface = findOutputConvertibleInterface(interfaceClass);
            if(convertibleInterface != null) return convertibleInterface;
        }
        return null;
    }
    
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static <V> Value of(Optional<? extends V> optional){
        return of(ValueUtil.unpackOptional(optional));
    }

    @Override
    @NotNull
    public Value in(Value value1) {
        return this.getProperty(value1.getString());
    }

    protected abstract String getDiscordTypeString();

    @Override
    @NotNull
    public String getTypeString() {
        return "dc_" + this.getDiscordTypeString();
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    @NotNull
    public String getString() {
        return delegate.toString();
    }

    @Override
    public boolean getBoolean() {
        return true;
    }

    @Override
    @NotNull
    public NbtElement toTag(boolean force) {
        return NbtString.of(getString());
    }
}
