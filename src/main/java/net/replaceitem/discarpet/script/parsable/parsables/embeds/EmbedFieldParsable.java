package net.replaceitem.discarpet.script.parsable.parsables.embeds;

import net.replaceitem.discarpet.script.parsable.Applicable;
import net.replaceitem.discarpet.script.parsable.Optional;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
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
