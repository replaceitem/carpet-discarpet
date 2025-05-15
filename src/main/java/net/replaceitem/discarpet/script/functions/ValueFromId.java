package net.replaceitem.discarpet.script.functions;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.sticker.Sticker;
import net.replaceitem.discarpet.config.Bot;
import carpet.script.Context;
import carpet.script.annotation.ScarpetFunction;
import net.replaceitem.discarpet.Discarpet;
import net.replaceitem.discarpet.script.util.ValueUtil;


@SuppressWarnings("unused")
public class ValueFromId {
	@ScarpetFunction
	public Channel dc_channel_from_id(Context ctx, String channelId) {
		Bot bot = Discarpet.getBotInContext(ctx,"dc_channel_from_id");
        return bot.getJda().getChannelById(Channel.class, channelId);
	}
	
	@ScarpetFunction
	public Guild dc_server_from_id(Context ctx, String serverId) {
		Bot bot = Discarpet.getBotInContext(ctx,"dc_server_from_id");
        return bot.getJda().getGuildById(serverId);
	}
	
	@ScarpetFunction
	public Emoji dc_emoji_from_id(Context ctx, String emojiId) {
		Bot bot = Discarpet.getBotInContext(ctx,"dc_emoji_from_id");
        return bot.getJda().getEmojiById(emojiId);
	}

	@ScarpetFunction
	public Sticker dc_sticker_from_id(Context ctx, String stickerId) {
		Bot bot = Discarpet.getBotInContext(ctx,"dc_sticker_from_id");
		return ValueUtil.awaitRest(bot.getJda().retrieveSticker(Sticker.fromId(stickerId)), "Could not retrieve sticker");
	}

    @ScarpetFunction
    public Role dc_role_from_id(Context ctx, String roleId) {
        Bot bot = Discarpet.getBotInContext(ctx,"dc_role_from_id");
        return bot.getJda().getRoleById(roleId);
    }

	@ScarpetFunction
	public User dc_user_from_id(Context ctx, String userId) {
		Bot bot = Discarpet.getBotInContext(ctx,"dc_user_from_id");
		return ValueUtil.awaitRest(bot.getJda().retrieveUserById(userId), "Error getting user from id");
	}
	
	@ScarpetFunction
	public Message dc_message_from_id(Context ctx, String messageId, Channel channel) {
		Bot bot = Discarpet.getBotInContext(ctx,"dc_message_from_id");
		if(!(channel instanceof MessageChannel textChannel)) return null;
		return ValueUtil.awaitRest(textChannel.retrieveMessageById(messageId), "Error getting message from id");
	}

	@ScarpetFunction
	public WebhookClient<Message> dc_webhook_from_id(Context ctx, String webhookId, String token) {
		Bot bot = Discarpet.getBotInContext(ctx,"dc_webhook_from_id");
		return WebhookClient.createClient(bot.getJda(), webhookId, token);
	}

	@ScarpetFunction
	public WebhookClient<Message> dc_webhook_from_url(Context ctx, String webhookUrl) {
		Bot bot = Discarpet.getBotInContext(ctx,"dc_webhook_from_url");
		return WebhookClient.createClient(bot.getJda(), webhookUrl);
	}

	@ScarpetFunction
	public Member dc_member_from_user(Context ctx, User user, Guild server) {
		Bot bot = Discarpet.getBotInContext(ctx,"dc_webhook_from_url");
		return ValueUtil.awaitRest(server.retrieveMember(user), "Error getting member from user and server");
	}
}
