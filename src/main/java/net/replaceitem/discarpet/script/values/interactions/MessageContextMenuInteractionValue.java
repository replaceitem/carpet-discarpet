package net.replaceitem.discarpet.script.values.interactions;

import carpet.script.value.Value;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.replaceitem.discarpet.script.values.UserValue;

public class MessageContextMenuInteractionValue extends ApplicationCommandInteractionValue<MessageContextInteractionEvent> {
    public MessageContextMenuInteractionValue(MessageContextInteractionEvent messageContextMenuInteraction) {
        super(messageContextMenuInteraction);
    }


    @Override
    protected String getDiscordTypeString() {
        return "message_context_menu_interaction";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "target" -> UserValue.of(delegate.getTarget());
            default -> super.getProperty(property);
        };
    }
}
