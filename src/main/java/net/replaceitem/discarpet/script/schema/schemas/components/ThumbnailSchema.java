package net.replaceitem.discarpet.script.schema.schemas.components;

import carpet.script.Context;
import net.dv8tion.jda.api.components.thumbnail.Thumbnail;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;
import net.replaceitem.discarpet.script.schema.schemas.FileSchema;
import org.jetbrains.annotations.Nullable;

@SchemaClass(name = "thumbnail")
public class ThumbnailSchema implements SchemaConstructor<Thumbnail> {

    FileSchema.AbstractFile media;
    @OptionalField @Nullable
    String description;
    @OptionalField
    Boolean spoiler = false;

    @Override
    public Thumbnail construct(Context context) {
        // if the file doesn't need to be uploaded, we will only send the url to discord and avoid
        // unnecessarily downloading it through discarpet only to attach it to the message.
        Thumbnail thumbnail = media.requiresUpload()
                ? Thumbnail.fromFile(media.asFileUpload())
                : Thumbnail.fromUrl(media.asUrl().url());
        return thumbnail.withDescription(this.description).withSpoiler(this.spoiler);
    }
}
