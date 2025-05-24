package net.replaceitem.discarpet.script.values.interactions;

import carpet.script.value.ListValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.events.interaction.component.GenericSelectMenuInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.EntitySelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.replaceitem.discarpet.script.util.ValueConversions;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.MessageValue;
import org.jetbrains.annotations.Nullable;

public class SelectMenuInteractionValue extends InteractionValue<GenericSelectMenuInteractionEvent<?,?>> {
    public SelectMenuInteractionValue(GenericSelectMenuInteractionEvent<?,?> selectMenuInteraction) {
        super(selectMenuInteraction);
    }

    public static Value of(@Nullable GenericSelectMenuInteractionEvent<?,?> genericSelectMenuInteractionEvent) {
        return ValueUtil.ofNullable(genericSelectMenuInteractionEvent, SelectMenuInteractionValue::new);
    }

    @Override
    protected String getDiscordTypeString() {
        return "select_menu_interaction";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "custom_id" -> StringValue.of(delegate.getComponentId());
            case "chosen" -> ListValue.wrap(delegate.getValues().stream().map(ValueConversions::toValue));
            case "options" -> getAllOptions();
            case "min" -> NumericValue.of(delegate.getSelectMenu().getMinValues());
            case "max" -> NumericValue.of(delegate.getSelectMenu().getMaxValues());
            case "placeholder" -> StringValue.of(delegate.getSelectMenu().getPlaceholder());
            case "message" -> MessageValue.of(delegate.getMessage());
            case "component_type" -> ValueUtil.ofEnum(delegate.getComponentType());
            default -> super.getProperty(property);
        };
    }
    
    private Value getAllOptions() {
        return switch (delegate.getSelectMenu()) {
            case StringSelectMenu s -> ListValue.wrap(s.getOptions().stream().map(SelectOption::getValue).map(StringValue::of));
            case EntitySelectMenu s -> ListValue.wrap(s.getDefaultValues().stream().map(ISnowflake::getId).map(StringValue::of));
            default -> ListValue.of();
        };
    }
}
