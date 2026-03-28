package net.replaceitem.discarpet.script.schema.schemas.components;

import carpet.script.Context;
import net.dv8tion.jda.api.components.checkboxgroup.CheckboxGroupOption;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;
import org.jspecify.annotations.Nullable;

@SchemaClass(name = "checkbox_group_option")
public class CheckboxGroupOptionSchema implements SchemaConstructor<CheckboxGroupOption> {
    String value;
    String label;
    @OptionalField @Nullable
    String description;
    @OptionalField
    Boolean is_default = false;

    @Override
    public CheckboxGroupOption construct(Context context) {
        return CheckboxGroupOption.of(label, value, description, is_default);
    }
}
