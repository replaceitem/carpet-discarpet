package Discarpet.script.values;

import Discarpet.script.values.common.InteractionValue;
import carpet.script.value.MapValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.HighLevelComponent;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.entity.message.component.TextInput;
import org.javacord.api.interaction.ModalInteraction;

import java.util.HashMap;
import java.util.Map;

public class ModalInteractionValue extends InteractionValue<ModalInteraction> {
    public ModalInteractionValue(ModalInteraction modalInteraction) {
        super("modal_interaction",modalInteraction);
    }
    
    public Value getProperty(String property) {
        return switch (property) {
            case "id" -> StringValue.of(delegate.getCustomId());
            case "channel" -> ChannelValue.of(delegate.getChannel());
            case "user" -> new UserValue(delegate.getUser());
            case "locale" -> StringValue.of(delegate.getLocale().getLocaleCode());
            case "input_values_by_id" -> getInputValuesById();
            default -> Value.NULL;
        };
    }
    
    private MapValue getInputValuesById() {
        Map<Value, Value> inputValues = new HashMap<>();
        for (HighLevelComponent highLevelComponent : delegate.getComponents()) {
            if(!highLevelComponent.isActionRow()) continue;
            for (LowLevelComponent lowLevelComponent : ((ActionRow) highLevelComponent).getComponents()) {
                if(!lowLevelComponent.isTextInput()) continue;
                TextInput textInput = ((TextInput) lowLevelComponent);
                inputValues.put(StringValue.of(textInput.getCustomId()),StringValue.of(textInput.getValue()));
            }
        }
        return MapValue.wrap(inputValues);
    }
}
