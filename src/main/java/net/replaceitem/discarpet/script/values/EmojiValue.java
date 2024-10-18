package net.replaceitem.discarpet.script.values;

import net.replaceitem.discarpet.script.exception.DiscordThrowables;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.common.Deletable;
import net.replaceitem.discarpet.script.values.common.DiscordValue;
import net.replaceitem.discarpet.script.values.common.Renamable;
import carpet.script.value.BooleanValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.entity.DiscordEntity;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.emoji.KnownCustomEmoji;

public class EmojiValue extends DiscordValue<Emoji> implements Deletable, Renamable {
    public EmojiValue(Emoji emoji) {
        super(emoji);
    }

    @Override
    protected String getDiscordTypeString() {
        return "emoji";
    }

    @Override
    public Value getProperty(String property) {
        return switch (property) {
            case "id" -> ValueUtil.ofOptionalString(delegate.asCustomEmoji().map(DiscordEntity::getIdAsString));
            case "mention_tag" -> StringValue.of(delegate.getMentionTag());
            case "unicode" -> ValueUtil.ofOptionalString(delegate.asUnicodeEmoji());
            case "is_animated" -> BooleanValue.of(delegate.isAnimated());
            case "is_unicode" -> BooleanValue.of(delegate.isUnicodeEmoji());
            case "is_custom" -> BooleanValue.of(delegate.isCustomEmoji());
            default -> super.getProperty(property);
        };
    }

    @Override
    public void delete(String reason) {
        if(!(delegate instanceof KnownCustomEmoji knownCustomEmoji)) throw DiscordThrowables.genericCode(DiscordThrowables.Codes.INVALID_EMOJI);
        ValueUtil.awaitFuture(knownCustomEmoji.delete(reason), "Failed to delete " + this.getTypeString());
    }

    @Override
    public void rename(String name) {
        if(!(delegate instanceof KnownCustomEmoji knownCustomEmoji)) throw DiscordThrowables.genericCode(DiscordThrowables.Codes.INVALID_EMOJI);
        ValueUtil.awaitFuture(knownCustomEmoji.updateName(name), "Could not rename emoji");
    }
}
