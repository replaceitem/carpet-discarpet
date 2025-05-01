package net.replaceitem.discarpet.script.parsable.parsables;

import carpet.script.exception.InternalExpressionException;
import carpet.script.value.Value;
import net.dv8tion.jda.api.utils.FileUpload;
import net.replaceitem.discarpet.Discarpet;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;
import net.replaceitem.discarpet.script.util.FileUtil;
import net.replaceitem.discarpet.script.util.ScarpetGraphicsDependency;

import javax.annotation.Nullable;

@ParsableClass(name = "attachment")
public class AttachmentParsable implements ParsableConstructor<FileUpload> {
    
    @OptionalField
    @Nullable String file;
    @OptionalField
    @Nullable String url;
    @OptionalField
    @Nullable String bytes;
    @OptionalField
    @Nullable Value image;
    
    @OptionalField
    @Nullable String name;
    @OptionalField
    Boolean spoiler = false;
    
    private FileUpload createUpload() {
        if (file != null) {
            // TODO convert this to use scarpet's app file system
            return FileUtil.fromFile(file, name);
        } else if (url != null) {
            if(name == null) throw new InternalExpressionException("'bytes' attachment needs to have a specified 'name'");
            return FileUtil.fromUrl(url, name);
        } else if (bytes != null) {
            if(name == null) throw new InternalExpressionException("'bytes' attachment needs to have a specified 'name'");
            return FileUtil.fromString(bytes, name);
        } else if(image != null) {
            if(!Discarpet.isScarpetGraphicsInstalled()) throw new InternalExpressionException("scarpet-graphics is not installed, but required for an 'image' attachment");
            if(name == null) throw new InternalExpressionException("'image' attachment needs to have a specified 'name'");
            if(!ScarpetGraphicsDependency.isPixelAccessible(image)) throw new InternalExpressionException("'image' needs to be an image or graphics value");
            return FileUtil.fromImage(image, name);
        } else throw new InternalExpressionException("Expected either 'file', 'url', 'image' or 'bytes' value as an attachment");
    }

    @Override
    public FileUpload construct() {
        FileUpload fileUpload = createUpload();
        if(spoiler) fileUpload.asSpoiler();
        return fileUpload;
    }
}
