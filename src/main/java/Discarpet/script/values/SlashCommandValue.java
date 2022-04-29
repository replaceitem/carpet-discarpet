package Discarpet.script.values;

import carpet.script.value.ListValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.minecraft.nbt.NbtElement;
import org.javacord.api.interaction.SlashCommand;

public class SlashCommandValue extends Value {

    private final SlashCommand slashCommand;

    public SlashCommandValue(SlashCommand slashCommand) {
        this.slashCommand = slashCommand;
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "id" -> StringValue.of(slashCommand.getIdAsString());
            case "name" -> StringValue.of(slashCommand.getName());
            case "description" -> StringValue.of(slashCommand.getDescription());
            case "server" -> ServerValue.of(slashCommand.getServer());
            case "options" -> ListValue.wrap(slashCommand.getOptions().stream().map(slashCommandOption -> StringValue.of(slashCommandOption.getName())));
            case "creation_timestamp" -> NumericValue.of(slashCommand.getCreationTimestamp().toEpochMilli());
            default -> Value.NULL;
        };
    }

    public SlashCommand getSlashCommand() {
        return slashCommand;
    }

    @Override
    public Value in(Value value1) {
        return getProperty(value1.getString());
    }

    @Override
    public String getTypeString() {
        return "dc_slash_command";
    }

    @Override
    public String getString() {
        return slashCommand.toString();
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
