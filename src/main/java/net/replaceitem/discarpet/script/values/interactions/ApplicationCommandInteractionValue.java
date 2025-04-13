package net.replaceitem.discarpet.script.values.interactions;

import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.interactions.commands.CommandInteraction;

public abstract class ApplicationCommandInteractionValue<T extends CommandInteraction> extends InteractionValue<T> {
    public ApplicationCommandInteractionValue(T applicationCommandInteraction) {
        super(applicationCommandInteraction);
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "command_id" -> StringValue.of(delegate.getCommandId());
            case "command_name" -> StringValue.of(delegate.getFullCommandName());
            default -> super.getProperty(property);
        };
    }
}
