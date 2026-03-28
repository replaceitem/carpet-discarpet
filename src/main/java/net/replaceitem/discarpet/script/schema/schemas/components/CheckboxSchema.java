package net.replaceitem.discarpet.script.schema.schemas.components;

import carpet.script.Context;
import net.dv8tion.jda.api.components.checkbox.Checkbox;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;

@SchemaClass(name = "checkbox")
public class CheckboxSchema implements SchemaConstructor<Checkbox> {
    String id;
    @OptionalField
    Boolean is_default = true;

    @Override
    public Checkbox construct(Context context) {
        return Checkbox.of(id, is_default);
    }
}
