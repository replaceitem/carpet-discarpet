package net.replaceitem.discarpet.script.schema.schemas.components;

import carpet.script.Context;
import net.dv8tion.jda.api.components.filedisplay.FileDisplay;
import net.dv8tion.jda.api.utils.FileUpload;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;
import net.replaceitem.discarpet.script.schema.schemas.FileSchema;
import org.jetbrains.annotations.Nullable;

@SchemaClass(name = "file_display")
public class FileDisplaySchema implements SchemaConstructor<FileDisplay> {

    FileSchema.AbstractFile file;
    @OptionalField @Nullable
    String name;
    @OptionalField
    Boolean spoiler = false;

    @Override
    public FileDisplay construct(Context context) {
        FileUpload upload = file.asFileUpload();
        if (this.name != null) {
            upload.setName(this.name);
        }
        return FileDisplay.fromFile(upload).withSpoiler(this.spoiler);
    }
}
