package net.replaceitem.discarpet.script.util;

import carpet.script.exception.InternalExpressionException;
import carpet.script.value.BooleanValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.requests.RestAction;
import net.replaceitem.discarpet.script.exception.DiscordThrowables;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class ValueUtil {


    public static <T> T awaitFuture(CompletableFuture<T> cf, String message) {
        try {
            return cf.get();
        } catch (Exception e) {
            throw DiscordThrowables.convert(e, message);
        }
    }
    
    public static <T> T awaitRest(RestAction<T> restAction, String message) {
        try {
            return restAction.complete();
        } catch (Exception e) {
            throw DiscordThrowables.convert(e, message);
        }
    }
    
    public static <T> Value ofNullable(@Nullable T val, Function<T,Value> constructor) {
        return val == null ? Value.NULL : constructor.apply(val);
    }

    public static <T> @Nullable T unpackOptional(Optional<T> optional) {
        return optional.orElse(null);
    }
    
    public static Value ofOptionalString(Optional<String> optionalString) {
        return optionalString.map(StringValue::of).orElse(Value.NULL);
    }

    public static Value ofOptionalNumber(Optional<? extends Number> optionalInteger) {
        return optionalInteger.map(NumericValue::of).orElse(Value.NULL);
    }

    public static Value ofOptionalBoolean(Optional<Boolean> optionalBoolean) {
        if(optionalBoolean.isEmpty()) return Value.NULL;
        return BooleanValue.of(optionalBoolean.get());
    }
    
    public static Value ofPositiveInt(int v) {
        if(v < 0) return Value.NULL;
        return NumericValue.of(v);
    }
    
    public static Value ofEnum(Enum<?> enumValue) {
        return StringValue.of(enumValue.name().toLowerCase(Locale.ROOT));
    }
    
    public static Value ofInstant(@Nullable Instant instant) {
        return instant == null ? Value.NULL : NumericValue.of(instant.toEpochMilli());
    }
    
    public static Value ofTime(@Nullable OffsetDateTime time) {
        return time == null ? Value.NULL : ofInstant(time.toInstant());
    }


    public static Value colorToValue(@Nullable Color color) {
        if(color == null) return Value.NULL;
        return StringValue.of(String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue()));
    }
    
    public static <T> @Nullable T optionalArg(T@Nullable[] array, int index) {
        if(array == null) return null;
        return array.length > index ? array[index] : null;
    }

    public static <T> @Nullable T optionalArg(T@Nullable[] array) {
        return optionalArg(array, 0);
    }
    
    public static <T extends Enum<T>> Optional<T> getEnum(Class<T> enumClass, String name) {
        for (T enumConstant : enumClass.getEnumConstants()) {
            if(enumConstant.name().equalsIgnoreCase(name)) return Optional.of(enumConstant);
        }
        return Optional.empty();
    }
    
    public static <T extends Enum<T>> T getEnumOrThrow(Class<T> enumClass, String name, String error) {
        return getEnum(enumClass, name).orElseThrow(() ->
                new InternalExpressionException("%s: Value %s is unknown, expected one of %s%s".formatted(error,
                        enumClass.getSimpleName(),
                        Arrays.stream(enumClass.getEnumConstants()).limit(8).map(type -> type.name().toLowerCase()).collect(Collectors.joining(", ")),
                        enumClass.getEnumConstants().length > 8 ? ", ..." : "")
                )
        );
    }
}
