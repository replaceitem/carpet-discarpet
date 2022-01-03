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
        return switch (property) {
            case "emoji" -> new EmojiValue(reaction.getEmoji());
            case "count" -> NumericValue.of(reaction.getCount());
            case "message" -> new MessageValue(reaction.getMessage());
            default -> Value.NULL;
        };
    }

    public Reaction getReaction() {
        return reaction;
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
        return true;
    }

    @Override
    public NbtElement toTag(boolean b) {
        return null;
    }
}
