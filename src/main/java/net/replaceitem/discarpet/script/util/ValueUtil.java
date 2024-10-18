package net.replaceitem.discarpet.script.util;

import carpet.script.value.BooleanValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.replaceitem.discarpet.script.values.EmojiValue;
import net.replaceitem.discarpet.script.exception.DiscordThrowables;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.core.entity.emoji.UnicodeEmojiImpl;

import java.awt.*;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class ValueUtil {


    public static <T> T awaitFuture(CompletableFuture<T> cf, String message) {
        try {
            return cf.get();
        } catch (Exception e) {
            throw DiscordThrowables.convert(e, message);
        }
    }

    public static <T> T unpackOptional(Optional<T> optional) {
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


    public static Value colorToValue(Color color) {
        if(color == null) return Value.NULL;
        return StringValue.of(String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue()));
    }
    
    public static Emoji emojiFromValue(Value value) {
        return value==null? null : (value instanceof EmojiValue emojiValue ? emojiValue.getDelegate() : UnicodeEmojiImpl.fromString(value.getString()));
    }
    
    public static <T> Optional<T> optionalArg(T[] array, int index) {
        if(array == null) return Optional.empty();
        return array.length > index ? Optional.of(array[index]) : Optional.empty();
    }

    public static <T> Optional<T> optionalArg(T[] array) {
        return optionalArg(array, 0);
    }
}
