package Discarpet.script.functions;

import Discarpet.Bot;
import Discarpet.script.values.ChannelValue;
import Discarpet.script.values.EmojiValue;
import Discarpet.script.values.ServerValue;
import carpet.script.Expression;
import carpet.script.value.Value;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.KnownCustomEmoji;
import org.javacord.api.entity.server.Server;

import java.util.Optional;

import static Discarpet.Discarpet.*;
import static carpet.script.LazyValue.FALSE;
import static carpet.script.LazyValue.NULL;

public class ValueFromId {
    public static void apply(Expression expr) {

        expr.addLazyFunction("dc_channel_from_id", 1, (c, t, lv) -> {
            Bot b = getBotInContext(c);
            if(b==null) scarpetNoBotException("dc_channel_from_id");

            Optional<Channel> optionalChannel = b.api.getChannelById(lv.get(0).evalValue(c).getString());
            if(optionalChannel.isPresent() && optionalChannel.get() instanceof TextChannel) {
                return (cc, tt) -> new ChannelValue((TextChannel) optionalChannel.get());
            } else {
                return FALSE;
            }
        });

        expr.addLazyFunction("dc_server_from_id", 1, (c, t, lv) -> {
            Bot b = getBotInContext(c);
            if(b==null) scarpetNoBotException("dc_server_from_id");

            Optional<Server> optionalServer = b.api.getServerById(lv.get(0).evalValue(c).getString());
            if(optionalServer.isPresent() && optionalServer.get() instanceof Server) {
                return (cc, tt) -> new ServerValue(optionalServer.get());
            } else {
                return FALSE;
            }
        });

        expr.addLazyFunction("dc_emoji_from_id", 2, (c, t, lv) -> {
            Bot b = getBotInContext(c);
            if(b==null) scarpetNoBotException("dc_emoji_from_id");

            Value serverValue = lv.get(0).evalValue(c);
            Value emojiValue = lv.get(1).evalValue(c);
            if(!(serverValue instanceof ServerValue)) scarpetException("dc_get_display_name","server",0);

            Optional<KnownCustomEmoji> optionalEmoji = (((ServerValue) serverValue).server.getCustomEmojiById(emojiValue.getString()));

            if(optionalEmoji.isPresent()) {
                return (cc, tt) -> new EmojiValue(optionalEmoji.get());
            } else {
                return NULL;
            }
        });
    }
}
