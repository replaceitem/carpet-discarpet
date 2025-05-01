package net.replaceitem.discarpet.script.parsable.parsables.embeds;

import carpet.script.exception.InternalExpressionException;
import carpet.script.value.Value;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.utils.FileUpload;
import net.replaceitem.discarpet.Discarpet;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;
import net.replaceitem.discarpet.script.util.FileUtil;
import net.replaceitem.discarpet.script.util.ScarpetGraphicsDependency;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ParsableClass(name = "embed")
public class EmbedParsable implements ParsableConstructor<MessageEmbed> {
    @OptionalField @Nullable
    String title;
    @OptionalField @Nullable
    String url;
    @OptionalField @Nullable
    String description;
    @OptionalField @Nullable
    EmbedAuthorParsable author;
    @OptionalField
    List<EmbedFieldParsable> fields = List.of();
    @OptionalField @Nullable
    Color color;
    @OptionalField @Nullable
    EmbedFooterParsable footer;
    @OptionalField @Nullable
    Value image;
    @OptionalField @Nullable
    Value thumbnail;
    @OptionalField @Nullable
    Instant timestamp;

    private final List<FileUpload> fileUploads = new ArrayList<>(0);

    @Override
    public MessageEmbed construct() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(title);
        embedBuilder.setUrl(url);
        embedBuilder.setDescription(description);
        embedBuilder.setColor(color);
        embedBuilder.setTimestamp(timestamp);
        
        for (EmbedFieldParsable field : fields) {
            field.apply(embedBuilder);
        }
        
        if(author != null) {
            if(author.getFileUpload() != null) fileUploads.add(author.getFileUpload());
            author.apply(embedBuilder);
        }
        if(footer != null) {
            if(footer.getFileUpload() != null) fileUploads.add(footer.getFileUpload());
            footer.apply(embedBuilder);
        }
        if(image != null) {
            EmbedImage embedImage = handleImage(image);
            this.fileUploads.add(embedImage.fileUpload());
            embedBuilder.setImage(embedImage.url());
        }
        if(thumbnail != null) {
            EmbedImage embedImage = handleImage(thumbnail);
            this.fileUploads.add(embedImage.fileUpload());
            embedBuilder.setThumbnail(embedImage.url());
        }
        
        return embedBuilder.build();
    }

    public List<FileUpload> getFileUploads() {
        return fileUploads;
    }

    record EmbedImage(String url, @Nullable FileUpload fileUpload) {}

    static EmbedImage handleImage(Value value) {
        if(Discarpet.isScarpetGraphicsInstalled() && ScarpetGraphicsDependency.isPixelAccessible(value)) {
            FileUpload fileUpload = FileUtil.fromImage(value, FileUtil.randomName("png"));
            return new EmbedImage("attachment://" + fileUpload.getName(), fileUpload);
        }
        String valueString = value.getString();
        if(EmbedBuilder.URL_PATTERN.matcher(valueString).matches()) {
            return new EmbedImage(valueString, null);
        }
        File file = new File(valueString);
        if(file.exists()) {
            FileUpload fileUpload = FileUtil.fromFile(file, FileUtil.randomName(FileUtil.getFileExtension(valueString, "png")));
            return new EmbedImage("attachment://" + fileUpload.getName(), fileUpload);
        }
        throw new InternalExpressionException("Expected either a url, file path or image value");
    }
}
