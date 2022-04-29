package Discarpet.script.values;

import Discarpet.script.values.common.InteractionValue;
import carpet.script.value.ListValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.interaction.SlashCommandInteraction;

public class SlashCommandInteractionValue extends InteractionValue<SlashCommandInteraction> {
    public SlashCommandInteractionValue(SlashCommandInteraction slashCommandInteraction) {
        super("slash_command_interaction",slashCommandInteraction);
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "id" -> StringValue.of(value.getCommandIdAsString());
            case "command_name" -> StringValue.of(value.getCommandName());
            case "channel" -> ChannelValue.of(value.getChannel());
            case "user" -> UserValue.of(value.getUser());
            case "token" -> StringValue.of(value.getToken());
            case "arguments" -> ListValue.wrap((value.getArguments().stream().map(SlashCommandInteractionOptionValue::new)));
            default -> Value.NULL;
        };
    }
}
