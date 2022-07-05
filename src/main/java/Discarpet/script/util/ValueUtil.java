package Discarpet.script.util;

import Discarpet.Discarpet;
import Discarpet.script.values.EmojiValue;
import carpet.script.value.BooleanValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.core.entity.emoji.UnicodeEmojiImpl;

import javax.annotation.Nullable;
import java.awt.*;
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
    
    public static Value ofOptionalString(Optional<String> optionalString) {
        if(optionalString.isEmpty()) return Value.NULL;
        return StringValue.of(optionalString.get());
    }

    public static Value ofOptionalNumber(Optional<Integer> optionalInteger) {
        if(optionalInteger.isEmpty()) return Value.NULL;
        return NumericValue.of(optionalInteger.get());
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
    
    @Nullable
    public static <T> T optionalArg(T[] array, int index) {
        if(array == null) return null;
        return array.length > index ? array[index] : null;
    }

    @Nullable
    public static <T> T optionalArg(T[] array) {
        return optionalArg(array, 0);
    }
}
