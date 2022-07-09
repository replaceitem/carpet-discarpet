package Discarpet.script.values.commands;

import org.javacord.api.interaction.MessageContextMenu;

public class MessageContextMenuValue extends ApplicationCommandValue<MessageContextMenu> {
    public MessageContextMenuValue(MessageContextMenu messageContextMenu) {
        super("message_context_menu",messageContextMenu);
    }
}
