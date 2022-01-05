package Discarpet.script.functions;

import Discarpet.script.util.MessageContentParser;
import Discarpet.script.util.ValueUtil;
import Discarpet.script.values.ChannelValue;
import Discarpet.script.values.EmojiValue;
import carpet.script.annotation.ScarpetFunction;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.Value;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.Messageable;

import java.util.concurrent.CompletableFuture;

public class Messages {
    @ScarpetFunction
    public Message dc_send_message(Value target, Value messageContent) {
        MessageBuilder messageBuilder = MessageContentParser.parseMessageContent(messageContent);

        if(target instanceof ChannelValue channel && channel.getChannel() instanceof Messageable messageable) {
            CompletableFuture<Message> cf = messageBuilder.send(messageable);
            return ValueUtil.awaitFuture(cf,"Error sending message");
        } else throw new InternalExpressionException("'dc_send_message' expected a text channel or user as the first parameter");

    }

    @ScarpetFunction
    public boolean dc_delete_message(Message message) {
        if(message.canYouDelete()) return false;
        return ValueUtil.awaitFutureBoolean(message.delete(),"Error deleting message");
    }

	@ScarpetFunction
	public boolean dc_react(Message message, Value emojiValue) {
		if (!message.canYouAddNewReactions()) return false;

        CompletableFuture<Void> cf;
		if(emojiValue instanceof EmojiValue) {
            cf = message.addReaction(((EmojiValue) emojiValue).emoji);
        } else {
            cf = message.addReaction(emojiValue.getString());
        }

        return ValueUtil.awaitFutureBoolean(cf, "Error reacting to message");
	}
}
