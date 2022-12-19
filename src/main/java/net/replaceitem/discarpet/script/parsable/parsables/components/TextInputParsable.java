package net.replaceitem.discarpet.script.parsable.parsables.components;

import net.replaceitem.discarpet.script.parsable.Optional;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;
import org.javacord.api.entity.message.component.TextInput;
import org.javacord.api.entity.message.component.TextInputBuilder;
import org.javacord.api.entity.message.component.TextInputStyle;

@ParsableClass(name = "text_input")
public class TextInputParsable implements ParsableConstructor<TextInput> {
    
    String id;
    TextInputStyle style;
    String label;
    @Optional Integer min_length;
    @Optional Integer max_length;
    @Optional Boolean required = true;
    @Optional String value = "";
    @Optional String placeholder;
    
    @Override
    public TextInput construct() {
        TextInputBuilder textInputBuilder = new TextInputBuilder(style, id, label);
        textInputBuilder.setMinimumLength(min_length);
        textInputBuilder.setMaximumLength(max_length);
        textInputBuilder.setRequired(required);
        textInputBuilder.setValue(value);
        textInputBuilder.setPlaceholder(placeholder);
        return textInputBuilder.build();
    }
}
