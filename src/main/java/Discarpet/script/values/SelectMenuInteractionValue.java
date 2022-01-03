package Discarpet.script.values;

import carpet.script.value.ListValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.minecraft.nbt.NbtElement;
import org.javacord.api.interaction.InteractionBase;
import org.javacord.api.interaction.SelectMenuInteraction;

public class SelectMenuInteractionValue extends Value implements InteractionValue {

    public SelectMenuInteraction selectMenuInteraction;

    public SelectMenuInteractionValue(SelectMenuInteraction selectMenuInteraction) {
        this.selectMenuInteraction = selectMenuInteraction;
    }

    public Value getProperty(String property) {

        switch (property) {
            case "id":
                return StringValue.of(selectMenuInteraction.getCustomId());
            case "chosen":
                return new ListValue(selectMenuInteraction.getChosenOptions().stream().map(selectMenuOption -> StringValue.of(selectMenuOption.getValue())).toList());
            case "options":
                return new ListValue(selectMenuInteraction.getPossibleOptions().stream().map(selectMenuOption -> StringValue.of(selectMenuOption.getValue())).toList());
            case "min":
                return NumericValue.of(selectMenuInteraction.getMinimumValues());
            case "max":
                return NumericValue.of(selectMenuInteraction.getMaximumValues());
            case "placeholder":
                if(selectMenuInteraction.getPlaceholder().isEmpty()) return Value.NULL;
                return StringValue.of(selectMenuInteraction.getPlaceholder().get());
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
        return selectMenuInteraction.toString();
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

    @Override
    public InteractionBase getInteractionBase() {
        return selectMenuInteraction;
    }
}
