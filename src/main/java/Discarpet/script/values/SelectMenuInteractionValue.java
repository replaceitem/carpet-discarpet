package Discarpet.script.values;

import Discarpet.script.util.ValueUtil;
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
        return switch (property) {
            case "id" -> StringValue.of(selectMenuInteraction.getCustomId());
            case "chosen" -> ListValue.wrap(selectMenuInteraction.getChosenOptions().stream().map(selectMenuOption -> StringValue.of(selectMenuOption.getValue())));
            case "options" -> ListValue.wrap(selectMenuInteraction.getPossibleOptions().stream().map(selectMenuOption -> StringValue.of(selectMenuOption.getValue())));
            case "min" -> NumericValue.of(selectMenuInteraction.getMinimumValues());
            case "max" -> NumericValue.of(selectMenuInteraction.getMaximumValues());
            case "placeholder" -> StringValue.of(ValueUtil.unpackOptional(selectMenuInteraction.getPlaceholder()));
            case "channel" -> ChannelValue.of(selectMenuInteraction.getChannel());
            case "user" -> UserValue.of(selectMenuInteraction.getUser());
            case "message" -> MessageValue.of(selectMenuInteraction.getMessage());
            default -> Value.NULL;
        };
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
