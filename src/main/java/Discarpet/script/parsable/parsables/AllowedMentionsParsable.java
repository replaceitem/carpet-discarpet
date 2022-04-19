package Discarpet.script.parsable.parsables;

import Discarpet.script.parsable.Optional;
import Discarpet.script.parsable.ParsableClass;
import Discarpet.script.parsable.ParsableConstructor;
import org.javacord.api.entity.message.mention.AllowedMentions;
import org.javacord.api.entity.message.mention.AllowedMentionsBuilder;

import java.util.List;

@ParsableClass(name = "allowed_mentions")
public class AllowedMentionsParsable implements ParsableConstructor<AllowedMentions> {

    @Optional Boolean mention_roles = false;
    @Optional Boolean mention_users = false;
    @Optional Boolean mention_everyone = false;
    @Optional List<String> roles;
    @Optional List<String> users;

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
