package net.replaceitem.discarpet.script.schema.schemas.components;

import carpet.script.Context;
import net.dv8tion.jda.api.components.textinput.TextInput;
import net.dv8tion.jda.api.components.textinput.TextInput.Builder;
import net.dv8tion.jda.api.components.textinput.TextInputStyle;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;
import org.jetbrains.annotations.Nullable;

@SchemaClass(name = "text_input")
public class TextInputSchema implements SchemaConstructor<TextInput> {
    
    String id;
    TextInputStyle style;
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
        Builder builder = TextInput.create(id, style);
        if(min_length != null) builder.setMinLength(min_length);
        if(max_length != null) builder.setMaxLength(max_length);
        builder.setRequired(required);
        builder.setValue(value);
        builder.setPlaceholder(placeholder);
        return builder.build();
    }
}
