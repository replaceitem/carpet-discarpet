package net.replaceitem.discarpet.script.functions;

import carpet.script.Context;
import carpet.script.annotation.ScarpetFunction;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.Value;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.WebhookClient;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.WebhookMessageCreateAction;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.replaceitem.discarpet.script.parsable.Parser;
import net.replaceitem.discarpet.script.parsable.parsables.MessageContentParsable;
import net.replaceitem.discarpet.script.parsable.parsables.webhooks.WebhookMessageProfileParsable;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.common.MessageableValue;

import java.util.Optional;

@SuppressWarnings({"unused", "OptionalUsedAsFieldOrParameterType"})
public class Messages {
    @ScarpetFunction
    public Message dc_send_message(Context context, Value target, Value messageContent) {
        Optional<MessageableValue.MessageConsumer> optionalMessageConsumer = target instanceof MessageableValue<?> messageableValue ? messageableValue.getMessageConsumer() : Optional.empty();
        MessageableValue.MessageConsumer messageConsumer = optionalMessageConsumer.orElseThrow(
                () -> new InternalExpressionException("'dc_send_message' expected a messageable channel, user or incoming webhook as the first parameter. Got: " + target.getTypeString())
        );
        MessageContentParsable messageContentParsable = Parser.parseType(context, messageContent, MessageContentParsable.class);
        RestAction<Message> action = messageContentParsable.apply(new MessageCreateBuilder(), MessageCreateBuilder::build, messageConsumer::send);
        return ValueUtil.awaitRest(action,"Error sending message");
    }
    
    @ScarpetFunction
    public Message dc_send_webhook(Context context, WebhookClient<Message> webhook, Value messageContent, Value webhookProfile) {
        MessageContentParsable messageContentParsable = Parser.parseType(context, messageContent, MessageContentParsable.class);
        WebhookMessageCreateAction<Message> action = messageContentParsable.apply(new MessageCreateBuilder(), MessageCreateBuilder::build, webhook::sendMessage);
        Parser.parseType(context, webhookProfile, WebhookMessageProfileParsable.class).apply(action);
        return ValueUtil.awaitRest(action,"Error sending message");
    }

	@ScarpetFunction
	public void dc_add_reaction(Context context, Message message, Value emojiValue) {
        Emoji emoji = Parser.parseType(context, emojiValue, Emoji.class);
        ValueUtil.awaitRest(message.addReaction(emoji), "Error adding reaction to message");
	}

	@ScarpetFunction(maxParams = 3)
	public void dc_remove_reaction(Context context, Message message, Optional<Value> emojiValue, Optional<User> user) {
        if (emojiValue.isEmpty()) {
            ValueUtil.awaitRest(message.clearReactions(), "Error removing all reactions from message");
            return;
        }
        Emoji emoji = Parser.parseType(context, emojiValue.get(), Emoji.class);
        if(user.isEmpty()) {
            ValueUtil.awaitRest(message.removeReaction(emoji), "Error removing reactions from message");
            return;
        }
        ValueUtil.awaitRest(message.removeReaction(emoji, user.get()), "Error removing reactions from message for a user");
    }
}
