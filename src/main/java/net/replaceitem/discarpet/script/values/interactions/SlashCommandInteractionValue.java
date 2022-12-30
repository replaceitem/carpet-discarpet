package net.replaceitem.discarpet.script.values.interactions;

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

public class SlashCommandInteractionValue extends ApplicationCommandInteractionValue<SlashCommandInteraction> {
    public SlashCommandInteractionValue(SlashCommandInteraction applicationCommandInteraction) {
        super("slash_command_interaction", applicationCommandInteraction);
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "arguments" -> ListValue.wrap(getAllArguments().stream().map(SlashCommandInteractionOptionValue::new));
            case "arguments_by_name" -> getArgumentsByName();
            default -> super.getProperty(property);
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
