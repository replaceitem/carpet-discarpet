package Discarpet.script.functions;

import Discarpet.Bot;
import Discarpet.script.values.ChannelValue;
import Discarpet.script.values.EmojiValue;
import Discarpet.script.values.ServerValue;
import carpet.script.Context;
import carpet.script.annotation.ScarpetFunction;
import carpet.script.value.ListValue;
import carpet.script.value.Value;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.KnownCustomEmoji;
import org.javacord.api.entity.server.Server;

import java.util.Collection;
import java.util.Optional;

import static Discarpet.Discarpet.*;

public class ValueFromId {
	@ScarpetFunction
	public Value dc_channel_from_id(Context ctx, String channelId) {
		Bot bot = getBotInContext(ctx);
        if (bot == null) scarpetNoBotException("dc_channel_from_id");

        Optional<Channel> optionalChannel = bot.api.getChannelById(channelId);
        if (optionalChannel.isPresent() && optionalChannel.get() instanceof TextChannel) {
            return new ChannelValue(optionalChannel.get());
        } else {
            return Value.NULL;
        }
	}
	
	@ScarpetFunction
	public Value dc_server_from_id(Context ctx, String serverId) {
		Bot bot = getBotInContext(ctx);
        if (bot == null) scarpetNoBotException("dc_server_from_id");

        Optional<Server> optionalServer = bot.api.getServerById(serverId);
        if(optionalServer.isPresent()) {
            return new ServerValue(optionalServer.get());
        } else {
            return Value.NULL;
        }
	}
	
	@ScarpetFunction
	public Value dc_emoji_from_id(Context ctx, Server server, String emojiId) {
		Bot b = getBotInContext(ctx);
        if (b == null) scarpetNoBotException("dc_emoji_from_id");
        Optional<KnownCustomEmoji> optionalEmoji = server.getCustomEmojiById(emojiId);

        if(optionalEmoji.isPresent()) return new EmojiValue(optionalEmoji.get());

        Collection<KnownCustomEmoji> emojis = server.getCustomEmojisByName(emojiId);

        return ListValue.wrap(emojis.stream().map(EmojiValue::new));
	}
}
