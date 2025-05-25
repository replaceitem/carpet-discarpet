package net.replaceitem.discarpet.script.schema.schemas.embeds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.utils.FileUpload;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.schemas.FileSchema;
import org.jetbrains.annotations.Nullable;

@SchemaClass(name = "embed_footer")
public class EmbedFooterSchema {

    String text;
    @OptionalField @Nullable
    FileSchema.AbstractFile icon;

    @Nullable
    private FileUpload fileUpload = null;

    public void apply(EmbedBuilder embedBuilder) {
        @Nullable String iconUrl = null;
        if(icon != null) {
            FileSchema.AttachableUrl attachableUrl = icon.asUrl();
            attachableUrl.optAttachment().ifPresent(upload -> this.fileUpload = upload);
            iconUrl = attachableUrl.url();
        }
        embedBuilder.setFooter(text, iconUrl);
    }

    @Nullable
    public FileUpload getFileUpload() {
        return fileUpload;
    }
}
