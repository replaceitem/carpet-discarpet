package net.replaceitem.discarpet.script.parsable.parsables.embeds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;

@ParsableClass(name = "embed_field")
public class EmbedFieldParsable {
    String name;
    String value;
    @OptionalField
    Boolean inline = false;
    
    public void apply(EmbedBuilder embedBuilder) {
        embedBuilder.addField(name, value, inline);
    }
}
