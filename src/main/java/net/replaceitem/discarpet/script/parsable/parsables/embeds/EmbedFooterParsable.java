package net.replaceitem.discarpet.script.parsable.parsables.embeds;

import carpet.script.value.Value;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.utils.FileUpload;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import org.jetbrains.annotations.Nullable;

@ParsableClass(name = "embed_footer")
public class EmbedFooterParsable {

    String text;
    @OptionalField @Nullable
    Value icon;

    @Nullable
    private FileUpload fileUpload = null;

    public void apply(EmbedBuilder embedBuilder) {
        @Nullable String iconUrl = null;
        if(icon != null) {
            EmbedParsable.EmbedImage embedImage = EmbedParsable.handleImage(icon);
            //noinspection resource
            if (embedImage.fileUpload() != null) this.fileUpload = embedImage.fileUpload();
            iconUrl = embedImage.url();
        }
        embedBuilder.setFooter(text, iconUrl);
    }

    @Nullable
    public FileUpload getFileUpload() {
        return fileUpload;
    }
}
