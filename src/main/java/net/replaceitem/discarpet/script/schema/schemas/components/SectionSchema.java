package net.replaceitem.discarpet.script.schema.schemas.components;

import carpet.script.Context;
import carpet.script.exception.InternalExpressionException;
import net.dv8tion.jda.api.components.Component;
import net.dv8tion.jda.api.components.section.Section;
import net.dv8tion.jda.api.components.section.SectionAccessoryComponent;
import net.dv8tion.jda.api.components.section.SectionContentComponent;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;
import net.replaceitem.discarpet.script.util.ComponentUtil;

import java.util.List;

@SchemaClass(name = "section")
public class SectionSchema implements SchemaConstructor<Section> {

    List<Component> components;
    Component accessory;

    @Override
    public Section construct(Context context) {
        var containerChildren = ComponentUtil.ensureComponentType(
                this.components, SectionContentComponent.class,
                component -> "Components of type %s cannot be used inside a section.".formatted(component.getType().toString().toLowerCase())
        );
        if(!(this.accessory instanceof SectionAccessoryComponent sectionAccessoryComponent)) {
            throw new InternalExpressionException("Component of type " + this.accessory.getType().toString().toLowerCase() + " cannot be used as the accessory inside a section.");
        }
        return Section.of(sectionAccessoryComponent, containerChildren);
    }
}
