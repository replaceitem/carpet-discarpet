package Discarpet.script.util.content;

import carpet.script.exception.InternalExpressionException;
import org.javacord.api.entity.message.component.HighLevelComponent;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;

import java.io.File;
import java.net.URL;

public class InteractionImmediateResponseContentApplier implements ContentApplier {

    private final InteractionImmediateResponseBuilder interactionImmediateResponseBuilder;

    private final InternalExpressionException NO_ATTACHMENTS_EXCEPTION = new InternalExpressionException("Attachments are not supported for the immediate response for interactions, use the followup response instead");

    public InteractionImmediateResponseContentApplier(InteractionImmediateResponseBuilder builder) {
        this.interactionImmediateResponseBuilder = builder;
    }

    public InteractionImmediateResponseBuilder get() {
        return interactionImmediateResponseBuilder;
    }

    @Override
    public void setContent(String content) {
        interactionImmediateResponseBuilder.setContent(content);
    }

    @Override
    public void addAttachment(File file, boolean spoiler) {
        throw NO_ATTACHMENTS_EXCEPTION;
    }

    @Override
    public void addAttachment(URL url, boolean spoiler) {
        throw NO_ATTACHMENTS_EXCEPTION;
    }

    @Override
    public void addAttachment(byte[] bytes, String name, boolean spoiler) {
        throw NO_ATTACHMENTS_EXCEPTION;
    }

    @Override
    public void addEmbed(EmbedBuilder embed) {
        interactionImmediateResponseBuilder.addEmbed(embed);
    }

    @Override
    public void addComponents(HighLevelComponent highLevelComponent) {
        interactionImmediateResponseBuilder.addComponents(highLevelComponent);
    }
}
