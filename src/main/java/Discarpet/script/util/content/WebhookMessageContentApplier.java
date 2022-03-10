package Discarpet.script.util.content;

import org.javacord.api.entity.message.WebhookMessageBuilder;
import org.javacord.api.entity.message.component.HighLevelComponent;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.io.File;
import java.net.URL;

public class WebhookMessageContentApplier implements ContentApplier {

    private final WebhookMessageBuilder webhookMessageBuilder;

    public WebhookMessageContentApplier(WebhookMessageBuilder builder) {
        this.webhookMessageBuilder = builder;
    }

    public WebhookMessageBuilder get() {
        return webhookMessageBuilder;
    }

    @Override
    public void setContent(String content) {
        webhookMessageBuilder.setContent(content);
    }

    @Override
    public void addAttachment(File file, boolean spoiler) {
        if(spoiler)
            webhookMessageBuilder.addAttachmentAsSpoiler(file);
        else
            webhookMessageBuilder.addAttachment(file);
    }

    @Override
    public void addAttachment(URL url, boolean spoiler) {
        if(spoiler)
            webhookMessageBuilder.addAttachmentAsSpoiler(url);
        else
            webhookMessageBuilder.addAttachment(url);
    }

    @Override
    public void addAttachment(byte[] bytes, String name, boolean spoiler) {
        if(spoiler)
            webhookMessageBuilder.addAttachmentAsSpoiler(bytes,name);
        else
            webhookMessageBuilder.addAttachment(bytes,name);
    }

    @Override
    public void addEmbed(EmbedBuilder embed) {
        webhookMessageBuilder.addEmbed(embed);
    }

    @Override
    public void addComponents(HighLevelComponent highLevelComponent) {
        webhookMessageBuilder.addComponents(highLevelComponent);
    }
}
