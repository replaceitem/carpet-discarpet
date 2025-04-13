package net.replaceitem.discarpet.script.values;

import carpet.script.value.BooleanValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.entities.emoji.CustomEmoji;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import net.dv8tion.jda.api.entities.emoji.UnicodeEmoji;
import net.replaceitem.discarpet.script.exception.DiscordThrowables;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.common.Deletable;
import net.replaceitem.discarpet.script.values.common.DiscordValue;
import net.replaceitem.discarpet.script.values.common.Renamable;

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
            case "id" -> StringValue.of(delegate instanceof CustomEmoji customEmoji ? customEmoji.getId() : null);
            case "mention_tag" -> StringValue.of(delegate instanceof CustomEmoji customEmoji ? customEmoji.getAsMention() : delegate.getName());
            case "unicode" -> StringValue.of(delegate instanceof UnicodeEmoji unicodeEmoji ? unicodeEmoji.getName() : null);
            case "is_animated" -> BooleanValue.of(delegate instanceof CustomEmoji customEmoji && customEmoji.isAnimated());
            case "is_unicode" -> BooleanValue.of(delegate instanceof UnicodeEmoji);
            case "is_custom" -> BooleanValue.of(delegate instanceof CustomEmoji);
            case "type" -> StringValue.of(delegate.getType().name());
            default -> super.getProperty(property);
        };
    }

    @Override
    public void delete(String reason) {
        if(!(delegate instanceof RichCustomEmoji customEmoji)) throw DiscordThrowables.genericCode(DiscordThrowables.Codes.INVALID_EMOJI);
        ValueUtil.awaitRest(customEmoji.delete().reason(reason), "Failed to delete " + this.getTypeString());
    }

    @Override
    public void rename(String name) {
        if(!(delegate instanceof RichCustomEmoji customEmoji)) throw DiscordThrowables.genericCode(DiscordThrowables.Codes.INVALID_EMOJI);
        ValueUtil.awaitRest(customEmoji.getManager().setName(name), "Could not rename emoji");
    }
}
