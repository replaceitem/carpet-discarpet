package Discarpet.script.values;

import carpet.script.value.NumericValue;
import carpet.script.value.Value;
import net.minecraft.nbt.NbtElement;
import org.javacord.api.entity.message.Reaction;

public class ReactionValue extends Value {

    public Reaction reaction;

    public ReactionValue(Reaction reaction) {
        this.reaction = reaction;
    }

    public Value getProperty(String property) {
        switch (property) {
            case "emoji":
                return new EmojiValue(reaction.getEmoji());
            case "count":
                return NumericValue.of(reaction.getCount());
            case "message":
                return new MessageValue(reaction.getMessage());
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
        return "dc_reaction";
    }

    @Override
    public String getString() {
        return reaction.toString();
    }

    @Override
    public boolean getBoolean() {
        return false;
    }

    @Override
    public NbtElement toTag(boolean b) {
        return null;
    }
}
