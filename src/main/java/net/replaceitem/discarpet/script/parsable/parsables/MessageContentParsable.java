package net.replaceitem.discarpet.script.parsable.parsables;

import carpet.script.exception.InternalExpressionException;
import carpet.script.value.MapValue;
import carpet.script.value.Value;
import net.replaceitem.discarpet.script.parsable.Applicable;
import net.replaceitem.discarpet.script.parsable.DirectParsable;
import net.replaceitem.discarpet.script.parsable.Optional;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.util.content.ContentApplier;
import net.replaceitem.discarpet.script.values.StickerValue;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.message.mention.AllowedMentions;

import java.util.EnumSet;
import java.util.List;

@ParsableClass(name = "message_content")
public class MessageContentParsable implements Applicable<ContentApplier>, DirectParsable {
    
    String content;
    @Optional List<AttachmentParsable> attachments = List.of();
    @Optional List<Value> stickers = List.of();
    @Optional List<EmbedBuilder> embeds = List.of();
    @Optional List<List<LowLevelComponent>> components = List.of();
    @Optional AllowedMentions allowed_mentions;
    @Optional Message reply_to;
    @Optional String nonce;
    @Optional Boolean tts = false;
    
    // message flags
    @Optional Boolean ephemeral = false;
    @Optional Boolean suppress_embeds = false;
    @Optional Boolean suppress_notifications = false;
    
    @Override
    public void apply(ContentApplier contentApplier) {
        contentApplier.setContent(content);
        for (AttachmentParsable attachment : attachments) {
            attachment.apply(contentApplier);
        }
        for (Value sticker : stickers) {
            if(sticker instanceof StickerValue stickerValue) {
                contentApplier.addSticker(stickerValue.getDelegate());
            } else {
                String stickerId = sticker.getString();
                try {
                    long id = Long.parseLong(stickerId);
                    contentApplier.addSticker(id);
                } catch (NumberFormatException e) {
                    throw new InternalExpressionException("'stickers' must be a list of sticker values or sticker ids");
                }
            }
        }
        for (EmbedBuilder embed : embeds) {
            contentApplier.addEmbed(embed);
        }
        List<ActionRow> actionRows = components.stream().map(ActionRow::of).toList();
        for (ActionRow actionRow : actionRows) {
            contentApplier.addComponent(actionRow);
        }
        if(allowed_mentions != null) contentApplier.setAllowedMentions(allowed_mentions);
        if(reply_to != null) contentApplier.replyTo(reply_to);
        contentApplier.setNonce(nonce);
        contentApplier.setTts(tts);

        EnumSet<MessageFlag> messageFlags = EnumSet.noneOf(MessageFlag.class);
        if(ephemeral) messageFlags.add(MessageFlag.EPHEMERAL);
        if(suppress_embeds) messageFlags.add(MessageFlag.SUPPRESS_EMBEDS);
        if(suppress_notifications) messageFlags.add(MessageFlag.SUPPRESS_NOTIFICATIONS);
        if(!messageFlags.isEmpty()) contentApplier.setFlags(messageFlags);
    }

    @Override
    public boolean tryParseDirectly(Value value) {
        if(!(value instanceof MapValue)) {
            this.content = value.getString();
            return true;
        }
        return false;
    }
}
