package net.replaceitem.discarpet.script.functions;

import net.replaceitem.discarpet.script.parsable.Parser;
import net.replaceitem.discarpet.script.parsable.parsables.MessageContentParsable;
import net.replaceitem.discarpet.script.parsable.parsables.webhooks.WebhookMessageProfileParsable;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.util.content.MessageContentApplier;
import net.replaceitem.discarpet.script.util.content.WebhookMessageContentApplier;
import net.replaceitem.discarpet.script.values.common.MessageableValue;
import carpet.script.annotation.ScarpetFunction;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.Value;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.Messageable;
import org.javacord.api.entity.message.WebhookMessageBuilder;
import org.javacord.api.entity.webhook.IncomingWebhook;
import org.javacord.api.entity.webhook.Webhook;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class Messages {
    @ScarpetFunction
    public Message dc_send_message(Value target, Value messageContent) {
        MessageBuilder messageBuilder = new MessageBuilder();
        if(!(target instanceof MessageableValue<?> messageableValue) || !messageableValue.isMessageable()) throw new InternalExpressionException("'dc_send_message' expected a messageable channel, user or incoming webhook as the first parameter");
        Parser.parseType(messageContent, MessageContentParsable.class).apply(new MessageContentApplier(messageBuilder));
        Messageable messageable = messageableValue.getMessageable();
        CompletableFuture<Message> cf = messageBuilder.send(messageable);
        return ValueUtil.awaitFuture(cf,"Error sending message");
    }
    
    @ScarpetFunction
    public Message dc_send_webhook(Webhook webhook, Value messageContent, Value webhookProfile) {
        Optional<IncomingWebhook> optionalIncomingWebhook = webhook.asIncomingWebhook();
        if(optionalIncomingWebhook.isEmpty()) throw new InternalExpressionException("'dc_send_webhook' expected an incoming webhook as the first parameter");
        WebhookMessageBuilder webhookMessageBuilder = new WebhookMessageBuilder();
        Parser.parseType(messageContent, MessageContentParsable.class).apply(new WebhookMessageContentApplier(webhookMessageBuilder));
        Parser.parseType(webhookProfile, WebhookMessageProfileParsable.class).apply(webhookMessageBuilder);
        CompletableFuture<Message> cf = webhookMessageBuilder.send(optionalIncomingWebhook.get());
        return ValueUtil.awaitFuture(cf,"Error sending message");
    }

	@ScarpetFunction
	public void dc_react(Message message, Value emojiValue) {
        CompletableFuture<Void> cf = message.addReaction(ValueUtil.emojiFromValue(emojiValue));
        ValueUtil.awaitFuture(cf, "Error reacting to message");
	}
}
