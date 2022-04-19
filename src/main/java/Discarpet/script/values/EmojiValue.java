package Discarpet.script.values;

import Discarpet.script.util.ValueUtil;
import carpet.script.value.BooleanValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.minecraft.nbt.NbtElement;
import org.javacord.api.entity.emoji.Emoji;

import java.util.Optional;

public class EmojiValue extends Value {

    private Emoji emoji;

    public EmojiValue(Emoji emoji) {
        this.emoji = emoji;
    }

    public static Value of(Emoji emoji) {
        if(emoji == null) return Value.NULL;
        return new EmojiValue(emoji);
    }
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static Value of(Optional<? extends Emoji> optionalEmoji){
        return of(ValueUtil.unpackOptional(optionalEmoji));
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "mention_tag" -> StringValue.of(emoji.getMentionTag());
            case "unicode" -> ValueUtil.ofOptionalString(emoji.asUnicodeEmoji());
            case "is_animated" -> BooleanValue.of(emoji.isAnimated());
            case "is_unicode" -> BooleanValue.of(emoji.isUnicodeEmoji());
            case "is_custom" -> BooleanValue.of(emoji.isCustomEmoji());
            default -> Value.NULL;
        };
    }

    public Emoji getEmoji() {
        return emoji;
    }

    @Override
    public Value in(Value value1) {
        return getProperty(value1.getString());
    }

    @Override
    public String getTypeString() {
        return "dc_emoji";
    }

    @Override
    public String getString() {
        return emoji.toString();
    }

    @Override
    public boolean getBoolean() {
        return true;
    }

    @Override
    public NbtElement toTag(boolean b) {
        return null;
    }
}
