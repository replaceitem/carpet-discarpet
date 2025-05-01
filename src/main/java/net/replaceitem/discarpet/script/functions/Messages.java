package net.replaceitem.discarpet.script.functions;

import carpet.script.annotation.ScarpetFunction;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.Value;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.WebhookClient;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.requests.restaction.WebhookMessageCreateAction;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.replaceitem.discarpet.script.parsable.Parser;
import net.replaceitem.discarpet.script.parsable.parsables.MessageContentParsable;
import net.replaceitem.discarpet.script.parsable.parsables.webhooks.WebhookMessageProfileParsable;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.common.MessageableValue;

import java.util.Optional;

@SuppressWarnings("unused")
public class Messages {
    @ScarpetFunction
    public Message dc_send_message(Value target, Value messageContent) {
        Optional<MessageableValue.MessageConsumer> messageConsumer = target instanceof MessageableValue<?> messageableValue ? messageableValue.getMessageConsumer() : Optional.empty();
        if(messageConsumer.isEmpty()) throw new InternalExpressionException("'dc_send_message' expected a messageable channel, user or incoming webhook as the first parameter. Got: " + target.getTypeString());
        MessageCreateBuilder builder = new MessageCreateBuilder();
        Parser.parseType(messageContent, MessageContentParsable.class).apply(builder);
        return ValueUtil.awaitRest(messageConsumer.get().send(builder.build()),"Error sending message");
    }
    
    @ScarpetFunction
    public Message dc_send_webhook(WebhookClient<Message> webhook, Value messageContent, Value webhookProfile) {
        MessageCreateBuilder builder = new MessageCreateBuilder();
        WebhookMessageCreateAction<Message> messageWebhookMessageCreateAction = webhook.sendMessage(builder.build());
        Parser.parseType(messageContent, MessageContentParsable.class).apply(builder);
        Parser.parseType(webhookProfile, WebhookMessageProfileParsable.class).apply(messageWebhookMessageCreateAction);
        return ValueUtil.awaitRest(messageWebhookMessageCreateAction,"Error sending message");
    }

	@ScarpetFunction
	public void dc_react(Message message, Value emojiValue) {
        // TODO split to dc_add_reaction(msg, emoji) and dc_remove_reaction(msg, emoji?, user?)
        Emoji emoji = Parser.parseType(emojiValue, Emoji.class);
        ValueUtil.awaitRest(message.addReaction(emoji), "Error reacting to message");
	}
}
