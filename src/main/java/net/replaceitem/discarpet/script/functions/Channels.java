package net.replaceitem.discarpet.script.functions;

import carpet.script.Context;
import carpet.script.annotation.ScarpetFunction;
import carpet.script.exception.InternalExpressionException;
import carpet.script.exception.ThrowStatement;
import carpet.script.value.Value;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.entities.WebhookClient;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.managers.WebhookManager;
import net.dv8tion.jda.api.managers.channel.ChannelManager;
import net.dv8tion.jda.api.requests.ErrorResponse;
import net.dv8tion.jda.api.requests.restaction.ThreadChannelAction;
import net.dv8tion.jda.api.requests.restaction.WebhookAction;
import net.dv8tion.jda.internal.entities.channel.mixin.attribute.IThreadContainerMixin;
import net.dv8tion.jda.internal.entities.channel.mixin.attribute.IWebhookContainerMixin;
import net.replaceitem.discarpet.script.exception.DiscordThrowables;
import net.replaceitem.discarpet.script.schema.Parser;
import net.replaceitem.discarpet.script.schema.schemas.ChannelUpdaterSchema;
import net.replaceitem.discarpet.script.schema.schemas.ThreadSchema;
import net.replaceitem.discarpet.script.schema.schemas.webhooks.WebhookProfileSchema;
import net.replaceitem.discarpet.script.schema.schemas.webhooks.WebhookProfileUpdaterSchema;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.ChannelValue;
import net.replaceitem.discarpet.script.values.MessageValue;

@SuppressWarnings("unused")
public class Channels {
	@ScarpetFunction
	public void dc_update_channel(Context context, Channel channel, Value channelUpdater) {
        try {
            ChannelUpdaterSchema channelUpdaterSchema = Parser.parseType(context, channelUpdater, ChannelUpdaterSchema.class);
            if (!(channel instanceof GuildChannel guildChannel)) throw DiscordThrowables.genericMessage("Can only update server channels");
            ChannelManager<?, ?> manager = guildChannel.getManager();
            channelUpdaterSchema.apply(manager);
            ValueUtil.awaitRest(manager, "Error updating channel");
        } catch (InsufficientPermissionException e) {
            throw DiscordThrowables.convert(e);
        }
    }

	@ScarpetFunction
	public WebhookClient<?> dc_create_webhook(Context context, Channel channel, Value webhookBuilder) {
		if(!(channel instanceof IWebhookContainerMixin<?> webhookContainer)) return null;
		// name is dummy, will be set by the schema anyway
		WebhookAction webhookAction = webhookContainer.createWebhook("Webhook");
		Parser.parseType(context, webhookBuilder, WebhookProfileSchema.class).apply(webhookAction);
		return ValueUtil.awaitRest(webhookAction,"Error creating webhook");
	}

	@ScarpetFunction
	public void dc_update_webhook(Context context, WebhookClient<Message> webhookClient, Value webhookBuilder) {
        try {
            if(!(webhookClient instanceof Webhook webhook)) throw new ThrowStatement("Cannot update webhook not managed by this bot", DiscordThrowables.DISCORD_EXCEPTION);
            WebhookManager manager = webhook.getManager();
            Parser.parseType(context, webhookBuilder, WebhookProfileUpdaterSchema.class).apply(manager);
            ValueUtil.awaitRest(manager,"Error updating webhook");
        } catch (InsufficientPermissionException e) {
            throw DiscordThrowables.convert(e);
        }
    }
	
	@ScarpetFunction
	public Channel dc_create_thread(Context context, Value channelOrMessage, Value threadValue) {
		if(channelOrMessage instanceof ChannelValue channelValue) {
            if (!(channelValue.getDelegate() instanceof IThreadContainerMixin<?> threadContainerMixin)) {
                throw DiscordThrowables.genericCode(ErrorResponse.INVALID_CHANNEL_TYPE);
            }
            // Temporary name, will be set in schema.apply
			ThreadChannelAction action = Parser.parseType(context, threadValue, ThreadSchema.class).apply(threadContainerMixin);
			return ValueUtil.awaitRest(action, "Error creating thread channel");
        } else if(channelOrMessage instanceof MessageValue messageValue) {
			ThreadChannelAction action = Parser.parseType(context, threadValue, ThreadSchema.class).apply(messageValue.getDelegate());
			return ValueUtil.awaitRest(action, "Error creating thread channel");
		} else throw new InternalExpressionException("Expected a message or channel as the first parameter");
	}
}
