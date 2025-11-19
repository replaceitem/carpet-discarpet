package net.replaceitem.discarpet.script.schema.schemas.components;

import carpet.script.Context;
import carpet.script.exception.InternalExpressionException;
import net.dv8tion.jda.api.components.Component;
import net.dv8tion.jda.api.components.container.Container;
import net.dv8tion.jda.api.components.container.ContainerChildComponent;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

@SchemaClass(name = "container")
public class ContainerSchema implements SchemaConstructor<Container> {

    List<Component> components;
    @OptionalField @Nullable
    Color accent_color;
    @OptionalField
    Boolean spoiler = false;


    @Override
    public Container construct(Context context) {
        var containerChildren = this.components.stream().mapMulti((Component component, Consumer<ContainerChildComponent> consumer) -> {
            if (!(component instanceof ContainerChildComponent childComponent))
                throw new InternalExpressionException("Components of type " + component.getType().toString().toLowerCase() + " cannot be used inside a container.");
            consumer.accept(childComponent);
        }).toList();
        return Container.of(containerChildren).withAccentColor(accent_color).withSpoiler(spoiler);
    }
}
