package Discarpet.script.values;

import carpet.script.value.ListValue;
import carpet.script.value.MapValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.minecraft.nbt.NbtElement;
import org.javacord.api.interaction.InteractionBase;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SlashCommandInteractionValue extends Value implements InteractionValue {

    public SlashCommandInteraction slashCommandInteraction;

    public SlashCommandInteractionValue(SlashCommandInteraction slashCommandInteraction) {
        this.slashCommandInteraction = slashCommandInteraction;
    }

    public Value getProperty(String property) {

        switch (property) {
            case "command":
                List<Value> commandPath = new ArrayList<>();
                commandPath.add(StringValue.of(slashCommandInteraction.getCommandName()));

                slashCommandInteraction.getFirstOption().ifPresent(slashCommandInteractionOption -> {
                    if(slashCommandInteractionOption.isSubcommandOrGroup()) {
                        commandPath.add(StringValue.of(slashCommandInteractionOption.getName()));
                        slashCommandInteractionOption.getFirstOption().ifPresent(slashCommandInteractionOption1 -> {
                            if(slashCommandInteractionOption1.isSubcommandOrGroup()) {
                                commandPath.add(StringValue.of(slashCommandInteractionOption1.getName()));
                            }
                        });
                    }
                });
                return new ListValue(commandPath);
            case "options":
                List<SlashCommandInteractionOption> optionList = slashCommandInteraction.getOptions();
                while(optionList.size() != 0 && optionList.get(0).isSubcommandOrGroup()) {
                    optionList = optionList.get(0).getOptions();
                }

                Map<Value,Value> optionMap = new HashMap<>();


                optionList.forEach(slashCommandInteractionOption -> {
                    Optional<String> stringValue = slashCommandInteractionOption.getStringValue();

                    Value val = stringValue.map(StringValue::of).orElseGet(() -> Value.NULL);
                    optionMap.put(StringValue.of(slashCommandInteractionOption.getName()), val);
                });

                return MapValue.wrap(optionMap);

            case "channel":
                if(slashCommandInteraction.getChannel().isEmpty()) return Value.NULL;
                return new ChannelValue(slashCommandInteraction.getChannel().get());
            case "user":
                return new UserValue(slashCommandInteraction.getUser());
            default:
                return Value.NULL;
        }
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
