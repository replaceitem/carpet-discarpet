package Discarpet.script.values;

import Discarpet.script.util.ValueUtil;
import Discarpet.script.values.common.DiscordValue;
import carpet.script.value.ListValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import com.vdurmont.emoji.EmojiParser;
import org.javacord.api.entity.message.Message;

public class MessageValue extends DiscordValue<Message> {
    public MessageValue(Message message) {
        super("message",message);
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "content" -> StringValue.of(value.getContent());
            case "readable_content" -> StringValue.of(EmojiParser.parseToAliases(value.getReadableContent())); //not all user mentions will be parsed, if they are not cached
            case "id" -> StringValue.of(value.getIdAsString());
            case "channel" -> new ChannelValue(value.getChannel());
            case "user" -> UserValue.of(value.getUserAuthor());
            case "server" -> ServerValue.of(value.getServer());
            case "nonce" -> ValueUtil.ofOptionalString(value.getNonce());
            case "attachments" -> ListValue.wrap(value.getAttachments().stream().map(AttachmentValue::new));
            default -> Value.NULL;
        };
    }
}
