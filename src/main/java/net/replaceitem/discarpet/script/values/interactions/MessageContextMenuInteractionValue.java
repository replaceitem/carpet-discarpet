package net.replaceitem.discarpet.script.values.interactions;

import carpet.script.value.Value;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.MessageValue;
import org.jetbrains.annotations.Nullable;

public class MessageContextMenuInteractionValue extends ApplicationCommandInteractionValue<MessageContextInteractionEvent> {
    public MessageContextMenuInteractionValue(MessageContextInteractionEvent messageContextMenuInteraction) {
        super(messageContextMenuInteraction);
    }

    public static Value of(@Nullable MessageContextInteractionEvent messageContextInteractionEvent) {
        return ValueUtil.ofNullable(messageContextInteractionEvent, MessageContextMenuInteractionValue::new);
    }

    @Override
    protected String getDiscordTypeString() {
        return "message_context_menu_interaction";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "target" -> MessageValue.of(delegate.getTarget());
            default -> super.getProperty(property);
        };
    }
}
