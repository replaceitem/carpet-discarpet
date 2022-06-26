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
            case "id" -> StringValue.of(delegate.getCustomId());
            case "channel" -> ChannelValue.of(delegate.getChannel());
            case "user" -> new UserValue(delegate.getUser());
            case "message" -> MessageValue.of(delegate.getMessage());
            case "locale" -> StringValue.of(delegate.getLocale().getLocaleCode());
            default -> Value.NULL;
        };
    }
}
