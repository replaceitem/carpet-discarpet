package net.replaceitem.discarpet.script.values;

import carpet.script.value.NumericValue;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.common.Deletable;
import net.replaceitem.discarpet.script.values.common.DiscordValue;
import carpet.script.value.ListValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.entity.message.Message;

import java.time.Instant;

public class MessageValue extends DiscordValue<Message> implements Deletable {
    public MessageValue(Message message) {
        super(message);
    }

    @Override
    protected String getDiscordTypeString() {
        return "message";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "content" -> StringValue.of(delegate.getContent());
            case "readable_content" -> StringValue.of(delegate.getReadableContent()); //not all user mentions will be parsed, if they are not cached
            case "id" -> StringValue.of(delegate.getIdAsString());
            case "channel" -> new ChannelValue(delegate.getChannel());
            case "user" -> UserValue.of(delegate.getUserAuthor());
            case "server" -> ServerValue.of(delegate.getServer());
            case "nonce" -> ValueUtil.ofOptionalString(delegate.getNonce());
            case "attachments" -> ListValue.wrap(delegate.getAttachments().stream().map(AttachmentValue::new));
            case "referenced_message" -> MessageValue.of(delegate.getReferencedMessage());
            case "type" -> StringValue.of(delegate.getType().name());
            case "link" -> StringValue.of(delegate.getLink().toString());
            case "flags" -> ListValue.wrap(delegate.getFlags().stream().map(Enum::name).map(StringValue::of));
            case "creation_timestamp" -> NumericValue.of(delegate.getCreationTimestamp().toEpochMilli());
            case "edit_timestamp" -> delegate.getLastEditTimestamp().map(Instant::toEpochMilli).map(NumericValue::of).orElse(Value.NULL);
            case "position" -> delegate.getPosition().map(NumericValue::of).orElse(Value.NULL);
            default -> super.getProperty(property);
        };
    }

    @Override
    public boolean delete(String reason) {
        return ValueUtil.awaitFutureBoolean(delegate.delete(reason), "Failed to delete " + this.getTypeString());
    }
}
