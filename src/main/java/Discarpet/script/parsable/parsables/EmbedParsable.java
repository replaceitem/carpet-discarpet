package Discarpet.script.parsable.parsables;

import Discarpet.script.parsable.Optional;
import Discarpet.script.parsable.ParsableClass;
import Discarpet.script.parsable.ParsableConstructor;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;
import java.time.Instant;
import java.util.List;

@ParsableClass(name = "embed")
public class EmbedParsable implements ParsableConstructor<EmbedBuilder> {
    @Optional String title;
    @Optional String url;
    @Optional String description;
    @Optional EmbedAuthorParsable author;
    @Optional List<EmbedFieldParsable> fields;
    @Optional Color color;
    @Optional EmbedFooterParsable footer;
    @Optional String image;
    @Optional String thumbnail;
    @Optional Instant timestamp;
    
    

    @Override
    public EmbedBuilder construct() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(title);
        embedBuilder.setUrl(url);
        embedBuilder.setDescription(description);
        if(author!=null) author.apply(embedBuilder);
        for (EmbedFieldParsable field : fields) {
            field.apply(embedBuilder);
        }
        embedBuilder.setColor(color);
        if(footer!=null) footer.apply(embedBuilder);
        embedBuilder.setImage(image);
        embedBuilder.setThumbnail(thumbnail);
        embedBuilder.setTimestamp(timestamp);
        return embedBuilder;
    }
}
