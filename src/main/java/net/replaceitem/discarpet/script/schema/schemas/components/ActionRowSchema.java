package net.replaceitem.discarpet.script.schema.schemas.components;

import carpet.script.Context;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.ListValue;
import carpet.script.value.Value;
import com.google.common.collect.Streams;
import net.dv8tion.jda.api.components.Component;
import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.components.actionrow.ActionRowChildComponent;
import net.replaceitem.discarpet.script.schema.DirectParsable;
import net.replaceitem.discarpet.script.schema.Parser;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;
import net.replaceitem.discarpet.script.util.ComponentUtil;

import java.util.List;

@SchemaClass(name = "action_row")
public class ActionRowSchema implements SchemaConstructor<ActionRow>, DirectParsable {
    List<Component> components;

    @Override
    public ActionRow construct(Context context) {
        var actionRowChildren = ComponentUtil.ensureComponentType(
                this.components, ActionRowChildComponent.class,
                component -> "Components of type %s cannot be used inside an action row.".formatted(component.getType().toString().toLowerCase())
        );
        return ActionRow.of(actionRowChildren);
    }

    @Override
    public boolean tryParseDirectly(Value value, Context context) {
        if(value instanceof ListValue listValue) {
            this.components = Streams.mapWithIndex(listValue.getItems().stream(), (from, index) -> {
                try {
                    return Parser.parseType(context, from, Component.class);
                } catch (Exception e) {
                    throw new InternalExpressionException("Could not parse action row item " + index + " :\n" + e.getMessage());
                }
            }).toList();
            return true;
        }
        return false;
    }
}
