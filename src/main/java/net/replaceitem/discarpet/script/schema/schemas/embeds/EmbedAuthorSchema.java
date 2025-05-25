package net.replaceitem.discarpet.script.schema.schemas.embeds;

import carpet.script.value.MapValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.utils.FileUpload;
import net.replaceitem.discarpet.script.schema.DirectParsable;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.schemas.FileSchema;
import net.replaceitem.discarpet.script.values.UserValue;
import org.jetbrains.annotations.Nullable;

@SchemaClass(name = "embed_author")
public class EmbedAuthorSchema implements DirectParsable {
    
    String name;
    @OptionalField @Nullable
    String url;
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
        embedBuilder.setAuthor(name, url, iconUrl);
    }

    @Override
    public boolean tryParseDirectly(Value value) {
        if(value instanceof UserValue userValue) {
            User user = userValue.getDelegate();
            this.name = user.getName();
            this.icon = FileSchema.AbstractFile.ofUrl(user.getEffectiveAvatarUrl(), null);
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
