package Discarpet.script.functions;

import Discarpet.script.util.MessageContentParser;
import Discarpet.script.util.MiscParser;
import Discarpet.script.util.ValueUtil;
import Discarpet.script.util.content.MessageContentApplier;
import Discarpet.script.util.content.WebhookMessageContentApplier;
import Discarpet.script.values.EmojiValue;
import Discarpet.script.values.MessageableValue;
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
        MessageContentParser.parseMessageContent(new MessageContentApplier(messageBuilder),messageContent);
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
        MessageContentParser.parseMessageContent(new WebhookMessageContentApplier(webhookMessageBuilder),messageContent);
        MiscParser.parseWebhookMessageContentProfile(webhookProfile, webhookMessageBuilder);
        CompletableFuture<Message> cf = webhookMessageBuilder.send(optionalIncomingWebhook.get());
        return ValueUtil.awaitFuture(cf,"Error sending message");
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
