package Discarpet.script.values;

import Discarpet.script.util.ValueUtil;
import Discarpet.script.values.common.Deletable;
import Discarpet.script.values.common.DiscordValue;
import carpet.script.value.ListValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.entity.message.Message;

public class MessageValue extends DiscordValue<Message> implements Deletable {
    public MessageValue(Message message) {
        super("message",message);
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
            default -> Value.NULL;
        };
    }

    @Override
    public boolean delete() {
        return ValueUtil.awaitFutureBoolean(delegate.delete(), "Failed to delete " + this.getTypeString());
    }
}
