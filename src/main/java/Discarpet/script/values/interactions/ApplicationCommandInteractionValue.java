package Discarpet.script.values.interactions;

import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.interaction.ApplicationCommandInteraction;

public abstract class ApplicationCommandInteractionValue<T extends ApplicationCommandInteraction> extends InteractionValue<T> {
    public ApplicationCommandInteractionValue(String typeName, T applicationCommandInteraction) {
        super(typeName, applicationCommandInteraction);
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "command_id" -> StringValue.of(delegate.getCommandIdAsString());
            case "command_name" -> StringValue.of(delegate.getCommandName());
            default -> super.getProperty(property);
        };
    }
}
