package Discarpet.script.values;

import Discarpet.script.values.common.InteractionValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.interaction.ButtonInteraction;

public class ButtonInteractionValue extends InteractionValue<ButtonInteraction> {
    public ButtonInteractionValue(ButtonInteraction buttonInteraction) {
        super("button_interaction",buttonInteraction);
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "id" -> StringValue.of(value.getCustomId());
            case "channel" -> ChannelValue.of(value.getChannel());
            case "user" -> new UserValue(value.getUser());
            case "message" -> MessageValue.of(value.getMessage());
            default -> Value.NULL;
        };
    }
}
