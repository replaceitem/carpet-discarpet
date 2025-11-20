package net.replaceitem.discarpet.script.schema.schemas.components;

import carpet.script.Context;
import net.dv8tion.jda.api.components.mediagallery.MediaGallery;
import net.dv8tion.jda.api.components.mediagallery.MediaGalleryItem;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;
import net.replaceitem.discarpet.script.schema.schemas.FileSchema;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SchemaClass(name = "media_gallery")
public class MediaGallerySchema implements SchemaConstructor<MediaGallery> {

    List<MediaGalleryItem> items;

    @Override
    public MediaGallery construct(Context context) {
        return MediaGallery.of(items);
    }

    @SchemaClass(name = "media_gallery_item")
    public static class MediaGalleryItemSchema implements SchemaConstructor<MediaGalleryItem> {

        FileSchema.AbstractFile media;
        @OptionalField
        @Nullable
        String description;
        @OptionalField
        Boolean spoiler = false;

        @Override
        public MediaGalleryItem construct(Context context) {
            // if the file doesn't need to be uploaded, we will only send the url to discord and avoid
            // unnecessarily downloading it through discarpet only to attach it to the message.
            MediaGalleryItem item = media.requiresUpload()
                    ? MediaGalleryItem.fromFile(media.asFileUpload())
                    : MediaGalleryItem.fromUrl(media.asUrl().url());
            return item.withDescription(this.description).withSpoiler(this.spoiler);
        }
    }
}
