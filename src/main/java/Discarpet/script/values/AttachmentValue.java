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
            case "message" -> MessageValue.of(value.getMessage());
            case "file_name" -> StringValue.of(value.getFileName());
            case "size" -> NumericValue.of(value.getSize());
            case "url" -> StringValue.of(value.getUrl().toString());
            case "proxy_url" -> StringValue.of(value.getProxyUrl().toString());
            case "is_image" -> BooleanValue.of(value.isImage());
            case "width" -> ValueUtil.ofOptionalNumber(value.getWidth());
            case "height" -> ValueUtil.ofOptionalNumber(value.getHeight());
            case "is_spoiler" -> BooleanValue.of(value.isSpoiler());
            case "download" -> StringValue.of(new String(value.downloadAsByteArray().join()));
            default -> Value.NULL;
        };
    }
}
