package net.replaceitem.discarpet.script.parsable.parsables.components;

import net.replaceitem.discarpet.script.parsable.Applicable;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.util.ValueUtil;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.HighLevelComponent;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.interaction.InteractionBase;

import java.util.List;

@ParsableClass(name = "modal")
public class ModalParsable implements Applicable<InteractionBase> {
    
    String id;
    String title;
    List<List<LowLevelComponent>> components;
    
    @Override
    public void apply(InteractionBase interactionBase) {
        List<HighLevelComponent> highLevelComponents = components.stream().map(lowLevelComponents -> ((HighLevelComponent) ActionRow.of(lowLevelComponents))).toList();
        ValueUtil.awaitFuture(interactionBase.respondWithModal(id, title, highLevelComponents),"Error sending 'respond_with_modal' response to interaction");
    }
}
