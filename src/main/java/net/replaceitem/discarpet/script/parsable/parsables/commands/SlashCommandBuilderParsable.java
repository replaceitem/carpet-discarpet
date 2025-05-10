package net.replaceitem.discarpet.script.parsable.parsables.commands;

import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;

import java.util.List;

@ParsableClass(name = "slash_command_builder")
public class SlashCommandBuilderParsable implements ParsableConstructor<SlashCommandData> {

    String name;
    String description;
    @OptionalField
    List<SlashCommandOptionParsable.SlashCommandOptionUnion> options = List.of();

    @Override
    public SlashCommandData construct() {
        SlashCommandData slash = Commands.slash(name, description);
        for (SlashCommandOptionParsable.SlashCommandOptionUnion option : options) {
            option.asOption().ifPresent(slash::addOptions);
            option.asSubcommand().ifPresent(slash::addSubcommands);
            option.asSubcommandGroup().ifPresent(slash::addSubcommandGroups);
        }
        return slash;
    }
}
