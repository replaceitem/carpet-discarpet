package Discarpet.script.util.content;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.HighLevelComponent;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.message.mention.AllowedMentions;

import java.io.File;
import java.net.URL;

public class MessageContentApplier implements ContentApplier {

    private final MessageBuilder builder;

    public MessageContentApplier(MessageBuilder builder) {
        this.builder = builder;
    }

    public MessageBuilder get() {
        return builder;
    }

    @Override
    public void setContent(String content) {
        builder.setContent(content);
    }

    @Override
    public void addAttachment(File file, boolean spoiler) {
        if(spoiler)
            builder.addAttachmentAsSpoiler(file);
        else
            builder.addAttachment(file);
    }

    @Override
    public void addAttachment(URL url, boolean spoiler) {
        if(spoiler)
            builder.addAttachmentAsSpoiler(url);
        else
            builder.addAttachment(url);
    }

    @Override
    public void addAttachment(byte[] bytes, String name, boolean spoiler) {
        if(spoiler)
            builder.addAttachmentAsSpoiler(bytes,name);
        else
            builder.addAttachment(bytes,name);
    }

    @Override
    public void addEmbed(EmbedBuilder embed) {
        builder.addEmbed(embed);
    }

    @Override
    public void addComponent(HighLevelComponent highLevelComponent) {
        builder.addComponents(highLevelComponent);
    }

    @Override
    public void setAllowedMentions(AllowedMentions allowedMentions) {
        builder.setAllowedMentions(allowedMentions);
    }

    @Override
    public void replyTo(Message message) {
        builder.replyTo(message);
    }

    @Override
    public void setNonce(String nonce) {
        builder.setNonce(nonce);
    }

    @Override
    public void setTts(boolean tts) {
        builder.setTts(tts);
    }
}
