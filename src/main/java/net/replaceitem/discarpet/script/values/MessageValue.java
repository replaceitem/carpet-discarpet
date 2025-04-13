package net.replaceitem.discarpet.script.values;

import carpet.script.value.NumericValue;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.entities.Message;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.common.Deletable;
import net.replaceitem.discarpet.script.values.common.DiscordValue;
import carpet.script.value.ListValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Optional;

public class MessageValue extends DiscordValue<Message> implements Deletable {
    public MessageValue(Message message) {
        super(message);
    }

    @Override
    protected String getDiscordTypeString() {
        return "message";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "content" -> StringValue.of(delegate.getContentRaw());
            case "readable_content" -> StringValue.of(delegate.getContentDisplay()); //not all user mentions will be parsed, if they are not cached
            case "stripped_content" -> StringValue.of(delegate.getContentStripped());
            case "id" -> StringValue.of(delegate.getId());
            case "channel" -> ChannelValue.of(delegate.getChannel());
            case "user" -> UserValue.of(delegate.getAuthor());
            // TODO add ~'member'
            case "webhook_id" -> StringValue.of(delegate.isWebhookMessage() ? delegate.getAuthor().getId() : null);
            case "server" -> ServerValue.of(delegate.getGuild());
            case "nonce" -> StringValue.of(delegate.getNonce());
            case "attachments" -> ListValue.wrap(delegate.getAttachments().stream().map(AttachmentValue::new));
            case "sticker_ids" -> ListValue.wrap(delegate.getStickers().stream().map(ISnowflake::getId).map(StringValue::of));
            case "stickers" -> ListValue.wrap(delegate.getStickers().stream().map(StickerValue::new));
            case "referenced_message" -> MessageValue.of(delegate.getReferencedMessage());
            // TODO migrate?
            case "type" -> StringValue.of(delegate.getType().name());
            case "link" -> StringValue.of(delegate.getJumpUrl());
            // TODO migrate?
            case "flags" -> ListValue.wrap(delegate.getFlags().stream().map(Enum::name).map(StringValue::of));
            case "creation_timestamp" -> NumericValue.of(delegate.getTimeCreated().toInstant().toEpochMilli());
            case "edit_timestamp" -> NumericValue.of(Optional.ofNullable(delegate.getTimeEdited()).map(OffsetDateTime::toInstant).map(Instant::toEpochMilli).orElse(null));
            case "position" -> ValueUtil.ofPositiveInt(delegate.getApproximatePosition());
            default -> super.getProperty(property);
        };
    }

    @Override
    public void delete(String reason) {
        ValueUtil.awaitRest(delegate.delete().reason(reason), "Failed to delete " + this.getTypeString());
    }
}
