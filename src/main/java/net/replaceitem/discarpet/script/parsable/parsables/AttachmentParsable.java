package net.replaceitem.discarpet.script.parsable.parsables;

import carpet.script.exception.InternalExpressionException;
import carpet.script.exception.ThrowStatement;
import carpet.script.exception.Throwables;
import carpet.script.value.Value;
import net.dv8tion.jda.api.utils.FileUpload;
import net.replaceitem.discarpet.Discarpet;
import net.replaceitem.discarpet.script.parsable.Optional;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;
import net.replaceitem.discarpet.script.util.ScarpetGraphicsDependency;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

@ParsableClass(name = "attachment")
public class AttachmentParsable implements ParsableConstructor<FileUpload> {
    
    @Optional String file;
    @Optional String url;
    @Optional String bytes;
    @Optional Value image;
    
    @Optional String name;
    @Optional Boolean spoiler = false;
    
    public FileUpload create() {
        try {
            if (file != null) {
                return FileUpload.fromData(new File(file));
            } else if (url != null) {
                if(name == null) throw new InternalExpressionException("'bytes' attachment needs to have a specified 'name'");
                HttpURLConnection conn = (HttpURLConnection) new URI(url).toURL().openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                return FileUpload.fromData(conn.getInputStream(), url);
            } else if (bytes != null) {
                if(name == null) throw new InternalExpressionException("'bytes' attachment needs to have a specified 'name'");
                return FileUpload.fromData(bytes.getBytes(StandardCharsets.UTF_8), name);
            } else if(image != null) {
                if(!Discarpet.isScarpetGraphicsInstalled()) throw new InternalExpressionException("scarpet-graphics is not installed, but required for an 'image' attachment");
                if(name == null) throw new InternalExpressionException("'image' attachment needs to have a specified 'name'");
                if(!ScarpetGraphicsDependency.isPixelAccessible(image)) throw new InternalExpressionException("'image' needs to be an image or graphics value");
                BufferedImage image = ScarpetGraphicsDependency.getImageFromValue(this.image);
                PipedOutputStream pos = new PipedOutputStream();
                PipedInputStream pis = new PipedInputStream(pos);
                
                String fileType = getFileType();
                Thread.startVirtualThread(() -> {
                    try {
                        ImageIO.write(image, fileType, pos);
                        pos.close();
                    } catch (Throwable t) {
                        Discarpet.LOGGER.error("Failed to process buffered image file!", t);
                    }
                });
                return FileUpload.fromData(pis, name);
            } else throw new InternalExpressionException("Expected either 'file', 'url', 'image' or 'bytes' value as an attachment");
        } catch (IOException | URISyntaxException e) {
            throw new ThrowStatement(e.getMessage(), Throwables.IO_EXCEPTION);
        }
    }

    public String getFileType() {
        return name != null && name.contains(".") ? name.substring(name.lastIndexOf(".") + 1) : "png";
    }

    @Override
    public FileUpload construct() {
        FileUpload fileUpload = create();
        if(name != null) fileUpload.setName(name);
        if(spoiler) fileUpload.asSpoiler();
        return fileUpload;
    }
}
