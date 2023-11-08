package net.replaceitem.discarpet.script.values.interactions;

import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.replaceitem.discarpet.script.values.MessageValue;
import org.javacord.api.interaction.ButtonInteraction;

public class ButtonInteractionValue extends InteractionValue<ButtonInteraction> {
    public ButtonInteractionValue(ButtonInteraction buttonInteraction) {
        super(buttonInteraction);
    }

    @Override
    protected String getDiscordTypeString() {
        return "button_interaction";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "custom_id" -> StringValue.of(delegate.getCustomId());
            case "message" -> MessageValue.of(delegate.getMessage());
            default -> super.getProperty(property);
        };
    }
}
