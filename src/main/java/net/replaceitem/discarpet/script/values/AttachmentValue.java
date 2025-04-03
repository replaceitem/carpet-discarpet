package net.replaceitem.discarpet.script.values;

import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.common.DiscordValue;
import carpet.script.value.BooleanValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.entity.Attachment;
import org.javacord.api.entity.message.MessageAttachment;

public class AttachmentValue extends DiscordValue<Attachment> {
    public AttachmentValue(Attachment attachment) {
        super(attachment);
    }

    @Override
    protected String getDiscordTypeString() {
        return "attachment_value";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "message" -> delegate instanceof MessageAttachment messageAttachment ? of(messageAttachment.getMessage()) : Value.NULL;
            case "file_name" -> StringValue.of(delegate.getFileName());
            case "size" -> NumericValue.of(delegate.getSize());
            case "url" -> StringValue.of(delegate.getUrl().toString());
            case "proxy_url" -> StringValue.of(delegate.getProxyUrl().toString());
            case "is_image" -> BooleanValue.of(delegate.isImage());
            case "width" -> ValueUtil.ofOptionalNumber(delegate.getWidth());
            case "height" -> ValueUtil.ofOptionalNumber(delegate.getHeight());
            case "is_spoiler" -> BooleanValue.of(delegate.isSpoiler());
            case "download" -> StringValue.of(new String(ValueUtil.awaitFuture(delegate.asByteArray(), "Error downloading attachment")));
            default -> super.getProperty(property);
        };
    }
}
