package Discarpet.script.values;

import carpet.script.value.Value;
import net.minecraft.nbt.NbtElement;
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

    public EmbedBuilder getEmbedBuilder() {
        return embedBuilder;
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
        return true;
    }

    @Override
    public NbtElement toTag(boolean b) {
        return null;
    }
}
