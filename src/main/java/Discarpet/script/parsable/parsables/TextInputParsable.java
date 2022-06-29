package Discarpet.script.parsable.parsables;

import Discarpet.script.parsable.Optional;
import Discarpet.script.parsable.ParsableClass;
import Discarpet.script.parsable.ParsableConstructor;
import org.javacord.api.entity.message.component.TextInput;
import org.javacord.api.entity.message.component.TextInputBuilder;
import org.javacord.api.entity.message.component.TextInputStyle;

@ParsableClass(name = "text_input")
public class TextInputParsable implements ParsableConstructor<TextInput> {
    
    String id;
    String style;
    String label;
    @Optional Integer min_length;
    @Optional Integer max_length;
    @Optional Boolean required = true;
    @Optional String value = "";
    @Optional String placeholder;
    
    @Override
    public TextInput construct() {
        TextInputBuilder textInputBuilder = new TextInputBuilder(
                TextInputStyle.valueOf(style.toUpperCase()),
                id,
                label
        );
        textInputBuilder.setMinimumLength(min_length);
        textInputBuilder.setMaximumLength(max_length);
        textInputBuilder.setRequired(required);
        textInputBuilder.setValue(value);
        textInputBuilder.setPlaceholder(placeholder);
        return textInputBuilder.build();
    }
}
