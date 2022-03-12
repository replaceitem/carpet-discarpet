package Discarpet.script.util.content;

import carpet.script.exception.InternalExpressionException;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.component.HighLevelComponent;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.message.mention.AllowedMentions;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;

import java.io.File;
import java.net.URL;

public class InteractionImmediateResponseContentApplier implements ContentApplier {

    private final InteractionImmediateResponseBuilder builder;

    private static final InternalExpressionException NOT_SUPPORTED = new InternalExpressionException("Not supported for interactions");

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
        throw NOT_SUPPORTED;
    }

    @Override
    public void addAttachment(URL url, boolean spoiler) {
        throw NOT_SUPPORTED;
    }

    @Override
    public void addAttachment(byte[] bytes, String name, boolean spoiler) {
        throw NOT_SUPPORTED;
    }

    @Override
    public void addEmbed(EmbedBuilder embed) {
        builder.addEmbed(embed);
    }

    @Override
    public void addComponents(HighLevelComponent highLevelComponent) {
        builder.addComponents(highLevelComponent);
    }

    @Override
    public void setAllowedMentions(AllowedMentions allowedMentions) {
        builder.setAllowedMentions(allowedMentions);
    }


    @Override
    public void replyTo(Message message) {
        throw NOT_SUPPORTED;
    }

    @Override
    public void setNonce(String nonce) {
        throw NOT_SUPPORTED;
    }

    @Override
    public void setTts(boolean tts) {
        builder.setTts(tts);
    }
}
