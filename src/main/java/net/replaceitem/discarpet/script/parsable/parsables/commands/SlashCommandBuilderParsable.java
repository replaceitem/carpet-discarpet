package net.replaceitem.discarpet.script.parsable.parsables.commands;

import net.replaceitem.discarpet.script.parsable.Optional;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;
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
