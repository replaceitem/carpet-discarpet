package net.replaceitem.discarpet.script.parsable.parsables;

import carpet.script.exception.InternalExpressionException;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.utils.MiscUtil;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;
import net.replaceitem.discarpet.script.values.EmojiValue;
import org.jetbrains.annotations.Nullable;

@ParsableClass(name = "emoji")
public class EmojiParsable implements ParsableConstructor<Emoji> {
    
    @OptionalField @Nullable
    String name;
    @OptionalField @Nullable
    String unicode;
    @OptionalField @Nullable
    String id;
    @OptionalField
    Boolean animated = false;

    @Override
    public Emoji construct() {
        if(name != null && id != null) return Emoji.fromCustom(name, MiscUtil.parseSnowflake(id), animated);
        if(unicode != null) return Emoji.fromUnicode(unicode);
        throw new InternalExpressionException("Expected either 'unicode' or 'name' and 'id'");
    }

    @Override
    public @Nullable Emoji tryCreateFromValueDirectly(Value value) {
        return fromValue(value);
    }
    
    public static @Nullable Emoji fromValue(Value value) {
        if(value instanceof EmojiValue emojiValue) {
            return emojiValue.getDelegate();
        }
        if(value instanceof StringValue) return Emoji.fromFormatted(value.getString());
        return null;
    }
}
