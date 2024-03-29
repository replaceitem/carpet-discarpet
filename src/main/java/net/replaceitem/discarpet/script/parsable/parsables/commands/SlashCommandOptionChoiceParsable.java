package net.replaceitem.discarpet.script.parsable.parsables.commands;

import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;
import org.javacord.api.interaction.SlashCommandOptionChoice;
import org.javacord.api.interaction.SlashCommandOptionChoiceBuilder;

@ParsableClass(name = "slash_command_option_choice")
public class SlashCommandOptionChoiceParsable implements ParsableConstructor<SlashCommandOptionChoice> {
    
    String name;
    String value;
    
    @Override
    public SlashCommandOptionChoice construct() {
        SlashCommandOptionChoiceBuilder slashCommandOptionChoiceBuilder = new SlashCommandOptionChoiceBuilder();
        slashCommandOptionChoiceBuilder.setName(name);
        slashCommandOptionChoiceBuilder.setValue(value);
        return slashCommandOptionChoiceBuilder.build();
    }
}
