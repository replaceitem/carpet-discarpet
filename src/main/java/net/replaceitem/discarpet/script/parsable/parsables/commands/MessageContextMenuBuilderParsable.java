package net.replaceitem.discarpet.script.parsable.parsables.commands;

import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;
import org.javacord.api.interaction.MessageContextMenuBuilder;

@ParsableClass(name = "message_context_menu_builder")
public class MessageContextMenuBuilderParsable implements ParsableConstructor<MessageContextMenuBuilder> {

    String name;

    @Override
    public MessageContextMenuBuilder construct() {
        MessageContextMenuBuilder messageContextMenuBuilder = new MessageContextMenuBuilder();

        messageContextMenuBuilder.setName(name);
        
        return messageContextMenuBuilder;
    }
}
