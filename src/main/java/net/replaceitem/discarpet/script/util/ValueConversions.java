package net.replaceitem.discarpet.script.util;

import carpet.script.annotation.OutputConverter;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.Value;
import org.jetbrains.annotations.Nullable;

public class ValueConversions {

    private static <V> @Nullable OutputConverter<V> getOutputConverter(Class<V> inputClass) {
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

    @SuppressWarnings("unchecked")
    public static Value toValue(Object object) {
        if(object == null) return Value.NULL;
        Class<Object> toClass = (Class<Object>) ValueConversions.findOutputConvertibleInterface(object.getClass());
        if(toClass == null) throw new InternalExpressionException("Could not find an interface for an output converter for DiscordValue.of() and type " + object.getClass());
        OutputConverter<Object> converter = ValueConversions.getOutputConverter(toClass);
        if(converter == null) throw new InternalExpressionException("Could not find a suitable output converter for DiscordValue.of() and type " + object.getClass());
        return converter.convert(object, null);
    }
}
