package Discarpet.script.parsable.parsables.embeds;

import Discarpet.script.parsable.Applicable;
import Discarpet.script.parsable.Optional;
import Discarpet.script.parsable.ParsableClass;
import org.javacord.api.entity.message.embed.EmbedBuilder;

@ParsableClass(name = "embed_field")
public class EmbedFieldParsable implements Applicable<EmbedBuilder> {
    String name;
    String value;
    @Optional Boolean inline = false;
    
    @Override
    public void apply(EmbedBuilder embedBuilder) {
        embedBuilder.addField(name, value, inline);
    }
}
