package Discarpet.script.values;

import Discarpet.script.util.ValueUtil;
import carpet.script.value.BooleanValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import net.minecraft.nbt.NbtElement;
import org.javacord.api.entity.message.MessageAttachment;

public class AttachmentValue extends Value {

    public MessageAttachment attachment;

    public AttachmentValue(MessageAttachment attachment) {
        this.attachment = attachment;
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "message" -> MessageValue.of(attachment.getMessage());
            case "file_name" -> StringValue.of(attachment.getFileName());
            case "size" -> NumericValue.of(attachment.getSize());
            case "url" -> StringValue.of(attachment.getUrl().toString());
            case "proxy_url" -> StringValue.of(attachment.getProxyUrl().toString());
            case "is_image" -> BooleanValue.of(attachment.isImage());
            case "width" -> ValueUtil.ofOptionalNumber(attachment.getWidth());
            case "height" -> ValueUtil.ofOptionalNumber(attachment.getHeight());
            case "is_spoiler" -> BooleanValue.of(attachment.isSpoiler());
            case "download" -> StringValue.of(new String(attachment.downloadAsByteArray().join()));
            default -> Value.NULL;
        };
    }

    public MessageAttachment getAttachment() {
        return attachment;
    }

    @Override
    public Value in(Value value1) {
        return getProperty(value1.getString());
    }

    @Override
    public String getTypeString() {
        return "dc_attachment";
    }

    @Override
    public String getString() {
        return attachment.toString();
    }

    @Override
    public boolean getBoolean() {
        return true;
    }

    @Override
    public NbtElement toTag(boolean b) {
        return null;
    }
}
