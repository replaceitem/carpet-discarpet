package net.replaceitem.discarpet.script.schema.schemas.components;

import carpet.script.Context;
import net.dv8tion.jda.api.components.separator.Separator;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;

@SchemaClass(name = "separator")
public class SeparatorSchema implements SchemaConstructor<Separator> {

    @OptionalField
    Boolean is_divider = true;
    @OptionalField
    Separator.Spacing spacing = Separator.Spacing.SMALL;

    @Override
    public Separator construct(Context context) {
        return Separator.create(is_divider, spacing);
    }
}
