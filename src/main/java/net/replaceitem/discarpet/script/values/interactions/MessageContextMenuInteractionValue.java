package net.replaceitem.discarpet.script.values.interactions;

import net.replaceitem.discarpet.script.values.UserValue;
import carpet.script.value.Value;
import org.javacord.api.interaction.MessageContextMenuInteraction;

public class MessageContextMenuInteractionValue extends ApplicationCommandInteractionValue<MessageContextMenuInteraction> {
    public MessageContextMenuInteractionValue(MessageContextMenuInteraction messageContextMenuInteraction) {
        super("message_context_menu_interaction", messageContextMenuInteraction);
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "target" -> UserValue.of(delegate.getTarget());
            default -> super.getProperty(property);
        };
    }
}
