package net.replaceitem.discarpet.script.values;

import carpet.script.value.BooleanValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.entities.Message;
import net.replaceitem.discarpet.script.exception.DiscordThrowables;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.common.DiscordValue;

import java.io.IOException;
import java.io.InputStream;

public class AttachmentValue extends DiscordValue<Message.Attachment> {
    public AttachmentValue(Message.Attachment attachment) {
        super(attachment);
    }

    @Override
    protected String getDiscordTypeString() {
        return "attachment_value";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "file_name" -> StringValue.of(delegate.getFileName());
            case "size" -> NumericValue.of(delegate.getSize());
            case "url" -> StringValue.of(delegate.getUrl());
            case "proxy_url" -> StringValue.of(delegate.getProxyUrl());
            case "is_image" -> BooleanValue.of(delegate.isImage());
            case "width" -> ValueUtil.ofPositiveInt(delegate.getWidth());
            case "height" -> ValueUtil.ofPositiveInt(delegate.getHeight());
            case "is_spoiler" -> BooleanValue.of(delegate.isSpoiler());
            case "download" -> StringValue.of(download());
            default -> super.getProperty(property);
        };
    }
    
    private String download() {
        try(InputStream is = ValueUtil.awaitFuture(delegate.getProxy().download(), "Error downloading attachment")) {
            return new String(is.readAllBytes());
        } catch (IOException e) {
            throw DiscordThrowables.convert(e, "Could not download attachment data");
        }
    }
}
