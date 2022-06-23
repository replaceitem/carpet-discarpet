package Discarpet.script.values;

import Discarpet.script.values.common.InteractionValue;
import carpet.script.value.ListValue;
import carpet.script.value.MapValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlashCommandInteractionValue extends InteractionValue<SlashCommandInteraction> {
    public SlashCommandInteractionValue(SlashCommandInteraction slashCommandInteraction) {
        super("slash_command_interaction",slashCommandInteraction);
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "id" -> StringValue.of(delegate.getCommandIdAsString());
            case "command_name" -> StringValue.of(delegate.getCommandName());
            case "channel" -> ChannelValue.of(delegate.getChannel());
            case "user" -> UserValue.of(delegate.getUser());
            case "token" -> StringValue.of(delegate.getToken());
            case "arguments" -> ListValue.wrap(getAllArguments().stream().map(SlashCommandInteractionOptionValue::new));
            case "arguments_by_name" -> getArgumentsByName();
            default -> Value.NULL;
        };
    }
    
    private List<SlashCommandInteractionOption> getAllArguments() {
        List<SlashCommandInteractionOption> options = new ArrayList<>(this.delegate.getOptions());
        this.delegate.getOptions().forEach(option -> addOptionRec(options,option));
        return options;
    }
    
    private void addOptionRec(List<SlashCommandInteractionOption> options, SlashCommandInteractionOption option) {
        options.add(option);
        option.getOptions().forEach(option1 -> addOptionRec(options, option1));
    }

    private Value getArgumentsByName() {
        final Map<Value, Value> optionsMap = new HashMap<>();
        this.addOptions(getAllArguments(), optionsMap);
        return MapValue.wrap(optionsMap);
    }
    
    private void addOptions(List<SlashCommandInteractionOption> options, final Map<Value, Value> map) {
        for (SlashCommandInteractionOption slashCommandInteractionOption : options) {
            map.put(StringValue.of(slashCommandInteractionOption.getName()), SlashCommandInteractionOptionValue.of(slashCommandInteractionOption));
            this.addOptions(slashCommandInteractionOption.getOptions(),map);
        }
    }
}
