package net.replaceitem.discarpet.script.parsable.parsables.commands;

import carpet.script.Context;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;

@ParsableClass(name = "slash_command_option_choice")
public class SlashCommandOptionChoiceParsable implements ParsableConstructor<Command.Choice> {
    
    String name;
    String value;
    
    @Override
    public Command.Choice construct(Context context) {
        return new Command.Choice(name, value);
    }
}
