package Discarpet.script.values;

import Discarpet.script.values.common.DiscordValue;
import carpet.script.value.NumericValue;
import carpet.script.value.Value;
import org.javacord.api.entity.message.Reaction;

public class ReactionValue extends DiscordValue<Reaction> {
    public ReactionValue(Reaction reaction) {
        super("reaction",reaction);
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "emoji" -> new EmojiValue(delegate.getEmoji());
            case "count" -> NumericValue.of(delegate.getCount());
            case "message" -> new MessageValue(delegate.getMessage());
            default -> super.getProperty(property);
        };
    }
}
