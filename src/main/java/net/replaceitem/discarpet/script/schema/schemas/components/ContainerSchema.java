package net.replaceitem.discarpet.script.schema.schemas.components;

import carpet.script.Context;
import net.dv8tion.jda.api.components.Component;
import net.dv8tion.jda.api.components.container.Container;
import net.dv8tion.jda.api.components.container.ContainerChildComponent;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;
import net.replaceitem.discarpet.script.util.ComponentUtil;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

@SchemaClass(name = "container")
public class ContainerSchema implements SchemaConstructor<Container> {

    List<Component> components;
    @OptionalField @Nullable
    Color accent_color;
    @OptionalField
    Boolean spoiler = false;


    @Override
    public Container construct(Context context) {
        var containerChildren = ComponentUtil.ensureComponentType(
                this.components, ContainerChildComponent.class,
                component -> "Components of type %s cannot be used inside a container.".formatted(component.getType().toString().toLowerCase())
        );
        return Container.of(containerChildren).withAccentColor(accent_color).withSpoiler(spoiler);
    }
}
