package net.replaceitem.discarpet.script.values.interactions;

import net.replaceitem.discarpet.script.values.UserValue;
import carpet.script.value.Value;
import org.javacord.api.interaction.UserContextMenuInteraction;

public class UserContextMenuInteractionValue extends ApplicationCommandInteractionValue<UserContextMenuInteraction> {
    public UserContextMenuInteractionValue(UserContextMenuInteraction userContextMenuInteraction) {
        super(userContextMenuInteraction);
    }


    @Override
    protected String getDiscordTypeString() {
        return "user_context_menu_interaction";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "target" -> UserValue.of(delegate.getTarget());
            default -> super.getProperty(property);
        };
    }
}
