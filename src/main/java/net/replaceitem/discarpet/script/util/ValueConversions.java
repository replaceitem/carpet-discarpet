package net.replaceitem.discarpet.script.util;

import carpet.script.annotation.OutputConverter;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.Value;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

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
        return Stream.concat(Stream.of(inputClass.getSuperclass()), Arrays.stream(inputClass.getInterfaces()))
                .filter(Objects::nonNull)
                .map(ValueConversions::findOutputConvertibleInterface)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    @SuppressWarnings("unchecked")
    public static Value toValue(Object object) {
        if(object == null) return Value.NULL;
        Class<Object> toClass = (Class<Object>) ValueConversions.findOutputConvertibleInterface(object.getClass());
        if(toClass == null) throw new InternalExpressionException("Could not find an interface for an output converter for DiscordValue.of() and type " + object.getClass());
        OutputConverter<Object> converter = ValueConversions.getOutputConverter(toClass);
        if(converter == null) throw new InternalExpressionException("Could not find a suitable output converter for DiscordValue.of() and type " + object.getClass());
        //noinspection DataFlowIssue
        return converter.convert(object, null);
    }
}
