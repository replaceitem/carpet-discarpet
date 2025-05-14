package net.replaceitem.discarpet.script.parsable.parsables.embeds;

import carpet.script.Context;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.utils.FileUpload;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;
import net.replaceitem.discarpet.script.parsable.parsables.AttachmentParsable;
import net.replaceitem.discarpet.script.parsable.parsables.FileParsable;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ParsableClass(name = "embed")
public class EmbedParsable implements ParsableConstructor<EmbedParsable.EmbedWithAttachments> {
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
    FileParsable.AbstractFile image;
    @OptionalField @Nullable
    FileParsable.AbstractFile thumbnail;
    @OptionalField @Nullable
    Instant timestamp;
    
    @Override
    public EmbedWithAttachments construct(Context context) {
        List<FileUpload> fileUploads = new ArrayList<>(0);
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
            FileParsable.AttachableUrl attachableUrl = image.asUrl();
            attachableUrl.optAttachment().ifPresent(fileUploads::add);
            embedBuilder.setImage(attachableUrl.url());
        }
        if(thumbnail != null) {
            FileParsable.AttachableUrl attachableUrl = thumbnail.asUrl();
            attachableUrl.optAttachment().ifPresent(fileUploads::add);
            embedBuilder.setThumbnail(attachableUrl.url());
        }
        
        return new EmbedWithAttachments(embedBuilder.build(), fileUploads);
    }

    public static class EmbedWithAttachments extends AttachmentParsable.WithAttachments<MessageEmbed> {
        public EmbedWithAttachments(MessageEmbed data, List<FileUpload> fileUploads) {
            super(data, fileUploads);
        }
    }
}
