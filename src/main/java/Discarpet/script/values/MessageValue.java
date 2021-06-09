package Discarpet.script.values;

import carpet.script.value.StringValue;
import carpet.script.value.Value;
import com.vdurmont.emoji.EmojiParser;
import net.minecraft.nbt.Tag;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.Optional;

public class MessageValue extends Value {

    private final Message message;

    public MessageValue(Message message) {
        this.message = message;
    }

    public Value getProperty(String property) {
        switch (property) {
            case "content":
                return StringValue.of(message.getContent());
            case "readable_content":
                //not all user mentions will be parsed, if they are not cached
                return StringValue.of(EmojiParser.parseToAliases(message.getReadableContent()));
            case "id":
                return StringValue.of(message.getIdAsString());
            case "channel":
                return new ChannelValue(message.getChannel());
            case "user":
                Optional<User> optionalUser = message.getUserAuthor();
                if(optionalUser.isPresent()) {
                    return new UserValue(optionalUser.get());
                } else {
                    return Value.NULL;
                }
            case "server":
                Optional<Server> optionalServer = message.getServer();
                if(optionalServer.isPresent()) {
                    return new ServerValue(optionalServer.get());
                } else {
                    return Value.NULL;
                }
            case "delete":
                if(message.canYouDelete()) {
                    message.delete();
                    return Value.TRUE;
                } else {
                    return Value.FALSE;
                }
            default:
                return Value.NULL;
        }
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
        return message.getIdAsString();
    }

    @Override
    public boolean getBoolean() {
        return false;
    }

    @Override
    public Tag toTag(boolean b) {
        return null;
    }

	public Message getMessage() {
		return message;
	}
}
