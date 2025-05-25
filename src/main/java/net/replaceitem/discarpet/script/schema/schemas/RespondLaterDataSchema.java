package net.replaceitem.discarpet.script.schema.schemas;

import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;

@SchemaClass(name = "respond_later_data")
public class RespondLaterDataSchema {
    @OptionalField
    Boolean ephemeral = false;
    
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void apply(ReplyCallbackAction replyCallbackAction) {
        replyCallbackAction.setEphemeral(ephemeral);
    }
}
