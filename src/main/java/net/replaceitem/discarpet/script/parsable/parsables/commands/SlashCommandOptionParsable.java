package net.replaceitem.discarpet.script.parsable.parsables.commands;

import net.replaceitem.discarpet.script.parsable.Optional;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionBuilder;
import org.javacord.api.interaction.SlashCommandOptionChoice;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.List;

@ParsableClass(name = "slash_command_option")
public class SlashCommandOptionParsable implements ParsableConstructor<SlashCommandOption> {

    SlashCommandOptionType type;
    String name;
    String description;
    @Optional Boolean required = false;
    @Optional List<SlashCommandOption> options = List.of();
    @Optional List<SlashCommandOptionChoice> choices = List.of();
    
    
    @Override
    public SlashCommandOption construct() {
        SlashCommandOptionBuilder slashCommandOptionBuilder = new SlashCommandOptionBuilder();
        slashCommandOptionBuilder.setType(type);
        slashCommandOptionBuilder.setName(name);
        slashCommandOptionBuilder.setDescription(description);
        slashCommandOptionBuilder.setRequired(required);
        slashCommandOptionBuilder.setOptions(options);
        slashCommandOptionBuilder.setChoices(choices);
        return slashCommandOptionBuilder.build();
    }
}
