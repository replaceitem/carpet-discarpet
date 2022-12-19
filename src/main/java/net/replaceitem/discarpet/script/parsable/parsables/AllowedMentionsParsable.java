package net.replaceitem.discarpet.script.parsable.parsables;

import net.replaceitem.discarpet.script.parsable.Optional;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;
import org.javacord.api.entity.message.mention.AllowedMentions;
import org.javacord.api.entity.message.mention.AllowedMentionsBuilder;

import java.util.List;

@ParsableClass(name = "allowed_mentions")
public class AllowedMentionsParsable implements ParsableConstructor<AllowedMentions> {

    @Optional Boolean mention_roles = false;
    @Optional Boolean mention_users = false;
    @Optional Boolean mention_everyone = false;
    @Optional List<String> roles = List.of();
    @Optional List<String> users = List.of();

    @Override
    public AllowedMentions construct() {
        AllowedMentionsBuilder allowedMentionsBuilder = new AllowedMentionsBuilder();
        allowedMentionsBuilder.setMentionRoles(mention_roles);
        allowedMentionsBuilder.setMentionUsers(mention_users);
        allowedMentionsBuilder.setMentionEveryoneAndHere(mention_everyone);

        for (String role : roles) {
            allowedMentionsBuilder.addRole(role);
        }
        for (String user : users) {
            allowedMentionsBuilder.addUser(user);
        }
        return allowedMentionsBuilder.build();
    }
}
