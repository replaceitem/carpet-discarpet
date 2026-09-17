package net.replaceitem.discarpet.script.schema.schemas;

import carpet.script.Context;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.interactions.FileType;
import net.replaceitem.discarpet.script.schema.DirectParsable;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;
import org.jetbrains.annotations.Nullable;

@SchemaClass(name = "file_type")
public class FileTypeSchema implements SchemaConstructor<FileType>, DirectParsable {
    @OptionalField @Nullable
    String group;
    @OptionalField @Nullable
    String extension;

    @Override
    public FileType construct(Context context) {
        if(extension != null) return FileType.ofExtension(extension);
        if(group != null) return switch (group) {
            case "image" -> FileType.IMAGE;
            case "audio" -> FileType.AUDIO;
            case "video" -> FileType.VIDEO;
            default -> throw new InternalExpressionException("Unknown value for 'group': " + group);
        };
        throw new InternalExpressionException("Expected either 'group' or 'extension'");
    }

    @Override
    public boolean tryParseDirectly(Value value, Context context) {
        if(!(value instanceof StringValue stringValue)) return false;
        var str = stringValue.getString();
        if(str.startsWith(".")) {
            this.extension = str.substring(1);
        } else {
            this.group = str;
        }
        return true;
    }
}
