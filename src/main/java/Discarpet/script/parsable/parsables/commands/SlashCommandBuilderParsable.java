package Discarpet.script.parsable.parsables.commands;

import Discarpet.script.parsable.Optional;
import Discarpet.script.parsable.ParsableClass;
import Discarpet.script.parsable.ParsableConstructor;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandOption;

import java.util.List;

@ParsableClass(name = "slash_command_builder")
public class SlashCommandBuilderParsable implements ParsableConstructor<SlashCommandBuilder> {

    String name;
    String description;
    @Optional List<SlashCommandOption> options = List.of();

    @Override
    public SlashCommandBuilder construct() {
        SlashCommandBuilder slashCommandBuilder = new SlashCommandBuilder();
        
        slashCommandBuilder.setName(name);
        slashCommandBuilder.setDescription(description);
        slashCommandBuilder.setOptions(options);
        
        return slashCommandBuilder;
    }
}
