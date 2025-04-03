package net.replaceitem.discarpet.script.functions;

import net.replaceitem.discarpet.config.Bot;
import carpet.script.Context;
import carpet.script.annotation.ScarpetFunction;
import net.replaceitem.discarpet.Discarpet;
import net.replaceitem.discarpet.script.util.ValueUtil;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.sticker.Sticker;
import org.javacord.api.entity.user.User;
import org.javacord.api.entity.webhook.Webhook;


@SuppressWarnings("unused")
public class ValueFromId {
	@ScarpetFunction
	public Channel dc_channel_from_id(Context ctx, String channelId) {
		Bot bot = Discarpet.getBotInContext(ctx,"dc_channel_from_id");
        return bot.getApi().getChannelById(channelId).orElse(null);
	}
	
	@ScarpetFunction
	public Server dc_server_from_id(Context ctx, String serverId) {
		Bot bot = Discarpet.getBotInContext(ctx,"dc_server_from_id");
        return bot.getApi().getServerById(serverId).orElse(null);
	}
	
	@ScarpetFunction
	public Emoji dc_emoji_from_id(Context ctx, String emojiId) {
		Bot bot = Discarpet.getBotInContext(ctx,"dc_emoji_from_id");
        return bot.getApi().getCustomEmojiById(emojiId).orElse(null);
	}

	@ScarpetFunction
	public Sticker dc_sticker_from_id(Context ctx, String stickerId) {
		Bot bot = Discarpet.getBotInContext(ctx,"dc_sticker_from_id");
		return bot.getApi().getStickerById(stickerId).orElse(null);
	}

    @ScarpetFunction
    public Role dc_role_from_id(Context ctx, String roleId) {
        Bot bot = Discarpet.getBotInContext(ctx,"dc_role_from_id");
        return bot.getApi().getRoleById(roleId).orElse(null);
    }

	@ScarpetFunction
	public User dc_user_from_id(Context ctx, String userId) {
		Bot bot = Discarpet.getBotInContext(ctx,"dc_user_from_id");
		return ValueUtil.awaitFuture(bot.getApi().getUserById(userId), "Error getting user from id");
	}
	
	@ScarpetFunction
	public Message dc_message_from_id(Context ctx, String messageId, Channel channel) {
		Bot bot = Discarpet.getBotInContext(ctx,"dc_message_from_id");
		if(!(channel instanceof TextChannel textChannel)) return null;
		return ValueUtil.awaitFuture(bot.getApi().getMessageById(messageId, textChannel), "Error getting message from id");
	}

	@ScarpetFunction
	public Webhook dc_webhook_from_id(Context ctx, String webhookId, String token) {
		Bot bot = Discarpet.getBotInContext(ctx,"dc_webhook_from_id");
		return ValueUtil.awaitFuture(bot.getApi().getIncomingWebhookByIdAndToken(webhookId, token), "Error getting webhook from id");
	}

	@ScarpetFunction
	public Webhook dc_webhook_from_url(Context ctx, String webhookUrl) {
		Bot bot = Discarpet.getBotInContext(ctx,"dc_webhook_from_url");
		return ValueUtil.awaitFuture(bot.getApi().getIncomingWebhookByUrl(webhookUrl), "Error getting webhook from url");
	}
}
