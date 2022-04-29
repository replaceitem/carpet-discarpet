package Discarpet.script.values;

import Discarpet.script.util.ValueUtil;
import Discarpet.script.values.common.InteractionValue;
import carpet.script.value.ListValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.interaction.SelectMenuInteraction;

public class SelectMenuInteractionValue extends InteractionValue<SelectMenuInteraction> {
    public SelectMenuInteractionValue(SelectMenuInteraction selectMenuInteraction) {
        super("select_menu_interaction",selectMenuInteraction);
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "id" -> StringValue.of(value.getCustomId());
            case "chosen" -> ListValue.wrap(value.getChosenOptions().stream().map(selectMenuOption -> StringValue.of(selectMenuOption.getValue())));
            case "options" -> ListValue.wrap(value.getPossibleOptions().stream().map(selectMenuOption -> StringValue.of(selectMenuOption.getValue())));
            case "min" -> NumericValue.of(value.getMinimumValues());
            case "max" -> NumericValue.of(value.getMaximumValues());
            case "placeholder" -> StringValue.of(ValueUtil.unpackOptional(value.getPlaceholder()));
            case "channel" -> ChannelValue.of(value.getChannel());
            case "user" -> UserValue.of(value.getUser());
            case "message" -> MessageValue.of(value.getMessage());
            default -> Value.NULL;
        };
    }
}
