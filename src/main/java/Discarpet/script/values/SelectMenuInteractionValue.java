package Discarpet.script.values;

import carpet.script.value.Value;
import net.minecraft.nbt.NbtElement;
import org.javacord.api.interaction.SelectMenuInteraction;

public class SelectMenuInteractionValue extends Value {

    public SelectMenuInteraction selectMenuInteraction;

    public SelectMenuInteractionValue(SelectMenuInteraction selectMenuInteraction) {
        this.selectMenuInteraction = selectMenuInteraction;
    }

    public Value getProperty(String property) {

        switch (property) {
            //TODO - OTHER CASES SPECIFIC TO select menu
            case "channel":
                if(selectMenuInteraction.getChannel().isEmpty()) return Value.NULL;
                return new ChannelValue(selectMenuInteraction.getChannel().get());
            case "user":
                return new UserValue(selectMenuInteraction.getUser());
            case "message":
                if(selectMenuInteraction.getMessage().isEmpty()) return Value.NULL;
                return new MessageValue(selectMenuInteraction.getMessage().get());
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
        return "dc_select_menu_interaction";
    }

    @Override
    public String getString() {
        return selectMenuInteraction.getCustomId();
    }

    @Override
    public boolean getBoolean() {
        return true;
    }

    @Override
    public NbtElement toTag(boolean b) {
        return null;
    }

    public SelectMenuInteraction getSelectMenuInteraction() {
        return selectMenuInteraction;
    }
}
