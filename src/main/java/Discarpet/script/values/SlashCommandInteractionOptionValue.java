package Discarpet.script.values;

import Discarpet.script.util.ValueUtil;
import carpet.script.value.BooleanValue;
import carpet.script.value.ListValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.minecraft.nbt.NbtElement;
import org.javacord.api.interaction.SlashCommandInteractionOption;

public class SlashCommandInteractionOptionValue extends Value {

    public SlashCommandInteractionOption slashCommandInteractionOption;

    public SlashCommandInteractionOptionValue(SlashCommandInteractionOption slashCommandInteractionOption) {
        this.slashCommandInteractionOption = slashCommandInteractionOption;
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "name" -> StringValue.of(slashCommandInteractionOption.getName());
            case "is_subcommand_or_group" -> BooleanValue.of(slashCommandInteractionOption.isSubcommandOrGroup());
            case "value" -> getValue();
            case "options" -> ListValue.wrap(slashCommandInteractionOption.getOptions().stream().map(SlashCommandInteractionOptionValue::new));
           
            default -> Value.NULL;
        };
    }
    
    private Value getValue() {
        if(slashCommandInteractionOption.getStringValue().isPresent()) return StringValue.of(ValueUtil.unpackOptional(slashCommandInteractionOption.getStringValue()));
        if(slashCommandInteractionOption.getLongValue().isPresent()) return NumericValue.of(ValueUtil.unpackOptional(slashCommandInteractionOption.getLongValue()));
        if(slashCommandInteractionOption.getBooleanValue().isPresent()) return ValueUtil.ofOptionalBoolean(slashCommandInteractionOption.getBooleanValue());
        if(slashCommandInteractionOption.getUserValue().isPresent()) return UserValue.of(slashCommandInteractionOption.getUserValue());
        if(slashCommandInteractionOption.getChannelValue().isPresent()) return ChannelValue.of(slashCommandInteractionOption.getChannelValue());
        if(slashCommandInteractionOption.getRoleValue().isPresent()) return RoleValue.of(slashCommandInteractionOption.getRoleValue());
        if(slashCommandInteractionOption.getDecimalValue().isPresent()) return NumericValue.of(ValueUtil.unpackOptional(slashCommandInteractionOption.getDecimalValue()));
        return StringValue.of(ValueUtil.unpackOptional(slashCommandInteractionOption.getStringRepresentationValue()));
    }

    public SlashCommandInteractionOption getSlashCommandInteractionOption() {
        return slashCommandInteractionOption;
    }

    @Override
    public Value in(Value value1) {
        return getProperty(value1.getString());
    }

    @Override
    public String getTypeString() {
        return "dc_slash_command_interaction_option";
    }

    @Override
    public String getString() {
        return slashCommandInteractionOption.toString();
    }

    @Override
    public boolean getBoolean() {
        return true;
    }

    @Override
    public NbtElement toTag(boolean b) {
        return null;
    }
}
