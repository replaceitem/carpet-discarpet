package net.replaceitem.discarpet.script.util.content;

import carpet.script.exception.InternalExpressionException;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.component.HighLevelComponent;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.message.mention.AllowedMentions;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.EnumSet;

public class InteractionImmediateResponseContentApplier implements ContentApplier {

    private final InteractionImmediateResponseBuilder builder;

    private static void throwNotSupported(String what) {
        throw new InternalExpressionException("'" + what + "' is not supported for immediate interaction response");
    }

    public InteractionImmediateResponseContentApplier(InteractionImmediateResponseBuilder builder) {
        this.builder = builder;
    }

    public InteractionImmediateResponseBuilder get() {
        return builder;
    }

    @Override
    public void setContent(String content) {
        builder.setContent(content);
    }

    @Override
    public void addAttachment(File file, boolean spoiler) {
        if(file == null) return;
        throwNotSupported("attachment");
    }

    @Override
    public void addAttachment(URL url, boolean spoiler) {
        if(url == null) return;
        throwNotSupported("attachment");
    }

    @Override
    public void addAttachment(byte[] bytes, String name, boolean spoiler) {
        if(bytes == null) return;
        throwNotSupported("attachment");
    }

    @Override
    public void addAttachment(BufferedImage image, String name, boolean spoiler) {
        throwNotSupported("attachment");
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
        if(message == null) return;
        throwNotSupported("reply_to");
    }

    @Override
    public void setNonce(String nonce) {
        if(nonce == null) return;
        throwNotSupported("nonce");
    }

    @Override
    public void setTts(boolean tts) {
        builder.setTts(tts);
    }

    @Override
    public void setFlags(EnumSet<MessageFlag> flags) {
        builder.setFlags(flags);
    }
}
