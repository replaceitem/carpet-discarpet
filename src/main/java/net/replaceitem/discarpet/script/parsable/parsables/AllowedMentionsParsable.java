package net.replaceitem.discarpet.script.parsable.parsables;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.utils.messages.MessageRequest;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;

import java.util.EnumSet;
import java.util.List;

@ParsableClass(name = "allowed_mentions")
public class AllowedMentionsParsable {

    @OptionalField
    Boolean mention_roles = false;
    @OptionalField
    Boolean mention_users = false;
    @OptionalField
    Boolean mention_everyone = false;
    @OptionalField
    List<String> roles = List.of();
    @OptionalField
    List<String> users = List.of();

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void apply(MessageRequest<?> messageRequest) {
        EnumSet<Message.MentionType> mentionTypes = EnumSet.noneOf(Message.MentionType.class);
        if(mention_roles) mentionTypes.add(Message.MentionType.ROLE);
        if(mention_users) mentionTypes.add(Message.MentionType.USER);
        if(mention_everyone) mentionTypes.add(Message.MentionType.EVERYONE);
        // TODO add and document other mention types
        messageRequest.setAllowedMentions(mentionTypes);
        
        // TODO add this
        //messageRequest.mentionRepliedUser()
        
        if(roles != null) messageRequest.mentionRoles(roles);
        if(users != null) messageRequest.mentionRoles(users);
    }
}
