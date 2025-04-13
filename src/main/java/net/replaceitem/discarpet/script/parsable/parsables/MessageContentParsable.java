package net.replaceitem.discarpet.script.parsable.parsables;

import carpet.script.exception.InternalExpressionException;
import carpet.script.value.MapValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.sticker.Sticker;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import net.dv8tion.jda.api.utils.messages.MessageRequest;
import net.replaceitem.discarpet.script.parsable.*;
import net.replaceitem.discarpet.script.parsable.parsables.embeds.EmbedParsable;
import net.replaceitem.discarpet.script.values.StickerValue;

import java.util.EnumSet;
import java.util.List;

@ParsableClass(name = "message_content")
public class MessageContentParsable implements Applicable<MessageRequest<?>>, DirectParsable {
    
    String content;
    @Optional List<FileUpload> attachments = List.of();
    @Optional List<Value> stickers = List.of();
    @Optional List<EmbedParsable> embeds = List.of();
    @Optional List<List<ItemComponent>> components = List.of();
    @Optional AllowedMentions allowed_mentions;
    @Optional Message reply_to;
    @Optional String nonce;
    @Optional Boolean tts = false;
    
    // message flags
    @Optional Boolean ephemeral = false;
    @Optional Boolean suppress_embeds = false;
    @Optional Boolean suppress_notifications = false;
    
    @Override
    public void apply(MessageRequest<?> message) {
        message.setContent(content);
        message.setFiles(attachments);
        if(!stickers.isEmpty()) {
            if(!(message instanceof MessageCreateAction messageCreateRequest)) throw new InternalExpressionException("'stickers' is only supported for message creation, not edits");
            messageCreateRequest.setStickers(stickers.stream().map(value ->
                    Sticker.fromId(value instanceof StickerValue stickerValue ?
                            stickerValue.getDelegate().getId() :
                            value.getString()
                    )).toList()
            );
        }
        message.setEmbeds()
        for (EmbedBuilder embed : embeds) {
            message.addEmbed(embed);
        }
        List<ActionRow> actionRows = components.stream().map(ActionRow::of).toList();
        for (ActionRow actionRow : actionRows) {
            message.addComponent(actionRow);
        }
        if(allowed_mentions != null) message.setAllowedMentions(allowed_mentions);
        if(reply_to != null) message.replyTo(reply_to);
        message.setNonce(nonce);
        message.setTts(tts);

        EnumSet<MessageFlag> messageFlags = EnumSet.noneOf(MessageFlag.class);
        if(ephemeral) messageFlags.add(MessageFlag.EPHEMERAL);
        if(suppress_embeds) messageFlags.add(MessageFlag.SUPPRESS_EMBEDS);
        if(suppress_notifications) messageFlags.add(MessageFlag.SUPPRESS_NOTIFICATIONS);
        if(!messageFlags.isEmpty()) message.setFlags(messageFlags);
    }

    @Override
    public MessageCreateData construct() {
        MessageCreateBuilder builder = new MessageCreateBuilder();
        builder.setContent(content);
        builder.addFiles();
        
        return builder.build();
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
