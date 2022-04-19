package Discarpet.script.parsable.parsables;

import Discarpet.script.parsable.Applicable;
import Discarpet.script.parsable.DirectParsable;
import Discarpet.script.parsable.Optional;
import Discarpet.script.parsable.ParsableClass;
import Discarpet.script.values.UserValue;
import carpet.script.value.MapValue;
import carpet.script.value.Value;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

@ParsableClass(name = "embed_author")
public class EmbedAuthorParsable implements Applicable<EmbedBuilder>, DirectParsable {
    
    String name;
    @Optional String url;
    @Optional String icon;
    
    @Override
    public void apply(EmbedBuilder embedBuilder) {
        embedBuilder.setAuthor(name, url, icon);
    }

    @Override
    public boolean tryParseDirectly(Value value) {
        if(value instanceof UserValue userValue) {
            User user = userValue.getUser();
            this.name = user.getName();
            this.icon = user.getAvatar().getUrl().toString();
            return true;
        }
        if(!(value instanceof MapValue)) {
            this.name = value.getString();
            return true;
        }
        return false;
    }
}
