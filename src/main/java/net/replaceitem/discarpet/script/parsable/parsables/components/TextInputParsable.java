package net.replaceitem.discarpet.script.parsable.parsables.components;

import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;

@ParsableClass(name = "text_input")
public class TextInputParsable implements ParsableConstructor<TextInput> {
    
    String id;
    TextInputStyle style;
    String label;
    @OptionalField
    Integer min_length;
    @OptionalField
    Integer max_length;
    @OptionalField
    Boolean required = true;
    @OptionalField
    String value = null;
    @OptionalField
    String placeholder;
    
    @Override
    public TextInput construct() {
        TextInput.Builder builder = TextInput.create(id, label, style);
        if(min_length != null) builder.setMinLength(min_length);
        if(max_length != null) builder.setMaxLength(max_length);
        builder.setRequired(required);
        builder.setValue(value);
        builder.setPlaceholder(placeholder);
        return builder.build();
    }
}
