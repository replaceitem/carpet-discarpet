package Discarpet.script.values;

import Discarpet.script.values.common.InteractionValue;
import carpet.script.value.ListValue;
import carpet.script.value.MapValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            case "arguments_by_name" -> getArgumentsByName();
            default -> Value.NULL;
        };
    }

    private Value getArgumentsByName() {
        final Map<Value, Value> optionsMap = new HashMap<>();
        this.addOptions(this.value.getArguments(), optionsMap);
        return MapValue.wrap(optionsMap);
    }
    
    private void addOptions(List<SlashCommandInteractionOption> options, final Map<Value, Value> map) {
        for (SlashCommandInteractionOption slashCommandInteractionOption : options) {
            map.put(StringValue.of(slashCommandInteractionOption.getName()), SlashCommandInteractionOptionValue.of(slashCommandInteractionOption));
            this.addOptions(slashCommandInteractionOption.getOptions(),map);
        }
    }
}
