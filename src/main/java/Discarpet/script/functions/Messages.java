package Discarpet.script.functions;

import Discarpet.script.parsable.Parser;
import Discarpet.script.parsable.parsables.MessageContentParsable;
import Discarpet.script.parsable.parsables.WebhookMessageProfileParsable;
import Discarpet.script.util.ValueUtil;
import Discarpet.script.util.content.MessageContentApplier;
import Discarpet.script.util.content.WebhookMessageContentApplier;
import Discarpet.script.values.common.MessageableValue;
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
        Parser.parseType(messageContent, MessageContentParsable.class).apply(new MessageContentApplier(messageBuilder));
        if(target instanceof MessageableValue messageableValue && messageableValue.getMessageable() != null) {
            Messageable messageable = messageableValue.getMessageable();
            CompletableFuture<Message> cf = messageBuilder.send(messageable);
            return ValueUtil.awaitFuture(cf,"Error sending message");
        } else throw new InternalExpressionException("'dc_send_message' expected a text channel, user or incoming webhook as the first parameter");
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
	public boolean dc_react(Message message, Value emojiValue) {
		if (!message.canYouAddNewReactions()) return false;

        CompletableFuture<Void> cf;
        cf = message.addReaction(ValueUtil.emojiFromValue(emojiValue));

        return ValueUtil.awaitFutureBoolean(cf, "Error reacting to message");
	}
}
