package net.replaceitem.discarpet.script.values.interactions;

import carpet.script.value.BooleanValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.replaceitem.discarpet.script.util.ValueConversions;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.AttachmentValue;
import net.replaceitem.discarpet.script.values.ChannelValue;
import net.replaceitem.discarpet.script.values.RoleValue;
import net.replaceitem.discarpet.script.values.UserValue;
import net.replaceitem.discarpet.script.values.common.DiscordValue;
import org.jetbrains.annotations.Nullable;

public class SlashCommandInteractionOptionValue extends DiscordValue<OptionMapping> {
    public SlashCommandInteractionOptionValue(OptionMapping option) {
        super(option);
    }
    
    public static Value of(@Nullable OptionMapping optionMapping) {
        return ValueUtil.ofNullable(optionMapping, SlashCommandInteractionOptionValue::new);
    }

    @Override
    protected String getDiscordTypeString() {
        return "slash_command_interaction_option";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "name" -> StringValue.of(delegate.getName());
            // TODO deprecate this and add ~type
            case "is_subcommand_or_group" -> BooleanValue.of(delegate.getType() == OptionType.SUB_COMMAND || delegate.getType() == OptionType.SUB_COMMAND_GROUP);
            case "value" -> getValue();
            default -> super.getProperty(property);
        };
    }
    
    private Value getValue() {
        return switch (delegate.getType()) {
            case STRING -> StringValue.of(delegate.getAsString());
            case INTEGER -> NumericValue.of(delegate.getAsInt());
            case BOOLEAN -> BooleanValue.of(delegate.getAsBoolean());
            case USER -> UserValue.of(delegate.getAsUser());
            case CHANNEL -> ChannelValue.of(delegate.getAsChannel());
            case ROLE -> RoleValue.of(delegate.getAsRole());
            case MENTIONABLE -> ValueConversions.toValue(delegate.getAsMentionable());
            case NUMBER -> NumericValue.of(delegate.getAsDouble());
            case ATTACHMENT -> AttachmentValue.of(delegate.getAsAttachment());
            case SUB_COMMAND, SUB_COMMAND_GROUP, UNKNOWN -> Value.NULL;
        };
    }
}
