package net.replaceitem.discarpet.script.schema.schemas;

import carpet.script.Context;
import net.dv8tion.jda.api.utils.FileUpload;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SchemaClass(name = "attachment")
public class AttachmentSchema implements SchemaConstructor<FileUpload> {
    
    FileSchema.AbstractFile file;
    @OptionalField @Nullable
    String name;
    @OptionalField @Nullable
    String description;
    @OptionalField
    Boolean spoiler = false;

    @Override
    public FileUpload construct(Context context) {
        FileUpload fileUpload = file.asFileUpload();
        if(name != null) fileUpload.setName(name);
        if(description != null) fileUpload.setDescription(description);
        if(spoiler) fileUpload.asSpoiler();
        return fileUpload;
    }
    
    public static abstract class WithAttachments<T> {
        private final T data;
        private final List<FileUpload> fileUploads;

        public WithAttachments(T data, List<FileUpload> fileUploads) {
            this.data = data;
            this.fileUploads = fileUploads;
        }

        public T getData() {
            return data;
        }

        public List<FileUpload> getFileUploads() {
            return fileUploads;
        }
    }
}
