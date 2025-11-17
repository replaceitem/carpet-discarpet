package net.replaceitem.discarpet.script.schema.schemas.components;

import carpet.script.Context;
import carpet.script.exception.InternalExpressionException;
import net.dv8tion.jda.api.components.Component;
import net.dv8tion.jda.api.components.ModalTopLevelComponent;
import net.dv8tion.jda.api.components.label.LabelChildComponent;
import net.dv8tion.jda.api.modals.Modal;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;

import java.util.List;
import java.util.function.Consumer;

@SchemaClass(name = "modal")
public class ModalSchema implements SchemaConstructor<Modal> {
    
    String id;
    String title;
    List<Component> components;

    @Override
    public Modal construct(Context context) {
        List<ModalTopLevelComponent> modalComponents = this.components.stream().mapMulti((Component component, Consumer<ModalTopLevelComponent> consumer) -> {
            if (!(component instanceof ModalTopLevelComponent modalTopLevelComponent)) {
                var additionalInfo = component instanceof LabelChildComponent ? " Consider wrapping it inside a label." : "";
                throw new InternalExpressionException("Components of type " + component.getType().toString().toLowerCase() + " cannot be used inside a modal." + additionalInfo);
            }
            consumer.accept(modalTopLevelComponent);
        }).toList();
        return Modal.create(id, title).addComponents(modalComponents).build();
    }
}
