package net.replaceitem.discarpet.script.parsable.parsables.embeds;

import carpet.script.value.MapValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.utils.FileUpload;
import net.replaceitem.discarpet.script.parsable.DirectParsable;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.parsables.FileParsable;
import net.replaceitem.discarpet.script.values.UserValue;
import org.jetbrains.annotations.Nullable;

@ParsableClass(name = "embed_author")
public class EmbedAuthorParsable implements DirectParsable {
    
    String name;
    @OptionalField @Nullable
    String url;
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
        embedBuilder.setAuthor(name, url, iconUrl);
    }

    @Override
    public boolean tryParseDirectly(Value value) {
        if(value instanceof UserValue userValue) {
            User user = userValue.getDelegate();
            this.name = user.getName();
            this.icon = FileParsable.AbstractFile.ofUrl(user.getEffectiveAvatarUrl());
            return true;
        }
        if(!(value instanceof MapValue)) {
            this.name = value.getString();
            return true;
        }
        return false;
    }

    @Nullable
    public FileUpload getFileUpload() {
        return fileUpload;
    }
}
