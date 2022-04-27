package Discarpet.script.parsable.parsables;

import Discarpet.script.parsable.Optional;
import Discarpet.script.parsable.ParsableClass;
import Discarpet.script.parsable.ParsableConstructor;
import carpet.script.exception.InternalExpressionException;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionBuilder;
import org.javacord.api.interaction.SlashCommandOptionChoice;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.List;
import java.util.Locale;

@ParsableClass(name = "slash_command_option")
public class SlashCommandOptionParsable implements ParsableConstructor<SlashCommandOption> {
    
    String type;
    String name;
    String description;
    @Optional Boolean required = false;
    @Optional List<SlashCommandOption> options = List.of();
    @Optional List<SlashCommandOptionChoice> choices = List.of();
    
    
    @Override
    public SlashCommandOption construct() {
        SlashCommandOptionBuilder slashCommandOptionBuilder = new SlashCommandOptionBuilder();
        SlashCommandOptionType slashCommandOptionType = SlashCommandOptionType.valueOf(type.toUpperCase(Locale.ROOT));
        if(slashCommandOptionType == SlashCommandOptionType.UNKNOWN) throw new InternalExpressionException("Invalid slash command option type: " + type);
        slashCommandOptionBuilder.setType(slashCommandOptionType);
        slashCommandOptionBuilder.setName(name);
        slashCommandOptionBuilder.setDescription(description);
        slashCommandOptionBuilder.setRequired(required);
        slashCommandOptionBuilder.setOptions(options);
        slashCommandOptionBuilder.setChoices(choices);
        return slashCommandOptionBuilder.build();
    }
}
