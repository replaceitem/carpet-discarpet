package Discarpet.script.values;

import Discarpet.script.values.common.DiscordValue;
import carpet.script.value.ListValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.interaction.SlashCommand;

public class SlashCommandValue extends DiscordValue<SlashCommand> {
    public SlashCommandValue(SlashCommand slashCommand) {
        super("slash_command",slashCommand);
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "id" -> StringValue.of(value.getIdAsString());
            case "name" -> StringValue.of(value.getName());
            case "description" -> StringValue.of(value.getDescription());
            case "server" -> ServerValue.of(value.getServer());
            case "options" -> ListValue.wrap(value.getOptions().stream().map(slashCommandOption -> StringValue.of(slashCommandOption.getName())));
            case "creation_timestamp" -> NumericValue.of(value.getCreationTimestamp().toEpochMilli());
            default -> Value.NULL;
        };
    }
}
