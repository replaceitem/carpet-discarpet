package net.replaceitem.discarpet.script.schema.schemas.components;

import carpet.script.Context;
import carpet.script.exception.InternalExpressionException;
import net.dv8tion.jda.api.components.Component;
import net.dv8tion.jda.api.components.label.Label;
import net.dv8tion.jda.api.components.label.LabelChildComponent;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;
import org.jetbrains.annotations.Nullable;

@SchemaClass(name = "label")
public class LabelSchema implements SchemaConstructor<Label> {
    
    String label;
    @OptionalField @Nullable
    String description;
    Component child;

    @Override
    public Label construct(Context context) {
        if(!(child instanceof LabelChildComponent labelChildComponent)) throw new InternalExpressionException("Components of type " + child.getType().toString().toLowerCase() + " cannot be used inside a label.");
        return Label.of(this.label, this.description, labelChildComponent);
    }
}
