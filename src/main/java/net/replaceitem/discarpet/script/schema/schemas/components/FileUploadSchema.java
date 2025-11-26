package net.replaceitem.discarpet.script.schema.schemas.components;

import carpet.script.Context;
import net.dv8tion.jda.api.components.attachmentupload.AttachmentUpload;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;
import org.jetbrains.annotations.Nullable;

@SchemaClass(name = "file_upload")
public class FileUploadSchema implements SchemaConstructor<AttachmentUpload> {
    String id;
    @OptionalField @Nullable
    Integer min_values;
    @OptionalField @Nullable
    Integer max_values;
    @OptionalField
    Boolean required = true;

    @Override
    public AttachmentUpload construct(Context context) {
        AttachmentUpload.Builder builder = AttachmentUpload.create(id);
        if(min_values != null) builder.setMinValues(min_values);
        if(max_values != null) builder.setMaxValues(max_values);
        return builder.setRequired(required).build();
    }
}
