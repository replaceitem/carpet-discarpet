package Discarpet.script.values;

import Discarpet.script.util.ValueUtil;
import Discarpet.script.values.common.DiscordValue;
import carpet.script.value.BooleanValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.entity.message.MessageAttachment;

public class AttachmentValue extends DiscordValue<MessageAttachment> {
    public AttachmentValue(MessageAttachment attachment) {
        super("attachment_value",attachment);
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "message" -> MessageValue.of(delegate.getMessage());
            case "file_name" -> StringValue.of(delegate.getFileName());
            case "size" -> NumericValue.of(delegate.getSize());
            case "url" -> StringValue.of(delegate.getUrl().toString());
            case "proxy_url" -> StringValue.of(delegate.getProxyUrl().toString());
            case "is_image" -> BooleanValue.of(delegate.isImage());
            case "width" -> ValueUtil.ofOptionalNumber(delegate.getWidth());
            case "height" -> ValueUtil.ofOptionalNumber(delegate.getHeight());
            case "is_spoiler" -> BooleanValue.of(delegate.isSpoiler());
            case "download" -> StringValue.of(new String(delegate.downloadAsByteArray().join()));
            default -> Value.NULL;
        };
    }
}
