package net.replaceitem.discarpet.script.values.interactions;

import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.AttachmentValue;
import net.replaceitem.discarpet.script.values.ChannelValue;
import net.replaceitem.discarpet.script.values.RoleValue;
import net.replaceitem.discarpet.script.values.UserValue;
import net.replaceitem.discarpet.script.values.common.DiscordValue;
import carpet.script.value.BooleanValue;
import carpet.script.value.ListValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.interaction.SlashCommandInteractionOption;

public class SlashCommandInteractionOptionValue extends DiscordValue<SlashCommandInteractionOption> {
    public SlashCommandInteractionOptionValue(SlashCommandInteractionOption slashCommandInteractionOption) {
        super(slashCommandInteractionOption);
    }

    @Override
    protected String getDiscordTypeString() {
        return "slash_command_interaction_option";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "name" -> StringValue.of(delegate.getName());
            case "is_subcommand_or_group" -> BooleanValue.of(delegate.isSubcommandOrGroup());
            case "value" -> getValue();
            case "options" -> ListValue.wrap(delegate.getOptions().stream().map(SlashCommandInteractionOptionValue::new));
           
            default -> super.getProperty(property);
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
