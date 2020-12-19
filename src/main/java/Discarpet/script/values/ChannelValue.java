package Discarpet.script.values;

import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.minecraft.nbt.Tag;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;

public class ChannelValue extends Value {

    public ServerTextChannel channel;

    public ChannelValue(TextChannel channel) {
        this.channel = (ServerTextChannel) channel;
    }

    public Value getProperty(String property) {
        switch (property) {
            case "name":
                return StringValue.of(channel.getName());
            case "topic":
                return StringValue.of(channel.getTopic());
            case "id":
                return StringValue.of(channel.getIdAsString());
            case "mention_tag":
                return StringValue.of(channel.getMentionTag());
            case "server":
                return new ServerValue(channel.getServer());
            default:
                return Value.NULL;
        }
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
        return channel.getName();
    }

    @Override
    public boolean getBoolean() {
        return false;
    }

    @Override
    public Tag toTag(boolean b) {
        return null;
    }
}
