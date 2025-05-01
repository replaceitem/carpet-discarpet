package net.replaceitem.discarpet.script.values.interactions;

import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.MessageValue;
import org.jetbrains.annotations.Nullable;

public class ButtonInteractionValue extends InteractionValue<ButtonInteractionEvent> {
    public ButtonInteractionValue(ButtonInteractionEvent buttonInteraction) {
        super(buttonInteraction);
    }
    
    public static Value of(@Nullable ButtonInteractionEvent buttonInteractionEvent) {
        return ValueUtil.ofNullable(buttonInteractionEvent, ButtonInteractionValue::new);
    }

    @Override
    protected String getDiscordTypeString() {
        return "button_interaction";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "custom_id" -> StringValue.of(delegate.getComponentId());
            case "message" -> MessageValue.of(delegate.getMessage());
            default -> super.getProperty(property);
        };
    }
}
