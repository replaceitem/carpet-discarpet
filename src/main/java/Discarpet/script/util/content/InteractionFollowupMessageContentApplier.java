package Discarpet.script.util.content;

import org.javacord.api.entity.message.component.HighLevelComponent;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.callback.InteractionFollowupMessageBuilder;

import java.io.File;
import java.net.URL;

public class InteractionFollowupMessageContentApplier implements ContentApplier {

    private final InteractionFollowupMessageBuilder interactionFollowupMessageBuilder;

    public InteractionFollowupMessageContentApplier(InteractionFollowupMessageBuilder builder) {
        this.interactionFollowupMessageBuilder = builder;
    }

    public InteractionFollowupMessageBuilder get() {
        return interactionFollowupMessageBuilder;
    }

    @Override
    public void setContent(String content) {
        interactionFollowupMessageBuilder.setContent(content);
    }

    @Override
    public void addAttachment(File file, boolean spoiler) {
        if(spoiler)
            interactionFollowupMessageBuilder.addAttachmentAsSpoiler(file);
        else
            interactionFollowupMessageBuilder.addAttachment(file);
    }

    @Override
    public void addAttachment(URL url, boolean spoiler) {
        if(spoiler)
            interactionFollowupMessageBuilder.addAttachmentAsSpoiler(url);
        else
            interactionFollowupMessageBuilder.addAttachment(url);
    }

    @Override
    public void addAttachment(byte[] bytes, String name, boolean spoiler) {
        if(spoiler)
            interactionFollowupMessageBuilder.addAttachmentAsSpoiler(bytes,name);
        else
            interactionFollowupMessageBuilder.addAttachment(bytes,name);
    }

    @Override
    public void addEmbed(EmbedBuilder embed) {
        interactionFollowupMessageBuilder.addEmbed(embed);
    }

    @Override
    public void addComponents(HighLevelComponent highLevelComponent) {
        interactionFollowupMessageBuilder.addComponents(highLevelComponent);
    }
}
