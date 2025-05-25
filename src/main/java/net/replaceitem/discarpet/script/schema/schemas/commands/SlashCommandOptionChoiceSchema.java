package net.replaceitem.discarpet.script.schema.schemas.commands;

import carpet.script.Context;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;

@SchemaClass(name = "slash_command_option_choice")
public class SlashCommandOptionChoiceSchema implements SchemaConstructor<Command.Choice> {
    
    String name;
    String value;
    
    @Override
    public Command.Choice construct(Context context) {
        return new Command.Choice(name, value);
    }
}
