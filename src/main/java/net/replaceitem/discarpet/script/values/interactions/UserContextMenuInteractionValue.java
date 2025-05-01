package net.replaceitem.discarpet.script.values.interactions;

import carpet.script.value.Value;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.replaceitem.discarpet.script.values.UserValue;

public class UserContextMenuInteractionValue extends ApplicationCommandInteractionValue<UserContextInteractionEvent> {
    public UserContextMenuInteractionValue(UserContextInteractionEvent userContextMenuInteraction) {
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
