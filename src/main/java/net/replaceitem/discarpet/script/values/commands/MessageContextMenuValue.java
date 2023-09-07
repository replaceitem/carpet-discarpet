package net.replaceitem.discarpet.script.values.commands;

import org.javacord.api.interaction.MessageContextMenu;

public class MessageContextMenuValue extends ApplicationCommandValue<MessageContextMenu> {
    public MessageContextMenuValue(MessageContextMenu messageContextMenu) {
        super(messageContextMenu);
    }

    @Override
    protected String getDiscordTypeString() {
        return "message_context_menu";
    }
}
