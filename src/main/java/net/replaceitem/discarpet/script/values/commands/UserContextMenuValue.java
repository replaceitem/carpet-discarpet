package net.replaceitem.discarpet.script.values.commands;

import org.javacord.api.interaction.UserContextMenu;

public class UserContextMenuValue extends ApplicationCommandValue<UserContextMenu> {
    public UserContextMenuValue(UserContextMenu userContextMenu) {
        super(userContextMenu);
    }

    @Override
    protected String getDiscordTypeString() {
        return "user_context_menu";
    }
}
