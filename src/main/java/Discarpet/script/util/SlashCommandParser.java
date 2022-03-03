package Discarpet.script.util;

import carpet.script.exception.InternalExpressionException;
import carpet.script.value.ListValue;
import carpet.script.value.MapValue;
import carpet.script.value.NumericValue;
import carpet.script.value.Value;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionBuilder;
import org.javacord.api.interaction.SlashCommandOptionChoice;
import org.javacord.api.interaction.SlashCommandOptionChoiceBuilder;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static Discarpet.script.util.MapValueUtil.*;

public class SlashCommandParser {
    public static List<SlashCommandOption> parseSlashCommandOptions(Value value) {
        if(!(value instanceof ListValue listValue)) throw new InternalExpressionException("Slash command options need to be a list value");
        List<Value> options = listValue.getItems();
        List<SlashCommandOption> slashCommandOptions = new ArrayList<>();
        for (int i = 0; i < options.size(); i++) {
            Value option = options.get(i);
            try {
                slashCommandOptions.add(parseSlashCommandOption(option));
            } catch (Exception ex) {
                throw new InternalExpressionException("Could not parse slash command option with index " + i + " inside options list: " + ex.getMessage());
            }
        }
        return slashCommandOptions;
    }

    private static SlashCommandOption parseSlashCommandOption(Value value) {
        if(!(value instanceof MapValue mapValue)) throw new InternalExpressionException("Slash command option needs to be a map value");
        Map<Value, Value> map = mapValue.getMap();

        SlashCommandOptionBuilder s = new SlashCommandOptionBuilder();
        s.setType(SlashCommandOptionType.valueOf(getStringInMap(map,"type").toUpperCase(Locale.ROOT)));

        s.setName(getStringInMap(map,"name"));
        s.setDescription(getStringInMap(map,"description"));
        s.setRequired(getBooleanInMapOrDefault(map,"required",false));
        if(mapHasKey(map,"options")) s.setOptions(parseSlashCommandOptions(getValueInMap(map,"options")));
        if(mapHasKey(map,"choices")) s.setChoices(parseSlashCommandOptionChoices(getListInMap(map,"choices")));
        return s.build();
    }

    private static List<SlashCommandOptionChoice> parseSlashCommandOptionChoices(List<Value> choiceValues) {
        List<SlashCommandOptionChoice> slashCommandOptionChoices = new ArrayList<>();
        for (int i = 0; i < choiceValues.size(); i++) {
            Value option = choiceValues.get(i);
            try {
                slashCommandOptionChoices.add(parseSlashCommandOptionChoice(option));
            } catch (Exception ex) {
                throw new InternalExpressionException("Could not parse slash command option with index " + i + " inside choices list: " + ex.getMessage());
            }
        }
        return slashCommandOptionChoices;
    }

    private static SlashCommandOptionChoice parseSlashCommandOptionChoice(Value value) {
        if(!(value instanceof MapValue mapValue)) throw new InternalExpressionException("Slash command option choice needs to be a map value");
        Map<Value, Value> map = mapValue.getMap();
        SlashCommandOptionChoiceBuilder s = new SlashCommandOptionChoiceBuilder();
        s.setName(getStringInMap(map,"name"));
        Value valueValue = getValueInMap(map, "value");
        if(valueValue instanceof NumericValue numericValue)
            s.setValue(numericValue.getInt());
        else
            s.setValue(valueValue.getString());
        return s.build();
    }

}
