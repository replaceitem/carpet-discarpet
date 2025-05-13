package net.replaceitem.discarpet.script.parsable.parsables;

import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;

@ParsableClass(name = "respond_later_data")
public class RespondLaterDataParsable {
    @OptionalField
    Boolean ephemeral = false;
    
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void apply(ReplyCallbackAction replyCallbackAction) {
        replyCallbackAction.setEphemeral(ephemeral);
    }
}
