package Discarpet.script.parsable.parsables;

import Discarpet.script.parsable.Applicable;
import Discarpet.script.parsable.Optional;
import Discarpet.script.parsable.ParsableClass;
import Discarpet.script.util.content.ContentApplier;
import carpet.script.exception.InternalExpressionException;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@ParsableClass(name = "attachment")
public class AttachmentParsable implements Applicable<ContentApplier> {
    
    @Optional String file;
    @Optional String url;
    @Optional String bytes;
    @Optional String name;
    @Optional Boolean spoiler;
    
    
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
            } else throw new InternalExpressionException("Expected either 'file', 'url' or 'bytes' value as an attachment");
        } catch (Exception e) {
            throw new InternalExpressionException(e.getMessage());
        }
    }
}
