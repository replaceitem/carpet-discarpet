package net.replaceitem.discarpet.script.values.interactions;

import carpet.script.value.ListValue;
import carpet.script.value.MapValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.replaceitem.discarpet.script.util.ValueUtil;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Collectors;

public class SlashCommandInteractionValue extends ApplicationCommandInteractionValue<SlashCommandInteractionEvent> {
    public SlashCommandInteractionValue(SlashCommandInteractionEvent applicationCommandInteraction) {
        super(applicationCommandInteraction);
    }
    
    public static Value of(@Nullable SlashCommandInteractionEvent slashCommandInteractionEvent) {
        return ValueUtil.ofNullable(slashCommandInteractionEvent, SlashCommandInteractionValue::new);
    }

    @Override
    protected String getDiscordTypeString() {
        return "slash_command_interaction";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "sub_command" -> StringValue.of(delegate.getSubcommandName());
            case "sub_command_group" -> StringValue.of(delegate.getSubcommandGroup());
            case "arguments" -> ListValue.wrap(delegate.getOptions().stream().map(SlashCommandInteractionOptionValue::new));
            case "arguments_by_name" -> MapValue.wrap(delegate.getOptions().stream().collect(Collectors.toMap(o -> StringValue.of(o.getName()), SlashCommandInteractionOptionValue::of)));
            default -> super.getProperty(property);
        };
    }
}
