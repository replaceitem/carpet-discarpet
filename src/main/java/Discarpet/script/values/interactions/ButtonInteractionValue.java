package Discarpet.script.values.interactions;

import Discarpet.script.values.ChannelValue;
import Discarpet.script.values.MessageValue;
import Discarpet.script.values.UserValue;
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
            default -> super.getProperty(property);
        };
    }
}
