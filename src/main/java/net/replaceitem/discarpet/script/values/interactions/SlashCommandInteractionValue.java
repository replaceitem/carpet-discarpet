package net.replaceitem.discarpet.script.values.interactions;

import carpet.script.value.ListValue;
import carpet.script.value.MapValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

import java.util.stream.Collectors;

public class SlashCommandInteractionValue extends ApplicationCommandInteractionValue<SlashCommandInteraction> {
    public SlashCommandInteractionValue(SlashCommandInteraction applicationCommandInteraction) {
        super(applicationCommandInteraction);
    }

    @Override
    protected String getDiscordTypeString() {
        return "slash_command_interaction";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "arguments" -> ListValue.wrap(delegate.getOptions().stream().map(SlashCommandInteractionOptionValue::new));
            case "arguments_by_name" -> MapValue.wrap(delegate.getOptions().stream().collect(Collectors.toMap(o -> StringValue.of(o.getName()), SlashCommandInteractionOptionValue::of)));
            default -> super.getProperty(property);
        };
    }
}
