package net.replaceitem.discarpet.script.schema.schemas.components;

import carpet.script.Context;
import net.dv8tion.jda.api.components.checkboxgroup.CheckboxGroup;
import net.dv8tion.jda.api.components.checkboxgroup.CheckboxGroupOption;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;

import java.util.List;

@SchemaClass(name = "checkbox_group")
public class CheckboxGroupSchema implements SchemaConstructor<CheckboxGroup> {
    String id;
    List<CheckboxGroupOption> options;
    @OptionalField
    Boolean required = true;
    @OptionalField
    Integer min_values = -1;
    @OptionalField
    Integer max_values = -1;

    @Override
    public CheckboxGroup construct(Context context) {
        return CheckboxGroup.create(id).addOptions(options).setRequired(required).setMinValues(min_values).setMaxValues(max_values).build();
    }
}
