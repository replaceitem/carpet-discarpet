package Discarpet.script.values.common;

import Discarpet.script.util.ValueUtil;
import carpet.script.annotation.OutputConverter;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.Value;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtString;

import java.util.Optional;

public abstract class DiscordValue<T> extends Value {
    
    protected final T value;
    
    protected DiscordValue(String typeName, T value) {
        this.value = value;
        this.typeName = typeName;
    }
    
    public T getInternal() {
        return value;
    }
    
    public abstract Value getProperty(String property);

    @SuppressWarnings("unchecked")
    public static <V> Value of(V object) {
        if(object == null) return Value.NULL;
        OutputConverter<V> outputConverter = null;
        try {
            outputConverter = (OutputConverter<V>) OutputConverter.get(object.getClass());
        } catch (NullPointerException ignored) {}
        if(outputConverter == null) {
            Class<?>[] interfaces = object.getClass().getInterfaces();
            for (Class<?> interfaceClass : interfaces) {
                try {
                    outputConverter = (OutputConverter<V>) OutputConverter.get(interfaceClass);
                } catch (NullPointerException ignored) {}
                if(outputConverter != null) break;
            }
        }
        if(outputConverter == null) throw new InternalExpressionException("Could not find a suitable output converter for DiscordValue.of()");
        return outputConverter.convert(object).evalValue(null);
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
        return "dc_" + typeName;
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
