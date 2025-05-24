package net.replaceitem.discarpet.script.parsable.parsables.components;

import carpet.script.Context;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;

import java.util.List;

@ParsableClass(name = "modal")
public class ModalParsable implements ParsableConstructor<Modal> {
    
    String id;
    String title;
    List<List<ItemComponent>> components;

    @Override
    public Modal construct(Context context) {
        return Modal.create(id, title).addComponents(components.stream().map(ActionRow::of).toList()).build();
    }
}
