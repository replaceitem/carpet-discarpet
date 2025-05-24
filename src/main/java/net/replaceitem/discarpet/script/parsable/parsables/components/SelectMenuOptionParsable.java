package net.replaceitem.discarpet.script.parsable.parsables.components;

import carpet.script.Context;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;
import org.jetbrains.annotations.Nullable;

@ParsableClass(name = "select_menu_option")
public class SelectMenuOptionParsable implements ParsableConstructor<SelectOption> {
    
    String value;
    String label;
    @OptionalField @Nullable
    Emoji emoji;
    @OptionalField @Nullable
    String description;
    @OptionalField
    Boolean default_option = false;
    
    
    @Override
    public SelectOption construct(Context context) {
        return SelectOption.of(label, value)
                .withEmoji(emoji)
                .withDescription(description)
                .withDefault(default_option);
    }
}
