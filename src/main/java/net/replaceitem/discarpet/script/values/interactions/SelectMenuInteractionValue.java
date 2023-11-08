package net.replaceitem.discarpet.script.values.interactions;

import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.ChannelValue;
import net.replaceitem.discarpet.script.values.MessageValue;
import net.replaceitem.discarpet.script.values.RoleValue;
import net.replaceitem.discarpet.script.values.UserValue;
import carpet.script.value.ListValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.replaceitem.discarpet.script.values.common.DiscordValue;
import org.javacord.api.interaction.SelectMenuInteraction;

import java.util.Locale;

public class SelectMenuInteractionValue extends InteractionValue<SelectMenuInteraction> {
    public SelectMenuInteractionValue(SelectMenuInteraction selectMenuInteraction) {
        super(selectMenuInteraction);
    }


    @Override
    protected String getDiscordTypeString() {
        return "select_menu_interaction";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "custom_id" -> StringValue.of(delegate.getCustomId());
            case "chosen" -> getChosenOptions();
            case "options" -> ListValue.wrap(delegate.getPossibleOptions().stream().map(selectMenuOption -> StringValue.of(selectMenuOption.getValue())));
            case "min" -> NumericValue.of(delegate.getMinimumValues());
            case "max" -> NumericValue.of(delegate.getMaximumValues());
            case "placeholder" -> StringValue.of(ValueUtil.unpackOptional(delegate.getPlaceholder()));
            case "message" -> MessageValue.of(delegate.getMessage());
            case "component_type" -> StringValue.of(delegate.getComponentType().name().toLowerCase(Locale.ROOT));
            default -> super.getProperty(property);
        };
    }
    
    private Value getChosenOptions() {
        return switch (delegate.getComponentType()) {
            case SELECT_MENU_CHANNEL -> ListValue.wrap(delegate.getSelectedChannels().stream().map(ChannelValue::new));
            case SELECT_MENU_USER -> ListValue.wrap(delegate.getSelectedUsers().stream().map(UserValue::new));
            case SELECT_MENU_ROLE -> ListValue.wrap(delegate.getSelectedRoles().stream().map(RoleValue::new));
            case SELECT_MENU_MENTIONABLE -> ListValue.wrap(delegate.getSelectedMentionables().stream().map(DiscordValue::of));
            default -> ListValue.wrap(delegate.getChosenOptions().stream().map(selectMenuOption -> StringValue.of(selectMenuOption.getValue())));
        };
    }
}
