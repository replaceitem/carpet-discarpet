package Discarpet.script.values;

import Discarpet.script.util.ValueUtil;
import Discarpet.script.values.common.DiscordValue;
import carpet.script.value.BooleanValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.entity.emoji.Emoji;

public class EmojiValue extends DiscordValue<Emoji> {
    public EmojiValue(Emoji emoji) {
        super("emoji",emoji);
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "mention_tag" -> StringValue.of(value.getMentionTag());
            case "unicode" -> ValueUtil.ofOptionalString(value.asUnicodeEmoji());
            case "is_animated" -> BooleanValue.of(value.isAnimated());
            case "is_unicode" -> BooleanValue.of(value.isUnicodeEmoji());
            case "is_custom" -> BooleanValue.of(value.isCustomEmoji());
            default -> Value.NULL;
        };
    }
}
