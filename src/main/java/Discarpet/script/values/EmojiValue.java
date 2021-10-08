package Discarpet.script.values;

import carpet.script.value.BooleanValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.minecraft.nbt.NbtElement;
import org.javacord.api.entity.emoji.Emoji;

import java.util.Optional;

public class EmojiValue extends Value {

    public Emoji emoji;

    public EmojiValue(Emoji emoji) {
        this.emoji = emoji;
    }

    public Value getProperty(String property) {
        switch (property) {
            case "mention_tag":
                //for custom emojis
                return StringValue.of(emoji.getMentionTag());
            case "unicode":
                Optional<String> unicode = emoji.asUnicodeEmoji();
                if(unicode.isPresent()) {
                    return StringValue.of(unicode.get());
                } else {
                    return Value.NULL;
                }
            case "is_animated":
                return BooleanValue.of(emoji.isAnimated());
            case "is_unicode":
                return BooleanValue.of(emoji.isUnicodeEmoji());
            case "is_custom":
                return BooleanValue.of(emoji.isCustomEmoji());
            default:
                return Value.NULL;
        }
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
        return false;
    }

    @Override
    public NbtElement toTag(boolean b) {
        return null;
    }
}
