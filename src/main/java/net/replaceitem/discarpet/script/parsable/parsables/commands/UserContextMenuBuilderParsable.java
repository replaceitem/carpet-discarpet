package net.replaceitem.discarpet.script.parsable.parsables.commands;

import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;
import org.javacord.api.interaction.UserContextMenuBuilder;

@ParsableClass(name = "user_context_menu_builder")
public class UserContextMenuBuilderParsable implements ParsableConstructor<UserContextMenuBuilder> {

    String name;

    @Override
    public UserContextMenuBuilder construct() {
        UserContextMenuBuilder userContextMenuBuilder = new UserContextMenuBuilder();

        userContextMenuBuilder.setName(name);
        
        return userContextMenuBuilder;
    }
}
