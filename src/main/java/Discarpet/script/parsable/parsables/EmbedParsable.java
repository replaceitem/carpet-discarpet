package Discarpet.script.parsable.parsables;

import Discarpet.Discarpet;
import Discarpet.script.parsable.Optional;
import Discarpet.script.parsable.ParsableClass;
import Discarpet.script.parsable.ParsableConstructor;
import Discarpet.script.util.ScarpetGraphicsDependency;
import carpet.script.value.Value;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.Instant;
import java.util.List;

@ParsableClass(name = "embed")
public class EmbedParsable implements ParsableConstructor<EmbedBuilder> {
    @Optional String title;
    @Optional String url;
    @Optional String description;
    @Optional EmbedAuthorParsable author;
    @Optional List<EmbedFieldParsable> fields = List.of();
    @Optional Color color;
    @Optional EmbedFooterParsable footer;
    @Optional Value image;
    @Optional Value thumbnail;
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
        setImage(embedBuilder);
        setThumbnail(embedBuilder);
        embedBuilder.setTimestamp(timestamp);
        return embedBuilder;
    }
    
    private void setImage(EmbedBuilder embedBuilder) {
        if(Discarpet.isScarpetGraphicsInstalled() && ScarpetGraphicsDependency.isPixelAccessible(image)) {
            BufferedImage bufferedImage = ScarpetGraphicsDependency.getImageFromValue(image);
            embedBuilder.setImage(bufferedImage);
            return;
        }
        String iconString = image.getString();
        File file = new File(iconString);
        if(file.exists()) {
            embedBuilder.setImage(file);
            return;
        }
        embedBuilder.setImage(iconString);
    }

    private void setThumbnail(EmbedBuilder embedBuilder) {
        if(Discarpet.isScarpetGraphicsInstalled() && ScarpetGraphicsDependency.isPixelAccessible(image)) {
            BufferedImage bufferedImage = ScarpetGraphicsDependency.getImageFromValue(image);
            embedBuilder.setThumbnail(bufferedImage);
            return;
        }
        String iconString = image.getString();
        File file = new File(iconString);
        if(file.exists()) {
            embedBuilder.setThumbnail(file);
            return;
        }
        embedBuilder.setThumbnail(iconString);
    }
}
