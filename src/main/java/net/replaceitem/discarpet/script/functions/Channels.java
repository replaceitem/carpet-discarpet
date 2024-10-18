package net.replaceitem.discarpet.script.functions;

import net.replaceitem.discarpet.script.parsable.Parser;
import net.replaceitem.discarpet.script.parsable.parsables.webhooks.WebhookProfileParsable;
import net.replaceitem.discarpet.script.parsable.parsables.webhooks.WebhookProfileUpdaterParsable;
import net.replaceitem.discarpet.script.util.ValueUtil;
import carpet.script.annotation.ScarpetFunction;
import carpet.script.value.Value;
import net.replaceitem.discarpet.script.exception.DiscordThrowables;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.ServerThreadChannel;
import org.javacord.api.entity.channel.ServerThreadChannelBuilder;
import org.javacord.api.entity.webhook.Webhook;
import org.javacord.api.entity.webhook.WebhookBuilder;
import org.javacord.api.entity.webhook.WebhookUpdater;

import java.util.concurrent.CompletableFuture;

import static net.replaceitem.discarpet.script.exception.DiscordThrowables.Codes.*;

@SuppressWarnings("unused")
public class Channels {
	
	@ScarpetFunction
	public void dc_set_channel_topic(Channel channel, String str) {
		if(!(channel instanceof ServerTextChannel textChannel)) throw DiscordThrowables.genericCode(CANNOT_EXECUTE_ACTION_ON_CHANNEL_TYPE);
        ValueUtil.awaitFuture(textChannel.updateTopic(str),"Error updating channel topic");
	}

	@ScarpetFunction
	public Webhook dc_create_webhook(Channel channel, Value webhookBuilder) {
		if(!(channel instanceof ServerTextChannel textChannel)) return null;
		WebhookBuilder builder = new WebhookBuilder(textChannel);
		Parser.parseType(webhookBuilder, WebhookProfileParsable.class).apply(builder);
		return ValueUtil.awaitFuture(builder.create(),"Error creating webhook");
	}

	@ScarpetFunction
	public Webhook dc_update_webhook(Webhook webhook, Value webhookBuilder) {
		WebhookUpdater updater = webhook.createUpdater();
		Parser.parseType(webhookBuilder, WebhookProfileUpdaterParsable.class).apply(updater);
		return ValueUtil.awaitFuture(updater.update(),"Error updating webhook");
	}
	
	@ScarpetFunction
	public Channel dc_create_thread(Value thread) {
		CompletableFuture<ServerThreadChannel> serverThreadChannelCompletableFuture = Parser.parseType(thread, ServerThreadChannelBuilder.class).create();
		return ValueUtil.awaitFuture(serverThreadChannelCompletableFuture, "Error creating thread channel");

	}
}
