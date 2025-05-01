package net.replaceitem.discarpet.script.parsable.parsables.embeds;

import carpet.script.value.MapValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.utils.FileUpload;
import net.replaceitem.discarpet.script.parsable.DirectParsable;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.values.UserValue;

import javax.annotation.Nullable;

@ParsableClass(name = "embed_author")
public class EmbedAuthorParsable implements DirectParsable {
    
    String name;
    @OptionalField
    String url;
    @OptionalField
    Value icon;
    
    @Nullable
    private FileUpload fileUpload = null;

    public void apply(EmbedBuilder embedBuilder) {
        EmbedParsable.EmbedImage embedImage = EmbedParsable.handleImage(icon);
        //noinspection resource
        if(embedImage.fileUpload() != null) this.fileUpload = embedImage.fileUpload();
        embedBuilder.setAuthor(name, url, embedImage.url());
    }

    @Override
    public boolean tryParseDirectly(Value value) {
        if(value instanceof UserValue userValue) {
            User user = userValue.getDelegate();
            this.name = user.getName();
            this.icon = StringValue.of(user.getEffectiveAvatarUrl());
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
