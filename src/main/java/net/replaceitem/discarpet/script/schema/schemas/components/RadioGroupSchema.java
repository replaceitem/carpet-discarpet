package net.replaceitem.discarpet.script.schema.schemas.components;

import carpet.script.Context;
import net.dv8tion.jda.api.components.radiogroup.RadioGroup;
import net.dv8tion.jda.api.components.radiogroup.RadioGroupOption;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;

import java.util.List;

@SchemaClass(name = "radio_group")
public class RadioGroupSchema implements SchemaConstructor<RadioGroup> {
    String id;
    List<RadioGroupOption> options;
    @OptionalField
    Boolean required = true;

    @Override
    public RadioGroup construct(Context context) {
        return RadioGroup.create(id).addOptions(options).setRequired(required).build();
    }
}
