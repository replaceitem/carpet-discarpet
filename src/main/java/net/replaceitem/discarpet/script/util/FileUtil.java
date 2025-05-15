package net.replaceitem.discarpet.script.util;

import carpet.script.CarpetScriptHost;
import carpet.script.Context;
import carpet.script.argument.FileArgument;
import carpet.script.exception.InternalExpressionException;
import net.dv8tion.jda.api.utils.FileUpload;
import net.replaceitem.discarpet.Discarpet;
import net.replaceitem.discarpet.script.exception.DiscordThrowables;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.UUID;
import java.util.function.Supplier;

public class FileUtil {
    public static String randomName() {
        return UUID.randomUUID().toString();
    }
    
    public static InputStream inputStreamFromUrl(String url) {
        try {
            URL uri = new URI(url).toURL();
            return inputStreamFromUrl(uri);
        } catch (MalformedURLException e) {
            throw new UncheckedIOException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static InputStream inputStreamFromUrl(URL url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            return conn.getInputStream();
        } catch (IOException e) {
            throw new UncheckedIOException("Could not download url " + url, e);
        }
    }
    
    public static FileUpload fromUrl(String urlString) {
        try {
            URI uri = new URI(urlString);
            String[] path = uri.getPath().split("/");
            String name = path.length == 0 ? "unnamed" : path[path.length - 1];
            URL url = uri.toURL();
            return FileUpload.fromStreamSupplier(name, () -> inputStreamFromUrl(url));
        } catch (UncheckedIOException | URISyntaxException | MalformedURLException e) {
            throw DiscordThrowables.convert(e);
        }
    }

    public static FileUpload fromFileArgument(Context context, FileArgument fileArgument) {
        String name = fileArgument.resource.substring(fileArgument.resource.lastIndexOf("/") + 1);
        return FileUpload.fromStreamSupplier(name, () -> inputStreamFromFileArgument(context, fileArgument));
    }
    
    public static InputStream inputStreamFromFileArgument(Context context, FileArgument fileArgument) {
        return FileUtil.readResourceAsStream(((CarpetScriptHost) context.host), fileArgument);
    }

    public static InputStream readResourceAsStream(CarpetScriptHost scriptHost, FileArgument fileArgument) {
        if(scriptHost.isDefaultApp() && !fileArgument.isShared) throw new InternalExpressionException("Cannot read shared resource in " + scriptHost.getVisualName());
        return ((FileArgumentExtension) fileArgument).asInputStream(scriptHost.main);
    }
    
    public static Supplier<InputStream> imageToInputStreamSupplier(BufferedImage image, String fileType) {
        return () -> {
            try {
                PipedOutputStream pos = new PipedOutputStream();
                PipedInputStream pis = new PipedInputStream(pos);

                Thread.startVirtualThread(() -> {
                    try {
                        ImageIO.write(image, fileType, pos);
                    } catch (Throwable t) {
                        Discarpet.LOGGER.error("Failed to process buffered image file!", t);
                    } finally {
                        try {
                            pos.close();
                        } catch (IOException e) {
                            Discarpet.LOGGER.error(e);
                        }
                    }
                });

                return pis;
            } catch (IOException ioException) {
                Discarpet.LOGGER.error("Error creating input stream for image value", ioException);
            }
            return null;
        };
    }
}
