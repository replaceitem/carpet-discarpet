package net.replaceitem.discarpet.script.values.interactions;

import carpet.script.value.Value;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.UserValue;
import org.jetbrains.annotations.Nullable;

public class UserContextMenuInteractionValue extends ApplicationCommandInteractionValue<UserContextInteractionEvent> {
    public UserContextMenuInteractionValue(UserContextInteractionEvent userContextMenuInteraction) {
        super(userContextMenuInteraction);
    }

    public static Value of(@Nullable UserContextInteractionEvent userContextInteractionEvent) {
        return ValueUtil.ofNullable(userContextInteractionEvent, UserContextMenuInteractionValue::new);
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
