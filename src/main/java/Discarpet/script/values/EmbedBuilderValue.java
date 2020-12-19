package Discarpet.script.values;

import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.minecraft.nbt.Tag;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class EmbedBuilderValue extends Value {

    public EmbedBuilder embedBuilder;

    public EmbedBuilderValue() {
        this.embedBuilder = new EmbedBuilder();
    }

    public EmbedBuilderValue(EmbedBuilder embedBuilder) {
        this.embedBuilder = embedBuilder;
    }

    public Value getProperty(String property) {
        return Value.NULL;
    }


    @Override
    public Value in(Value value1) {
        return getProperty(value1.getString());
    }

    @Override
    public String getTypeString() {
        return "dc_embed_builder";
    }

    @Override
    public String getString() {
        return "EmbedBuilder";
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
