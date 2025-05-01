package net.replaceitem.discarpet.script.parsable.parsables.embeds;

import carpet.script.value.Value;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.utils.FileUpload;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;

import javax.annotation.Nullable;

@ParsableClass(name = "embed_footer")
public class EmbedFooterParsable {

    String text;
    @OptionalField
    Value icon;

    @Nullable
    private FileUpload fileUpload = null;

    public void apply(EmbedBuilder embedBuilder) {
        EmbedParsable.EmbedImage embedImage = EmbedParsable.handleImage(icon);
        //noinspection resource
        if(embedImage.fileUpload() != null) this.fileUpload = embedImage.fileUpload();
        embedBuilder.setFooter(text, embedImage.url());
    }

    @Nullable
    public FileUpload getFileUpload() {
        return fileUpload;
    }
}
