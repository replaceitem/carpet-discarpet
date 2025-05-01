package net.replaceitem.discarpet.script.parsable.parsables.commands;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;

@ParsableClass(name = "user_context_menu_builder")
public class UserContextMenuBuilderParsable implements ParsableConstructor<CommandData> {

    String name;

    @Override
    public CommandData construct() {
        return Commands.user(name);
    }
}
