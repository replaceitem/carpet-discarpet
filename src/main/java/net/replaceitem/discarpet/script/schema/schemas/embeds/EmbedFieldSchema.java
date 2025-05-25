package net.replaceitem.discarpet.script.schema.schemas.embeds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;

@SchemaClass(name = "embed_field")
public class EmbedFieldSchema {
    String name;
    String value;
    @OptionalField
    Boolean inline = false;
    
    public void apply(EmbedBuilder embedBuilder) {
        embedBuilder.addField(name, value, inline);
    }
}
