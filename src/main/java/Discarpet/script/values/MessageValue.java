package Discarpet.script.values;

import Discarpet.script.util.ValueUtil;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import com.vdurmont.emoji.EmojiParser;
import net.minecraft.nbt.NbtElement;
import org.javacord.api.entity.message.Message;

import java.util.Optional;

public class MessageValue extends Value {

    private final Message message;

    public MessageValue(Message message) {
        this.message = message;
    }

    public static Value of(Message message) {
        if(message == null) return Value.NULL;
        return new MessageValue(message);
    }
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static Value of(Optional<Message> optionalMessage){
        return of(ValueUtil.unpackOptional(optionalMessage));
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "content" -> StringValue.of(message.getContent());
            case "readable_content" -> StringValue.of(EmojiParser.parseToAliases(message.getReadableContent())); //not all user mentions will be parsed, if they are not cached
            case "id" -> StringValue.of(message.getIdAsString());
            case "channel" -> new ChannelValue(message.getChannel());
            case "user" -> UserValue.of(message.getUserAuthor());
            case "server" -> ServerValue.of(message.getServer());
            case "nonce" -> ValueUtil.ofOptionalString(message.getNonce());
            default -> Value.NULL;
        };
    }


    @Override
    public Value in(Value value1) {
        return getProperty(value1.getString());
    }

    @Override
    public String getTypeString() {
        return "dc_message";
    }

    @Override
    public String getString() {
        return message.toString();
    }

    @Override
    public boolean getBoolean() {
        return true;
    }

    @Override
    public NbtElement toTag(boolean b) {
        return null;
    }

	public Message getMessage() {
		return message;
	}
}
