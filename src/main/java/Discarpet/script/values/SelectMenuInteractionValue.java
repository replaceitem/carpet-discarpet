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
            case "id" -> StringValue.of(delegate.getCustomId());
            case "chosen" -> ListValue.wrap(delegate.getChosenOptions().stream().map(selectMenuOption -> StringValue.of(selectMenuOption.getValue())));
            case "options" -> ListValue.wrap(delegate.getPossibleOptions().stream().map(selectMenuOption -> StringValue.of(selectMenuOption.getValue())));
            case "min" -> NumericValue.of(delegate.getMinimumValues());
            case "max" -> NumericValue.of(delegate.getMaximumValues());
            case "placeholder" -> StringValue.of(ValueUtil.unpackOptional(delegate.getPlaceholder()));
            case "channel" -> ChannelValue.of(delegate.getChannel());
            case "user" -> UserValue.of(delegate.getUser());
            case "message" -> MessageValue.of(delegate.getMessage());
            default -> Value.NULL;
        };
    }
}
