package net.replaceitem.discarpet.script.schema.schemas;

import carpet.script.Context;
import carpet.script.argument.FileArgument;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.utils.FileUpload;
import net.replaceitem.discarpet.Discarpet;
import net.replaceitem.discarpet.script.schema.DirectParsable;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;
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
import java.util.*;
import java.util.function.Supplier;

import static carpet.script.argument.FileArgument.recognizeResource;

@SchemaClass(name = "file")
public class FileSchema implements SchemaConstructor<FileSchema.AbstractFile>, DirectParsable {
    @OptionalField @Nullable
    String file;
    @OptionalField
    Boolean file_shared = false;
    @OptionalField @Nullable
    String url;
    @OptionalField @Nullable
    String string;
    @OptionalField @Nullable
    String base64;
    @OptionalField @Nullable
    Value image;
    @OptionalField @Nullable
    String image_type;

    
    private void validateFields() {
        List<String> fields = new ArrayList<>(5);
        if(file != null) fields.add("file");
        if(url != null) fields.add("url");
        if(string != null) fields.add("string");
        if(base64 != null) fields.add("base64");
        if(image != null) fields.add("image");
        if(fields.isEmpty()) throw new InternalExpressionException("Expected either 'file', 'url', 'image', 'base64' or 'string' value as an attachment");
        if(fields.size() > 1) throw new InternalExpressionException("Expected only one of 'file', 'url', 'image', 'base64' or 'string' value as an attachment, but got " + String.join(" and ", fields));
        if(file == null && file_shared) throw new InternalExpressionException("'file_shared' is only used when providing a 'file'");
    }

    @Override
    public AbstractFile construct(Context context) {
        this.validateFields();
        if (file != null) {
            Pair<String, String> resource = recognizeResource(file, false, FileArgument.Type.ANY);
            FileArgument fileArgument = new FileArgument(resource.getLeft(), FileArgument.Type.ANY, resource.getRight(), false, file_shared, FileArgument.Reason.READ, context.host);
            Icon.IconType iconType = image_type == null ? null : Icon.IconType.fromExtension(image_type);
            return AbstractFile.ofFileArgument(context, fileArgument, iconType);
        }
        if (url != null) {
            Icon.IconType iconType = Icon.IconType.fromExtension(image_type != null ? image_type : Objects.requireNonNullElse(FileUtil.getExtension(url), "png"));
            return AbstractFile.ofUrl(url, iconType);
        }
        if (string != null) {
            byte[] byteArr = string.getBytes(StandardCharsets.UTF_8);
            return AbstractFile.ofBytes(byteArr, Icon.IconType.fromExtension(Objects.requireNonNullElse(image_type, "png")));
        }
        if (base64 != null) {
            byte[] byteArr = Base64.getDecoder().decode(base64);
            return AbstractFile.ofBytes(byteArr, Icon.IconType.fromExtension(Objects.requireNonNullElse(image_type, "png")));
        }
        if(image != null) {
            if(!Discarpet.isScarpetGraphicsInstalled()) throw new InternalExpressionException("scarpet-graphics is not installed, but required for an image file upload");
            if(!ScarpetGraphicsDependency.isPixelAccessible(image)) throw new InternalExpressionException("Image needs to be an image or graphics value");
            BufferedImage bufferedImage = ScarpetGraphicsDependency.getImageFromValue(image);
            if(bufferedImage == null) throw new InternalExpressionException("Value is not an image value");
            Icon.IconType iconType = image_type == null ? Icon.IconType.PNG : Icon.IconType.fromExtension(image_type);
            return AbstractFile.ofBufferedImage(bufferedImage, iconType);
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

        static AbstractFile ofFileArgument(Context context, FileArgument fileArgument, @Nullable Icon.IconType iconType) {
            if(iconType == null) {
                String extension = FileUtil.getExtension(fileArgument.resource);
                iconType = extension == null ? Icon.IconType.PNG : Icon.IconType.fromExtension(extension);
            }
            Icon.IconType finalIconType = iconType;
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
                        return Icon.from(FileUtil.inputStreamFromFileArgument(context, fileArgument), finalIconType);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }
            };
        }

        static AbstractFile ofUrl(String url, @Nullable Icon.IconType iconType) {
            if(iconType == null) {
                String extension = FileUtil.getExtension(url);
                iconType = extension == null ? Icon.IconType.PNG : Icon.IconType.fromExtension(extension);
            }
            Icon.IconType finalIconType = iconType;
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
                        return Icon.from(FileUtil.inputStreamFromUrl(url), finalIconType);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }
            };
        }

        static AbstractFile ofBytes(byte[] byteArr, @Nullable Icon.IconType iconType) {
            return new AbstractFile() {
                @Override
                public FileUpload asFileUpload() {
                    return FileUpload.fromData(byteArr, FileUtil.randomName(iconType == null ? null : iconType.name().toLowerCase()));
                }

                @Override
                public AttachableUrl asUrl() {
                    return AttachableUrl.attached(asFileUpload());
                }

                @Override
                public Icon asIcon() {
                    return Icon.from(byteArr, iconType == null ? Icon.IconType.PNG : iconType);
                }
            };
        }

        static AbstractFile ofBufferedImage(BufferedImage bufferedImage, Icon.IconType iconType) {
            String fileExtension = iconType.name().toLowerCase();
            Supplier<InputStream> inputStreamSupplier = FileUtil.imageToInputStreamSupplier(bufferedImage, fileExtension);
            return new AbstractFile() {
                @Override
                public FileUpload asFileUpload() {
                    return FileUpload.fromStreamSupplier(FileUtil.randomName(fileExtension), inputStreamSupplier);
                }

                @Override
                public AttachableUrl asUrl() {
                    return AttachableUrl.attached(asFileUpload());
                }

                @Override
                public Icon asIcon() {
                    try {
                        return Icon.from(inputStreamSupplier.get(), iconType);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }
            };
        }
    }
}
