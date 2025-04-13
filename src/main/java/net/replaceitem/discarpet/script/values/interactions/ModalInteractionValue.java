package net.replaceitem.discarpet.script.values.interactions;

import carpet.script.value.MapValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.interactions.modals.ModalInteraction;

import java.util.stream.Collectors;

public class ModalInteractionValue extends InteractionValue<ModalInteraction> {
    public ModalInteractionValue(ModalInteraction modalInteraction) {
        super(modalInteraction);
    }


    @Override
    protected String getDiscordTypeString() {
        return "modal_interaction";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "custom_id" -> StringValue.of(delegate.getModalId());
            case "input_values_by_id" -> getInputValuesById();
            default -> super.getProperty(property);
        };
    }
    
    private MapValue getInputValuesById() {
        return MapValue.wrap(
                delegate.getValues().stream().collect(Collectors.toMap(
                        m -> StringValue.of(m.getId()),
                        m -> StringValue.of(m.getAsString())
                ))
        );
    }
}
