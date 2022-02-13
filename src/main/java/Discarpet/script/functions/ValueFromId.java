package Discarpet.script.functions;

import Discarpet.config.Bot;
import Discarpet.script.values.ChannelValue;
import Discarpet.script.values.EmojiValue;
import Discarpet.script.values.RoleValue;
import Discarpet.script.values.ServerValue;
import carpet.script.Context;
import carpet.script.annotation.ScarpetFunction;
import carpet.script.value.Value;


import static Discarpet.Discarpet.*;

public class ValueFromId {
	@ScarpetFunction
	public Value dc_channel_from_id(Context ctx, String channelId) {
		Bot bot = getBotInContext(ctx,"dc_channel_from_id");
        return ChannelValue.of(bot.api.getChannelById(channelId));
	}
	
	@ScarpetFunction
	public Value dc_server_from_id(Context ctx, String serverId) {
		Bot bot = getBotInContext(ctx,"dc_server_from_id");
        return ServerValue.of(bot.api.getServerById(serverId));
	}
	
	@ScarpetFunction
	public Value dc_emoji_from_id(Context ctx, String emojiId) {
		Bot bot = getBotInContext(ctx,"dc_emoji_from_id");
        return EmojiValue.of(bot.api.getCustomEmojiById(emojiId));
	}

    @ScarpetFunction
    public Value dc_role_from_id(Context ctx, String roleId) {
        Bot bot = getBotInContext(ctx,"dc_role_from_id");
        return RoleValue.of(bot.api.getRoleById(roleId));
    }
}
