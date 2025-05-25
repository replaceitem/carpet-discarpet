package net.replaceitem.discarpet.script.schema.schemas.components;

import carpet.script.Context;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;
import org.jetbrains.annotations.Nullable;

@SchemaClass(name = "select_menu_option")
public class SelectMenuOptionSchema implements SchemaConstructor<SelectOption> {
    
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
