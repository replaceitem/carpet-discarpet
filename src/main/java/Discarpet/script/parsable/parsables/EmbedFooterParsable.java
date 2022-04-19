package Discarpet.script.parsable.parsables;

import Discarpet.script.parsable.Applicable;
import Discarpet.script.parsable.Optional;
import Discarpet.script.parsable.ParsableClass;
import org.javacord.api.entity.message.embed.EmbedBuilder;

@ParsableClass(name = "embed_footer")
public class EmbedFooterParsable implements Applicable<EmbedBuilder> {

    String text;
    @Optional String icon;
    
    @Override
    public void apply(EmbedBuilder embedBuilder) {
        embedBuilder.setFooter(text,icon);
    }
}
