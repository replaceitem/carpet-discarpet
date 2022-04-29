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
            case "name" -> StringValue.of(value.getName());
            case "is_subcommand_or_group" -> BooleanValue.of(value.isSubcommandOrGroup());
            case "value" -> getValue();
            case "options" -> ListValue.wrap(value.getOptions().stream().map(SlashCommandInteractionOptionValue::new));
           
            default -> Value.NULL;
        };
    }
    
    private Value getValue() {
        if(value.getStringValue().isPresent()) return StringValue.of(ValueUtil.unpackOptional(value.getStringValue()));
        if(value.getLongValue().isPresent()) return NumericValue.of(ValueUtil.unpackOptional(value.getLongValue()));
        if(value.getBooleanValue().isPresent()) return ValueUtil.ofOptionalBoolean(value.getBooleanValue());
        if(value.getUserValue().isPresent()) return UserValue.of(value.getUserValue());
        if(value.getChannelValue().isPresent()) return ChannelValue.of(value.getChannelValue());
        if(value.getRoleValue().isPresent()) return RoleValue.of(value.getRoleValue());
        if(value.getDecimalValue().isPresent()) return NumericValue.of(ValueUtil.unpackOptional(value.getDecimalValue()));
        return StringValue.of(ValueUtil.unpackOptional(value.getStringRepresentationValue()));
    }
}
