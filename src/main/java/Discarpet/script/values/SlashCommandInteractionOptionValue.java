package Discarpet.script.values;

import Discarpet.script.util.ValueUtil;
import Discarpet.script.values.common.DiscordValue;
import carpet.script.value.BooleanValue;
import carpet.script.value.ListValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.interaction.SlashCommandInteractionOption;

public class SlashCommandInteractionOptionValue extends DiscordValue<SlashCommandInteractionOption> {
    public SlashCommandInteractionOptionValue(SlashCommandInteractionOption slashCommandInteractionOption) {
        super("slash_command_interaction_option",slashCommandInteractionOption);
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "name" -> StringValue.of(delegate.getName());
            case "is_subcommand_or_group" -> BooleanValue.of(delegate.isSubcommandOrGroup());
            case "value" -> getValue();
            case "options" -> ListValue.wrap(delegate.getOptions().stream().map(SlashCommandInteractionOptionValue::new));
           
            default -> Value.NULL;
        };
    }
    
    private Value getValue() {
        if(delegate.getStringValue().isPresent()) return StringValue.of(ValueUtil.unpackOptional(delegate.getStringValue()));
        if(delegate.getLongValue().isPresent()) return NumericValue.of(ValueUtil.unpackOptional(delegate.getLongValue()));
        if(delegate.getBooleanValue().isPresent()) return ValueUtil.ofOptionalBoolean(delegate.getBooleanValue());
        if(delegate.getUserValue().isPresent()) return UserValue.of(delegate.getUserValue());
        if(delegate.getChannelValue().isPresent()) return ChannelValue.of(delegate.getChannelValue());
        if(delegate.getRoleValue().isPresent()) return RoleValue.of(delegate.getRoleValue());
        if(delegate.getDecimalValue().isPresent()) return NumericValue.of(ValueUtil.unpackOptional(delegate.getDecimalValue()));
        if(delegate.getAttachmentValue().isPresent()) return AttachmentValue.of(delegate.getAttachmentValue());
        return StringValue.of(ValueUtil.unpackOptional(delegate.getStringRepresentationValue()));
    }
}
