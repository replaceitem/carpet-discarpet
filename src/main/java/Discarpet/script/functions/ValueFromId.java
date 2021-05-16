package Discarpet.script.functions;

import Discarpet.Bot;
import Discarpet.script.values.ChannelValue;
import Discarpet.script.values.EmojiValue;
import Discarpet.script.values.ServerValue;
import carpet.script.Expression;
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
    public static void apply(Expression expr) {

        expr.addContextFunction("dc_channel_from_id", 1, (c, t, lv) -> {
            Bot b = getBotInContext(c);
            if(b==null) scarpetNoBotException("dc_channel_from_id");

            Optional<Channel> optionalChannel = b.api.getChannelById(lv.get(0).getString());
            if(optionalChannel.isPresent() && optionalChannel.get() instanceof TextChannel) {
                return new ChannelValue((TextChannel) optionalChannel.get());
            } else {
                return Value.FALSE;
            }
        });

        expr.addContextFunction("dc_server_from_id", 1, (c, t, lv) -> {
            Bot b = getBotInContext(c);
            if(b==null) scarpetNoBotException("dc_server_from_id");

            Optional<Server> optionalServer = b.api.getServerById(lv.get(0).getString());
            if(optionalServer.isPresent() && optionalServer.get() instanceof Server) {
                return new ServerValue(optionalServer.get());
            } else {
                return Value.FALSE;
            }
        });

        expr.addContextFunction("dc_emoji_from_id", 2, (c, t, lv) -> {
            Bot b = getBotInContext(c);
            if(b==null) scarpetNoBotException("dc_emoji_from_id");

            Value serverValue = lv.get(0);
            Value emojiValue = lv.get(1);
            if(!(serverValue instanceof ServerValue)) scarpetException("dc_get_display_name","server",0);

            Optional<KnownCustomEmoji> optionalEmoji = (((ServerValue) serverValue).server.getCustomEmojiById(emojiValue.getString()));

            if(optionalEmoji.isPresent()) return new EmojiValue(optionalEmoji.get());

            Collection<KnownCustomEmoji> emojis = (((ServerValue) serverValue).server.getCustomEmojisByName(emojiValue.getString()));

            return ListValue.wrap(emojis.stream().map(EmojiValue::new));
        });
    }
}
