package Discarpet.script.util;

import Discarpet.Discarpet;
import carpet.script.annotation.OutputConverter;
import carpet.script.value.StringValue;
import carpet.script.value.Value;

import java.awt.Color;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class ValueUtil {


    public static <T> T awaitFuture(CompletableFuture<T> cf, String error) {
        try {
            return cf.get();
        } catch (Exception e) {
            Discarpet.LOGGER.error(error, e);
            return null;
        }
    }

    public static boolean awaitFutureBoolean(CompletableFuture<?> cf, String error) {
        try {
            cf.get();
        } catch (Exception e) {
            Discarpet.LOGGER.error(error, e);
            return false;
        }
        return true;
    }

    public static <T> T unpackOptional(Optional<T> optional) {
        if(optional.isEmpty()) return null;
        return optional.get();
    }

    public static Value convertToValue(Object object) {
        if(object == null) return Value.NULL;
        @SuppressWarnings("unchecked")
        Class<Object> objectClass = (Class<Object>) object.getClass();
        OutputConverter<Object> converter = OutputConverter.get(objectClass);
        if(converter == null) {
            return Value.NULL;
        }
        return converter.convert(object).evalValue(null);
    }

    public static Value ofOptionalString(Optional<String> optionalString) {
        if(optionalString.isEmpty()) return Value.NULL;
        return StringValue.of(optionalString.get());
    }


    public static Value colorToValue(Color color) {
        if(color == null) return Value.NULL;
        return StringValue.of(String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue()));
    }
}
