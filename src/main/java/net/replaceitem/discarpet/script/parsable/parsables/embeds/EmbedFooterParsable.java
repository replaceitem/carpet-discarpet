package net.replaceitem.discarpet.script.parsable.parsables.embeds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.utils.FileUpload;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.parsables.FileParsable;
import org.jetbrains.annotations.Nullable;

@ParsableClass(name = "embed_footer")
public class EmbedFooterParsable {

    String text;
    @OptionalField @Nullable
    FileParsable.AbstractFile icon;

    @Nullable
    private FileUpload fileUpload = null;

    public void apply(EmbedBuilder embedBuilder) {
        @Nullable String iconUrl = null;
        if(icon != null) {
            FileParsable.AttachableUrl attachableUrl = icon.asUrl();
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
