package net.replaceitem.discarpet.script.values.interactions;

import carpet.script.value.MapValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.replaceitem.discarpet.script.util.ValueUtil;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Collectors;

public class ModalInteractionValue extends InteractionValue<ModalInteractionEvent> {
    public ModalInteractionValue(ModalInteractionEvent modalInteraction) {
        super(modalInteraction);
    }

    public static Value of(@Nullable ModalInteractionEvent modalInteractionEvent) {
        return ValueUtil.ofNullable(modalInteractionEvent, ModalInteractionValue::new);
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
                        m -> StringValue.of(m.getCustomId()),
                        m -> StringValue.of(m.getAsString())
                ))
        );
    }
}
