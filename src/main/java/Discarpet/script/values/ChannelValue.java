package Discarpet.script.values;

import Discarpet.script.util.ValueUtil;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.minecraft.nbt.NbtElement;
import org.javacord.api.entity.Mentionable;
import org.javacord.api.entity.Nameable;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.channel.ServerTextChannel;

import java.util.Optional;

public class ChannelValue extends Value {

    private final Channel channel;

    public ChannelValue(Channel channel) {
        this.channel = channel;
    }

    public static Value of(Channel channel) {
        if(channel == null) return Value.NULL;
        return new ChannelValue(channel);
    }
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static Value of(Optional<? extends Channel> optionalChannel){
        return of(ValueUtil.unpackOptional(optionalChannel));
    }

    public Channel getChannel() {
        return channel;
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "name" -> StringValue.of(channel instanceof Nameable nameableChannel ? nameableChannel.getName() : null);
            case "type" -> StringValue.of(channel.getType().toString());
            case "topic" -> StringValue.of(channel instanceof ServerTextChannel serverTextChannel ? serverTextChannel.getTopic() : null);
            case "id" -> StringValue.of(channel.getIdAsString());
            case "mention_tag" -> StringValue.of(channel instanceof Mentionable mentionableChannel ? mentionableChannel.getMentionTag() : null);
            case "server" -> new ServerValue(channel instanceof ServerChannel serverChannel ? serverChannel.getServer() : null);
            default -> Value.NULL;
        };
    }


    @Override
    public Value in(Value value1) {
        return getProperty(value1.getString());
    }

    @Override
    public String getTypeString() {
        return "dc_channel";
    }

    @Override
    public String getString() {
        return channel.toString();
    }

    @Override
    public boolean getBoolean() {
        return true;
    }

    @Override
    public NbtElement toTag(boolean b) {
        return null;
    }
}
