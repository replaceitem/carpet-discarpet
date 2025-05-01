package net.replaceitem.discarpet.script.parsable.parsables.components;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;

@ParsableClass(name = "select_menu_option")
public class SelectMenuOptionParsable implements ParsableConstructor<SelectOption> {
    
    String value;
    String label;
    @OptionalField
    Emoji emoji;
    @OptionalField
    String description;
    @OptionalField
    Boolean default_option = false;
    
    
    @Override
    public SelectOption construct() {
        return SelectOption.of(label, value)
                .withEmoji(emoji)
                .withDescription(description)
                .withDefault(default_option);
    }
}
