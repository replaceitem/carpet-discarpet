package Discarpet.script.values;

import carpet.script.value.ListValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.minecraft.nbt.NbtElement;
import org.javacord.api.interaction.InteractionBase;
import org.javacord.api.interaction.SlashCommandInteraction;

public class SlashCommandInteractionValue extends InteractionValue {

    public SlashCommandInteraction slashCommandInteraction;

    public SlashCommandInteractionValue(SlashCommandInteraction slashCommandInteraction) {
        this.slashCommandInteraction = slashCommandInteraction;
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "id" -> StringValue.of(slashCommandInteraction.getCommandIdAsString());
            case "command_name" -> StringValue.of(slashCommandInteraction.getCommandName());
            case "channel" -> ChannelValue.of(slashCommandInteraction.getChannel());
            case "user" -> UserValue.of(slashCommandInteraction.getUser());
            case "token" -> StringValue.of(slashCommandInteraction.getToken());
            case "arguments" -> ListValue.wrap((slashCommandInteraction.getArguments().stream().map(SlashCommandInteractionOptionValue::new)));
            default -> Value.NULL;
        };
    }

    @Override
    public Value in(Value value1) {
        return getProperty(value1.getString());
    }

    @Override
    public String getTypeString() {
        return "dc_slash_command_interaction";
    }

    @Override
    public String getString() {
        return slashCommandInteraction.toString();
    }

    @Override
    public boolean getBoolean() {
        return true;
    }

    @Override
    public NbtElement toTag(boolean b) {
        return null;
    }

    public SlashCommandInteraction getSlashCommandInteraction() {
        return slashCommandInteraction;
    }

    @Override
    public InteractionBase getInteractionBase() {
        return slashCommandInteraction;
    }
}
