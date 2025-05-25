package net.replaceitem.discarpet.script.schema.schemas.commands;

import carpet.script.Context;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;

@SchemaClass(name = "user_context_menu_builder")
public class UserContextMenuBuilderSchema implements SchemaConstructor<CommandData> {

    String name;

    @Override
    public CommandData construct(Context context) {
        return Commands.user(name);
    }
}
