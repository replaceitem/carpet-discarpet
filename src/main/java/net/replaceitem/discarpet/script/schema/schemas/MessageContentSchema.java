package net.replaceitem.discarpet.script.schema.schemas;

import carpet.script.Context;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.MapValue;
import carpet.script.value.Value;
import net.dv8tion.jda.api.components.Component;
import net.dv8tion.jda.api.components.MessageTopLevelComponent;
import net.dv8tion.jda.api.components.label.LabelChildComponent;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReference;
import net.dv8tion.jda.api.entities.sticker.Sticker;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.requests.restaction.WebhookMessageCreateAction;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.messages.AbstractMessageBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageData;
import net.dv8tion.jda.api.utils.messages.MessageRequest;
import net.replaceitem.discarpet.script.schema.DirectParsable;
import net.replaceitem.discarpet.script.schema.OptionalField;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.schemas.embeds.EmbedSchema;
import net.replaceitem.discarpet.script.values.StickerValue;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

@SchemaClass(name = "message_content")
public class MessageContentSchema implements DirectParsable {
    @OptionalField @Nullable
    String content;
    @OptionalField
    List<FileUpload> attachments = List.of();
    @OptionalField
    List<Value> stickers = List.of();
    @OptionalField
    List<EmbedSchema.EmbedWithAttachments> embeds = List.of();
    @OptionalField
    List<Component> components = List.of();
    @OptionalField
    Boolean use_components_v2 = false;
    @OptionalField @Nullable
    AllowedMentionsSchema allowed_mentions;
    @OptionalField @Nullable
    Message referenced_message;
    @OptionalField
    MessageReference.MessageReferenceType message_reference_type = MessageReference.MessageReferenceType.DEFAULT;
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

    /**
     * The fields of message content are spread through the three class hierachies of the
     * {@link AbstractMessageBuilder}, {@link MessageData} and a message rest action like {@link MessageCreateAction}.
     * Some fields only exist in the later rest action object,
     * while some need to already be set in the builder (like content).
     * That is why the functions for converting between these phases need to be passed,
     * in order to apply the schema's data all in one place.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public <B extends AbstractMessageBuilder<D,B>,D extends MessageData,A extends RestAction<?>> A apply(B builder, Function<B,D> build, Function<D,A> send) {
        // temporarily set this to something non-blank, since otherwise builder.build() would fail,later this is set
        // in the restAction stage which might set it to null for non-content messages, like forwarding or components v2.
        if(!use_components_v2) {
            builder.setContent("-");
        }

        builder.setFiles(Stream.of(
                attachments.stream(),
                embeds.stream().flatMap(embedObject -> embedObject.getFileUploads().stream())
        ).flatMap(Function.identity()).toList());
        

        builder.setEmbeds(embeds.stream().map(AttachmentSchema.WithAttachments::getData).toList());


        List<MessageTopLevelComponent> messageComponents = this.components.stream().mapMulti((Component component, Consumer<MessageTopLevelComponent> consumer) -> {
            if (!(component instanceof MessageTopLevelComponent actionRowChildComponent)) {
                var additionalInfo = component instanceof LabelChildComponent ? " Consider wrapping it inside a label." : "";
                throw new InternalExpressionException("Components of type " + component.getType().toString().toLowerCase() + " cannot be used inside a message." + additionalInfo);
            }
            consumer.accept(actionRowChildComponent);
        }).toList();
        builder.setComponents(messageComponents);
        builder.useComponentsV2(use_components_v2);

        if(allowed_mentions != null) allowed_mentions.apply(builder);

        builder.setSuppressEmbeds(suppress_embeds);
        
        
        if(tts) {
            if(builder instanceof MessageCreateBuilder messageCreateBuilder) {
                messageCreateBuilder.setTTS(true);
            } else throw onlyCreate("tts");
        }

        D data = build.apply(builder);

        A action = send.apply(data);
        
        if(action instanceof MessageRequest<?> messageRequest) {
            messageRequest.setContent(content);
        }

        if(referenced_message != null) {
            if(action instanceof MessageCreateAction messageCreateAction) {
                messageCreateAction.setMessageReference(message_reference_type, referenced_message);
            } else throw onlyCreate("referenced_message");
        }
        if(nonce != null) {
            if(action instanceof MessageCreateAction messageCreateAction) {
                messageCreateAction.setNonce(nonce);
            } else throw onlyCreate("nonce");
        }
        if(suppress_notifications) {
            if(action instanceof MessageCreateAction messageCreateAction) {
                messageCreateAction.setSuppressedNotifications(true);
            } else throw onlyCreate("suppress_notifications");

        }
        if(!stickers.isEmpty()) {
            if (action instanceof MessageCreateAction messageCreateAction) {
                messageCreateAction.setStickers(stickers.stream().map(value ->
                        Sticker.fromId(value instanceof StickerValue stickerValue ?
                                stickerValue.getDelegate().getId() :
                                value.getString()
                        )).toList()
                );
            } else throw onlyCreate("stickers");
        }
        if(ephemeral) {
            if(action instanceof WebhookMessageCreateAction<?> webhookMessageCreateAction) {
                webhookMessageCreateAction.setEphemeral(true);
            } else if (action instanceof ReplyCallbackAction replyCallbackAction) {
                replyCallbackAction.setEphemeral(true);
            } else throw new InternalExpressionException("'ephemeral' is only supported for interaction replies");
        }
        
        return action;
    }

    @Override
    public boolean tryParseDirectly(Value value, Context context) {
        if(!(value instanceof MapValue)) {
            this.content = value.getString();
            return true;
        }
        return false;
    }
    
    private static InternalExpressionException onlyCreate(String field) {
        return new InternalExpressionException("'" + field + "' is only supported for message creation, not edits");
    }
}
