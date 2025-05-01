package net.replaceitem.discarpet.script.util;

import carpet.script.exception.InternalExpressionException;
import carpet.script.value.Value;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.utils.FileUpload;
import net.replaceitem.discarpet.Discarpet;
import net.replaceitem.discarpet.script.exception.DiscordThrowables;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@ParametersAreNonnullByDefault
public class FileUtil {
    
    public static Icon iconFromValue(Value value) {
        try {
            if (Discarpet.isScarpetGraphicsInstalled() && ScarpetGraphicsDependency.isPixelAccessible(value)) {
                return Icon.from(imageValueToInputStream(value, "png"), Icon.IconType.PNG);
            }
            String valueString = value.getString();
            if (EmbedBuilder.URL_PATTERN.matcher(valueString).matches()) {
                return Icon.from(inputStreamFromUrl(valueString), Icon.IconType.PNG);
            }
            File file = new File(valueString);
            if (file.exists()) {
                return Icon.from(file);
            }
            throw new InternalExpressionException("Expected either a url, file path or image value");
        } catch (UncheckedIOException | IOException e) {
            throw DiscordThrowables.convert(e);
        }
    }
    
    public static String randomName(String fileType) {
        return UUID.randomUUID() + "." + fileType;
    }

    public static FileUpload fromFile(String file, @Nullable String name) {
        return fromFile(new File(file), name);
    }
    
    public static FileUpload fromFile(File file, @Nullable String name) {
        try {
            return name == null ? FileUpload.fromData(file) : FileUpload.fromData(file, name);
        } catch (UncheckedIOException e) {
            throw DiscordThrowables.convert(e);
        }
    }
    
    public static InputStream inputStreamFromUrl(String url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URI(url).toURL().openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            return conn.getInputStream();
        } catch (UncheckedIOException | URISyntaxException | IOException e) {
            throw DiscordThrowables.convert(e);
        }
    }
    
    public static FileUpload fromUrl(String url, @Nullable String name) {
        try {
            if(name == null) {
                String[] path = new URI(url).getPath().split("/");
                name = path.length == 0 ? "unnamed" : path[path.length - 1];
            }
            InputStream inputStream = inputStreamFromUrl(url);
            return FileUpload.fromData(inputStream, name);
        } catch (UncheckedIOException | URISyntaxException e) {
            throw DiscordThrowables.convert(e);
        }
    }
    
    public static FileUpload fromString(String bytes, String name) {
        return FileUpload.fromData(bytes.getBytes(StandardCharsets.UTF_8), name);
    }
    
    public static InputStream imageValueToInputStream(Value value, String fileType) {
        try {
            if(!Discarpet.isScarpetGraphicsInstalled()) throw new InternalExpressionException("scarpet-graphics is not installed, but required for an image file upload");
            if(!ScarpetGraphicsDependency.isPixelAccessible(value)) throw new InternalExpressionException("Image needs to be an image or graphics value");
            BufferedImage image = ScarpetGraphicsDependency.getImageFromValue(value);
            PipedOutputStream pos = new PipedOutputStream();
            PipedInputStream pis = new PipedInputStream(pos);

            Thread.startVirtualThread(() -> {
                try {
                    ImageIO.write(image, fileType, pos);
                    pos.close();
                } catch (Throwable t) {
                    Discarpet.LOGGER.error("Failed to process buffered image file!", t);
                }
            });
            return pis;
        } catch (UncheckedIOException | IOException e) {
            throw DiscordThrowables.convert(e);
        }
    }
    
    public static FileUpload fromImage(Value value, String name) {
        return FileUpload.fromData(imageValueToInputStream(value, getFileExtension(name, "png")), name);
    }
    
    public static String getFileExtension(String name, @Nullable String fallback) {
        return name.contains(".") ? name.substring(name.lastIndexOf(".") + 1) : fallback;
    }
}
