package net.replaceitem.discarpet.script.schema.schemas;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.utils.messages.MessageRequest;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;

import java.util.EnumSet;
import java.util.List;

@SchemaClass(name = "allowed_mentions")
public class AllowedMentionsSchema {

    @OptionalField
    Boolean mention_roles = false;
    @OptionalField
    Boolean mention_users = false;
    @OptionalField
    Boolean mention_channels = false;
    @OptionalField
    Boolean mention_emojis = false;
    @OptionalField
    Boolean mention_slash_commands = false;
    @OptionalField
    Boolean mention_here = false;
    @OptionalField
    Boolean mention_everyone = false;
    
    @OptionalField
    Boolean mention_replied_user = false;
    
    @OptionalField
    List<String> roles = List.of();
    @OptionalField
    List<String> users = List.of();

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void apply(MessageRequest<?> messageRequest) {
        EnumSet<Message.MentionType> mentionTypes = EnumSet.noneOf(Message.MentionType.class);
        if(mention_roles) mentionTypes.add(Message.MentionType.ROLE);
        if(mention_users) mentionTypes.add(Message.MentionType.USER);
        if(mention_channels) mentionTypes.add(Message.MentionType.CHANNEL);
        if(mention_emojis) mentionTypes.add(Message.MentionType.EMOJI);
        if(mention_slash_commands) mentionTypes.add(Message.MentionType.SLASH_COMMAND);
        if(mention_here) mentionTypes.add(Message.MentionType.HERE);
        if(mention_everyone) mentionTypes.add(Message.MentionType.EVERYONE);
        messageRequest.setAllowedMentions(mentionTypes);
        
        messageRequest.mentionRepliedUser(mention_replied_user);
                
        if(roles != null) messageRequest.mentionRoles(roles);
        if(users != null) messageRequest.mentionRoles(users);
    }
}
