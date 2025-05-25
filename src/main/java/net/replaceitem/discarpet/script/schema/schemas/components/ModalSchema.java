package net.replaceitem.discarpet.script.schema.schemas.components;

import carpet.script.Context;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;

import java.util.List;

@SchemaClass(name = "modal")
public class ModalSchema implements SchemaConstructor<Modal> {
    
    String id;
    String title;
    List<List<ItemComponent>> components;

    @Override
    public Modal construct(Context context) {
        return Modal.create(id, title).addComponents(components.stream().map(ActionRow::of).toList()).build();
    }
}
