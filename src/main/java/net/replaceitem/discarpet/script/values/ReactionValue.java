package net.replaceitem.discarpet.script.values;

import carpet.script.value.StringValue;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.replaceitem.discarpet.script.values.common.DiscordValue;
import carpet.script.value.NumericValue;
import carpet.script.value.Value;

public class ReactionValue extends DiscordValue<MessageReaction> {
    public ReactionValue(MessageReaction reaction) {
        super(reaction);
    }

    @Override
    protected String getDiscordTypeString() {
        return "reaction";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "emoji" -> EmojiValue.of(delegate.getEmoji());
            case "count" -> NumericValue.of(delegate.getCount());
            case "message_id" -> StringValue.of(delegate.getMessageId());
            case "channel" -> ChannelValue.of(delegate.getChannel());
            case "server" -> ServerValue.of(delegate.getGuild());
            default -> super.getProperty(property);
        };
    }
}
