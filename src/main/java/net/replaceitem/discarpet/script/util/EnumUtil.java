package net.replaceitem.discarpet.script.util;

import carpet.script.exception.InternalExpressionException;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class EnumUtil {
    public static <T extends Enum<T>> Optional<T> getEnum(Class<T> enumClass, String name) {
        for (T enumConstant : enumClass.getEnumConstants()) {
            if(enumConstant.name().equalsIgnoreCase(name)) return Optional.of(enumConstant);
        }
        return Optional.empty();
    }

    public static <T extends Enum<T>> T getEnumOrThrow(Class<T> enumClass, String name, String error) {
        return getEnum(enumClass, name).orElseThrow(() ->
                new InternalExpressionException("%s: Value %s is unknown, expected one of %s%s".formatted(
                        error,
                        name,
                        Arrays.stream(enumClass.getEnumConstants()).limit(8).map(type -> type.name().toLowerCase()).collect(Collectors.joining(", ")),
                        enumClass.getEnumConstants().length > 8 ? ", ..." : "")
                )
        );
    }
}
