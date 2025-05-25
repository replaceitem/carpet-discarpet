package net.replaceitem.discarpet.script.schema.schemas.commands;

import carpet.script.Context;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;

import java.util.List;

@SchemaClass(name = "slash_command_builder")
public class SlashCommandBuilderSchema implements SchemaConstructor<SlashCommandData> {

    String name;
    String description;
    @OptionalField
    List<SlashCommandOptionSchema.SlashCommandOptionUnion> options = List.of();

    @Override
    public SlashCommandData construct(Context context) {
        SlashCommandData slash = Commands.slash(name, description);
        for (SlashCommandOptionSchema.SlashCommandOptionUnion option : options) {
            option.asOption().ifPresent(slash::addOptions);
            option.asSubcommand().ifPresent(slash::addSubcommands);
            option.asSubcommandGroup().ifPresent(slash::addSubcommandGroups);
        }
        return slash;
    }
}
