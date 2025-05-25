package net.replaceitem.discarpet.script.schema.schemas.embeds;

import carpet.script.Context;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.utils.FileUpload;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;
import net.replaceitem.discarpet.script.schema.schemas.AttachmentSchema;
import net.replaceitem.discarpet.script.schema.schemas.FileSchema;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@SchemaClass(name = "embed")
public class EmbedSchema implements SchemaConstructor<EmbedSchema.EmbedWithAttachments> {
    @OptionalField @Nullable
    String title;
    @OptionalField @Nullable
    String url;
    @OptionalField @Nullable
    String description;
    @OptionalField @Nullable
    EmbedAuthorSchema author;
    @OptionalField
    List<EmbedFieldSchema> fields = List.of();
    @OptionalField @Nullable
    Color color;
    @OptionalField @Nullable
    EmbedFooterSchema footer;
    @OptionalField @Nullable
    FileSchema.AbstractFile image;
    @OptionalField @Nullable
    FileSchema.AbstractFile thumbnail;
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
        
        for (EmbedFieldSchema field : fields) {
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
            FileSchema.AttachableUrl attachableUrl = image.asUrl();
            attachableUrl.optAttachment().ifPresent(fileUploads::add);
            embedBuilder.setImage(attachableUrl.url());
        }
        if(thumbnail != null) {
            FileSchema.AttachableUrl attachableUrl = thumbnail.asUrl();
            attachableUrl.optAttachment().ifPresent(fileUploads::add);
            embedBuilder.setThumbnail(attachableUrl.url());
        }
        
        return new EmbedWithAttachments(embedBuilder.build(), fileUploads);
    }

    public static class EmbedWithAttachments extends AttachmentSchema.WithAttachments<MessageEmbed> {
        public EmbedWithAttachments(MessageEmbed data, List<FileUpload> fileUploads) {
            super(data, fileUploads);
        }
    }
}
