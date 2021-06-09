package Discarpet.script.functions;

import Discarpet.script.ScriptFuture;
import Discarpet.script.values.ChannelValue;
import Discarpet.script.values.EmbedBuilderValue;
import Discarpet.script.values.EmojiValue;
import Discarpet.script.values.MessageValue;
import carpet.script.Expression;
import carpet.script.annotation.ScarpetFunction;
import carpet.script.argument.FunctionArgument;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.entity.message.Message;

import java.util.concurrent.CompletableFuture;

import static Discarpet.Discarpet.scarpetException;

public class Sending {
	@ScarpetFunction
	public boolean dc_react(Message message, Value emojiValue) {
		if (!(emojiValue instanceof EmojiValue || emojiValue instanceof StringValue)) scarpetException("dc_react","emoji",1);
		if (!message.canYouAddNewReactions()) return false;
		
		if(emojiValue instanceof EmojiValue) {
            message.addReaction(((EmojiValue) emojiValue).emoji);
        } else {
            message.addReaction(emojiValue.getString());
        }
		
		return true;
	}
	
    public static void apply(Expression expr) {
        expr.addContextFunction("dc_send_message", -1, (c, t, lv) -> {
            if(!(lv.size()==2 || lv.size()==3)) throw new InternalExpressionException("'dc_send_message' requires two or tree arguments");

            Value channelValue = lv.get(0);
            Value messageValue = lv.get(1);

            if (!(channelValue instanceof ChannelValue)) scarpetException("dc_send_message","channel",0);

            CompletableFuture<Message> cf;
            if(messageValue instanceof EmbedBuilderValue) {
                cf = ((ChannelValue) channelValue).getChannel().sendMessage(((EmbedBuilderValue) messageValue).embedBuilder);
            } else {
                cf = ((ChannelValue) channelValue).getChannel().sendMessage(messageValue.getString());
            }

            if(lv.size()==3) {
                FunctionArgument functionArgument = FunctionArgument.findIn(c, expr.module, lv, 2, false, false);
                ScriptFuture future = new ScriptFuture(c, functionArgument.function);
                cf.thenAccept(message -> {
                    future.execute(new MessageValue(message));
                });
            }

            return Value.TRUE;
        });
    }
}
