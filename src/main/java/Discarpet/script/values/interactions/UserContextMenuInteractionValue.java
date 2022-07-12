package Discarpet.script.values.interactions;

import Discarpet.script.values.UserValue;
import carpet.script.value.Value;
import org.javacord.api.interaction.UserContextMenuInteraction;

public class UserContextMenuInteractionValue extends ApplicationCommandInteractionValue<UserContextMenuInteraction> {
    public UserContextMenuInteractionValue(UserContextMenuInteraction userContextMenuInteraction) {
        super("user_context_menu_interaction", userContextMenuInteraction);
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "target" -> UserValue.of(delegate.getTarget());
            default -> super.getProperty(property);
        };
    }
}
