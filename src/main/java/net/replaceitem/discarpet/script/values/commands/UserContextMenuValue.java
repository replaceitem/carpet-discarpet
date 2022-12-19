package net.replaceitem.discarpet.script.values.commands;

import org.javacord.api.interaction.UserContextMenu;

public class UserContextMenuValue extends ApplicationCommandValue<UserContextMenu> {
    public UserContextMenuValue(UserContextMenu userContextMenu) {
        super("user_context_menu",userContextMenu);
    }
}
