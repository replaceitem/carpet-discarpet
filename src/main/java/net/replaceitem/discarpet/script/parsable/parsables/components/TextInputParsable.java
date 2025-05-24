package net.replaceitem.discarpet.script.parsable.parsables.components;

import carpet.script.Context;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;
import org.jetbrains.annotations.Nullable;

@ParsableClass(name = "text_input")
public class TextInputParsable implements ParsableConstructor<TextInput> {
    
    String id;
    TextInputStyle style;
    String label;
    @OptionalField @Nullable
    Integer min_length;
    @OptionalField @Nullable
    Integer max_length;
    @OptionalField
    Boolean required = true;
    @OptionalField @Nullable
    String value = null;
    @OptionalField @Nullable
    String placeholder;
    
    @Override
    public TextInput construct(Context context) {
        TextInput.Builder builder = TextInput.create(id, label, style);
        if(min_length != null) builder.setMinLength(min_length);
        if(max_length != null) builder.setMaxLength(max_length);
        builder.setRequired(required);
        builder.setValue(value);
        builder.setPlaceholder(placeholder);
        return builder.build();
    }
}
