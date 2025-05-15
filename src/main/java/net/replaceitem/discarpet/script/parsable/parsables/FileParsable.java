package net.replaceitem.discarpet.script.parsable.parsables;

import carpet.script.Context;
import carpet.script.argument.FileArgument;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.utils.FileUpload;
import net.replaceitem.discarpet.Discarpet;
import net.replaceitem.discarpet.script.parsable.DirectParsable;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;
import net.replaceitem.discarpet.script.util.FileUtil;
import net.replaceitem.discarpet.script.util.ScarpetGraphicsDependency;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static carpet.script.argument.FileArgument.recognizeResource;

@ParsableClass(name = "file")
public class FileParsable implements ParsableConstructor<FileParsable.AbstractFile>, DirectParsable {
    @OptionalField @Nullable
    String file;
    @OptionalField
    Boolean file_shared = false;
    @OptionalField @Nullable
    String url;
    @OptionalField @Nullable
    String bytes;
    @OptionalField @Nullable
    Value image;

    
    private void validateFields() {
        List<String> fields = new ArrayList<>(4);
        if(file != null) fields.add("file");
        if(url != null) fields.add("url");
        if(bytes != null) fields.add("bytes");
        if(image != null) fields.add("image");
        if(fields.isEmpty()) throw new InternalExpressionException("Expected either 'file', 'url', 'image' or 'bytes' value as an attachment");
        if(fields.size() > 1) throw new InternalExpressionException("Expected only one of 'file', 'url', 'image' or 'bytes' value as an attachment, but got " + String.join(" and ", fields));
        if(file == null && file_shared) throw new InternalExpressionException("'file_shared' is only used when providing a 'file'");
    }

    @Override
    public AbstractFile construct(Context context) {
        this.validateFields();
        if (file != null) {
            Pair<String, String> resource = recognizeResource(file, false, FileArgument.Type.ANY);
            FileArgument fileArgument = new FileArgument(resource.getLeft(), FileArgument.Type.ANY, resource.getRight(), false, file_shared, FileArgument.Reason.READ, context.host);
            return AbstractFile.ofFileArgument(context, fileArgument);
        }
        if (url != null) {
           return AbstractFile.ofUrl(url);
        }
        if (bytes != null) {
            byte[] byteArr = bytes.getBytes(StandardCharsets.UTF_8);
            return AbstractFile.ofBytes(byteArr);
        }
        if(image != null) {
            if(!Discarpet.isScarpetGraphicsInstalled()) throw new InternalExpressionException("scarpet-graphics is not installed, but required for an image file upload");
            if(!ScarpetGraphicsDependency.isPixelAccessible(image)) throw new InternalExpressionException("Image needs to be an image or graphics value");
            BufferedImage bufferedImage = ScarpetGraphicsDependency.getImageFromValue(image);
            if(bufferedImage == null) throw new InternalExpressionException("Value is not an image value");
            return AbstractFile.ofBufferedImage(bufferedImage);
        }
        // should not get here, validated by validateFields()
        throw new IllegalStateException();
    }

    @Override
    public boolean tryParseDirectly(Value value) {
        if(value instanceof StringValue) {
            String stringValue = value.getString();
            try {
                //noinspection ResultOfMethodCallIgnored
                new URI(stringValue).toURL();
                this.url = stringValue;
            } catch (MalformedURLException | URISyntaxException e) {
                this.file = stringValue;
            }
            return true;
        }
        if(Discarpet.isScarpetGraphicsInstalled() && ScarpetGraphicsDependency.isPixelAccessible(value)) {
            this.image = value;
            return true;
        }
        return false;
    }


    /**
     * A URL to a file that might come with a file uploaded that needs to be attached to the message.
     * This is relevant for using an attachment in an embed but a web-url could also be used.
     */
    public record AttachableUrl(String url, @Nullable FileUpload attachment) {
        public static AttachableUrl attached(FileUpload upload) {
            return new AttachableUrl("attachment://" + upload.getName(), upload);
        }
        public static AttachableUrl ofUrl(String url) {
            return new AttachableUrl(url, null);
        }
        public Optional<FileUpload> optAttachment() {
            return Optional.ofNullable(attachment);
        }
    }

    public interface AbstractFile {
        FileUpload asFileUpload();
        AttachableUrl asUrl();
        Icon asIcon();

        static AbstractFile ofFileArgument(Context context, FileArgument fileArgument) {
            return new AbstractFile() {
                @Override
                public FileUpload asFileUpload() {
                    return FileUtil.fromFileArgument(context, fileArgument);
                }

                @Override
                public AttachableUrl asUrl() {
                    return AttachableUrl.attached(asFileUpload());
                }

                @Override
                public Icon asIcon() {
                    try {
                        return Icon.from(FileUtil.inputStreamFromFileArgument(context, fileArgument));
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }
            };
        }

        static AbstractFile ofUrl(String url) {
            return new AbstractFile() {
                @Override
                public FileUpload asFileUpload() {
                    return FileUtil.fromUrl(url);
                }

                @Override
                public AttachableUrl asUrl() {
                    return AttachableUrl.ofUrl(url);
                }

                @Override
                public Icon asIcon() {
                    try {
                        return Icon.from(FileUtil.inputStreamFromUrl(url));
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }
            };
        }

        static AbstractFile ofBytes(byte[] byteArr) {
            return new AbstractFile() {
                @Override
                public FileUpload asFileUpload() {
                    return FileUpload.fromData(byteArr, FileUtil.randomName());
                }

                @Override
                public AttachableUrl asUrl() {
                    return AttachableUrl.attached(asFileUpload());
                }

                @Override
                public Icon asIcon() {
                    return Icon.from(byteArr);
                }
            };
        }

        static AbstractFile ofBufferedImage(BufferedImage bufferedImage) {
            Supplier<InputStream> inputStreamSupplier = FileUtil.imageToInputStreamSupplier(bufferedImage, "png");
            return new AbstractFile() {
                @Override
                public FileUpload asFileUpload() {
                    return FileUpload.fromStreamSupplier(FileUtil.randomName(), inputStreamSupplier);
                }

                @Override
                public AttachableUrl asUrl() {
                    return AttachableUrl.attached(asFileUpload());
                }

                @Override
                public Icon asIcon() {
                    try {
                        return Icon.from(inputStreamSupplier.get());
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }
            };
        }
    }
}
