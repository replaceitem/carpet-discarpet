package Discarpet.script.util.content;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.HighLevelComponent;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.io.File;
import java.net.URL;

public class MessageContentApplier implements ContentApplier {

    private final MessageBuilder messageBuilder;

    public MessageContentApplier(MessageBuilder builder) {
        this.messageBuilder = builder;
    }

    public MessageBuilder get() {
        return messageBuilder;
    }

    @Override
    public void setContent(String content) {
        messageBuilder.setContent(content);
    }

    @Override
    public void addAttachment(File file, boolean spoiler) {
        if(spoiler)
            messageBuilder.addAttachmentAsSpoiler(file);
        else
            messageBuilder.addAttachment(file);
    }

    @Override
    public void addAttachment(URL url, boolean spoiler) {
        if(spoiler)
            messageBuilder.addAttachmentAsSpoiler(url);
        else
            messageBuilder.addAttachment(url);
    }

    @Override
    public void addAttachment(byte[] bytes, String name, boolean spoiler) {
        if(spoiler)
            messageBuilder.addAttachmentAsSpoiler(bytes,name);
        else
            messageBuilder.addAttachment(bytes,name);
    }

    @Override
    public void addEmbed(EmbedBuilder embed) {
        messageBuilder.addEmbed(embed);
    }

    @Override
    public void addComponents(HighLevelComponent highLevelComponent) {
        messageBuilder.addComponents(highLevelComponent);
    }
}
