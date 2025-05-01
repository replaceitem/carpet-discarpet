package net.replaceitem.discarpet.script.parsable.parsables;

import carpet.script.exception.InternalExpressionException;
import carpet.script.value.MapValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.sticker.Sticker;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.requests.restaction.WebhookMessageCreateAction;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.messages.MessageRequest;
import net.replaceitem.discarpet.script.parsable.DirectParsable;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.parsables.embeds.EmbedParsable;
import net.replaceitem.discarpet.script.values.StickerValue;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

@ParsableClass(name = "message_content")
public class MessageContentParsable implements DirectParsable {
    
    String content;
    @OptionalField
    List<FileUpload> attachments = List.of();
    @OptionalField
    List<Value> stickers = List.of();
    @OptionalField
    List<EmbedParsable> embeds = List.of();
    @OptionalField
    List<List<ItemComponent>> components = List.of();
    @OptionalField @Nullable
    AllowedMentionsParsable allowed_mentions;
    @OptionalField @Nullable
    Message reply_to;
    @OptionalField @Nullable
    String nonce;
    @OptionalField
    Boolean tts = false;
    
    // message flags
    @OptionalField
    Boolean ephemeral = false;
    @OptionalField
    Boolean suppress_embeds = false;
    @OptionalField
    Boolean suppress_notifications = false;
    
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void apply(MessageRequest<?> message) {
        message.setContent(content);

        message.setFiles(Stream.of(
                attachments.stream(),
                embeds.stream().flatMap(embedParsable -> embedParsable.getFileUploads().stream())
        ).flatMap(Function.identity()).toList());
        
        if(!stickers.isEmpty()) {
            if(!(message instanceof MessageCreateAction messageCreateAction)) throw new InternalExpressionException("'stickers' is only supported for message creation, not edits");
            messageCreateAction.setStickers(stickers.stream().map(value ->
                    Sticker.fromId(value instanceof StickerValue stickerValue ?
                            stickerValue.getDelegate().getId() :
                            value.getString()
                    )).toList()
            );
        }
        
        message.setEmbeds(embeds.stream().map(EmbedParsable::construct).toList());
        
        message.setComponents(components.stream().map(ActionRow::of).toList());

        if(allowed_mentions != null) allowed_mentions.apply(message);
        
        if(message instanceof MessageCreateAction messageCreateAction) {
            if(reply_to != null) {
                messageCreateAction.setMessageReference(reply_to);
            }
            if(nonce != null) {
                messageCreateAction.setNonce(nonce);
            }
            if(tts != null) {
                messageCreateAction.setTTS(tts);
            }
            messageCreateAction.setSuppressedNotifications(suppress_notifications);
        }
        
        message.setSuppressEmbeds(suppress_embeds);
        
        if(message instanceof WebhookMessageCreateAction<?> webhookMessageCreateAction) {
            webhookMessageCreateAction.setEphemeral(ephemeral);
        }
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
