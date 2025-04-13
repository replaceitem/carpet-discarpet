package net.replaceitem.discarpet.script.parsable.parsables.embeds;

import carpet.script.value.Value;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.utils.FileUpload;
import net.replaceitem.discarpet.Discarpet;
import net.replaceitem.discarpet.script.parsable.Optional;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;
import net.replaceitem.discarpet.script.util.ScarpetGraphicsDependency;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ParsableClass(name = "embed")
public class EmbedParsable implements ParsableConstructor<MessageEmbed> {
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
    
    private final List<FileUpload> fileUploads = new ArrayList<>(0);

    @Override
    public MessageEmbed construct() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(title);
        embedBuilder.setUrl(url);
        embedBuilder.setDescription(description);
        if(author != null) author.apply(embedBuilder);
        for (EmbedFieldParsable field : fields) {
            field.apply(embedBuilder);
        }
        embedBuilder.setColor(color);
        if(footer!=null) footer.apply(embedBuilder);
        if(image != null) setImage(embedBuilder);
        if(thumbnail != null) setThumbnail(embedBuilder);
        embedBuilder.setTimestamp(timestamp);
        return embedBuilder.build();
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
        if(Discarpet.isScarpetGraphicsInstalled() && ScarpetGraphicsDependency.isPixelAccessible(thumbnail)) {
            BufferedImage bufferedImage = ScarpetGraphicsDependency.getImageFromValue(thumbnail);
            // TODO make file upload creation from image/url/file/bytes generalized
            fileUploads.add();
            embedBuilder.setThumbnail(bufferedImage);
            return;
        }
        String iconString = thumbnail.getString();
        File file = new File(iconString);
        if(file.exists()) {
            embedBuilder.setThumbnail(file);
            return;
        }
        embedBuilder.setThumbnail(iconString);
    }
}
