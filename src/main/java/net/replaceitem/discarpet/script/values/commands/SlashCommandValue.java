package net.replaceitem.discarpet.script.values.commands;

import carpet.script.value.ListValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.interaction.SlashCommand;

public class SlashCommandValue extends ApplicationCommandValue<SlashCommand> {
    public SlashCommandValue(SlashCommand slashCommand) {
        super(slashCommand);
    }

    @Override
    protected String getDiscordTypeString() {
        return "slash_command_value";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "options" -> ListValue.wrap(delegate.getOptions().stream().map(slashCommandOption -> StringValue.of(slashCommandOption.getName())));
            default -> super.getProperty(property);
        };
    }
}
