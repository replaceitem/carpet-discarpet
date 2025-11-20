package net.replaceitem.discarpet.script.schema.schemas.components;

import carpet.script.Context;
import net.dv8tion.jda.api.components.Component;
import net.dv8tion.jda.api.components.ModalTopLevelComponent;
import net.dv8tion.jda.api.components.label.LabelChildComponent;
import net.dv8tion.jda.api.modals.Modal;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;
import net.replaceitem.discarpet.script.util.ComponentUtil;

import java.util.List;

@SchemaClass(name = "modal")
public class ModalSchema implements SchemaConstructor<Modal> {
    
    String id;
    String title;
    List<Component> components;

    @Override
    public Modal construct(Context context) {
        var modalComponents = ComponentUtil.ensureComponentType(
                this.components, ModalTopLevelComponent.class,
                component -> {
                    var additionalInfo = component instanceof LabelChildComponent ? " Consider wrapping it inside a label." : "";
                    return "Components of type %s cannot be used inside a modal.%s".formatted(component.getType().toString().toLowerCase(), additionalInfo);
                }
        );
        return Modal.create(id, title).addComponents(modalComponents).build();
    }
}
