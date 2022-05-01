package Discarpet.script.parsable.parsables;

import Discarpet.Discarpet;
import Discarpet.script.parsable.Applicable;
import Discarpet.script.parsable.Optional;
import Discarpet.script.parsable.ParsableClass;
import Discarpet.script.util.ScarpetGraphicsDependency;
import Discarpet.script.util.content.ContentApplier;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.Value;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@ParsableClass(name = "attachment")
public class AttachmentParsable implements Applicable<ContentApplier> {
    
    @Optional String file;
    @Optional String url;
    @Optional String bytes;
    @Optional String name;
    @Optional Value image;
    @Optional Boolean spoiler = false;
    
    
    @Override
    public void apply(ContentApplier contentApplier) {
        try {
            if (file != null) {
                contentApplier.addAttachment(new File(file), spoiler);
            } else if (url != null) {
                contentApplier.addAttachment(new URL(url), spoiler);
            } else if (bytes != null) {
                if(name == null) throw new InternalExpressionException("'bytes' attachment needs to have a specified 'name'");
                contentApplier.addAttachment(bytes.getBytes(StandardCharsets.UTF_8), name, spoiler);
            } else if(image != null) {
                if(!Discarpet.isScarpetGraphicsInstalled()) throw new InternalExpressionException("scarpet-graphics is not installed, but required for an 'image' attachment");
                if(name == null) throw new InternalExpressionException("'image' attachment needs to have a specified 'name'");
                if(!ScarpetGraphicsDependency.isPixelAccessible(image)) throw new InternalExpressionException("'image' needs to be an image or graphics value");
                BufferedImage image = ScarpetGraphicsDependency.getImageFromValue(this.image);
                contentApplier.addAttachment(image,name,spoiler);
            } else throw new InternalExpressionException("Expected either 'file', 'url', 'image' or 'bytes' value as an attachment");
        } catch (Exception e) {
            throw new InternalExpressionException(e.getMessage());
        }
    }
}
