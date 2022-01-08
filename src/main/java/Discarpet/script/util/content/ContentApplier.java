package Discarpet.script.util.content;

import org.javacord.api.entity.message.component.HighLevelComponent;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.io.File;
import java.net.URL;

/**
 * Class to wrap {@link org.javacord.api.entity.message.MessageBuilder},
 * {@link org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder} and
 * {@link org.javacord.api.interaction.callback.InteractionFollowupMessageBuilder} that have common methods,
 * for same handling in {@link Discarpet.script.util.MessageContentParser}.
 */

public interface ContentApplier {
    void setContent(String content);

    void addAttachment(File file, boolean spoiler);
    void addAttachment(URL url, boolean spoiler);
    void addAttachment(byte[] bytes, String name, boolean spoiler);

    void addEmbed(EmbedBuilder embed);

    void addComponents(HighLevelComponent highLevelComponent);
}
