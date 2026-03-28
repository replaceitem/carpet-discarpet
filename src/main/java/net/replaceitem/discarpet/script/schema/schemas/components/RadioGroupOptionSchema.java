package net.replaceitem.discarpet.script.schema.schemas.components;

import carpet.script.Context;
import net.dv8tion.jda.api.components.radiogroup.RadioGroupOption;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;
import org.jspecify.annotations.Nullable;

@SchemaClass(name = "radio_group_option")
public class RadioGroupOptionSchema implements SchemaConstructor<RadioGroupOption> {
    String label;
    String value;
    @OptionalField @Nullable
    String description;
    @OptionalField
    Boolean is_default = false;

    @Override
    public RadioGroupOption construct(Context context) {
        return RadioGroupOption.of(label, value, description, is_default);
    }
}
