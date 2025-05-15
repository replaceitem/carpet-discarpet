package net.replaceitem.discarpet.script.parsable.parsables;

import carpet.script.Context;
import net.dv8tion.jda.api.utils.FileUpload;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@ParsableClass(name = "attachment")
public class AttachmentParsable implements ParsableConstructor<FileUpload> {
    
    FileParsable.AbstractFile file;
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
