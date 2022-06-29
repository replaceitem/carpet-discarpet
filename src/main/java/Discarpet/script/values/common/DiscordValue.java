package Discarpet.script.values.common;

import Discarpet.script.util.ValueUtil;
import carpet.script.annotation.OutputConverter;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.Value;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtString;

import java.util.Optional;

public abstract class DiscordValue<T> extends Value {
    
    protected final T delegate;
    
    protected DiscordValue(String typeName, T delegate) {
        this.delegate = delegate;
        this.typeName = "dc_" + typeName;
    }
    
    public T getInternal() {
        return delegate;
    }
    
    public abstract Value getProperty(String property);

    @SuppressWarnings("unchecked")
    public static Value of(Object object) {
        if(object == null) return Value.NULL;
        Class<Object> toClass = (Class<Object>) findOutputConvertibleInterface(object.getClass());
        if(toClass == null) throw new InternalExpressionException("Could not find an interface for an output converter for DiscordValue.of() and type " + object.getClass());
        OutputConverter<Object> converter = getOutputConverter(toClass);
        if(converter == null) throw new InternalExpressionException("Could not find a suitable output converter for DiscordValue.of() and type " + object.getClass());
        return converter.convert(object).evalValue(null);
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
    public Value in(Value value1) {
        return this.getProperty(value1.getString());
    }

    private final String typeName; 
    
    @Override
    public String getTypeString() {
        return typeName;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public String getString() {
        return getTypeString();
    }

    @Override
    public boolean getBoolean() {
        return true;
    }

    @Override
    public NbtElement toTag(boolean force) {
        return NbtString.of(getString());
    }
}
