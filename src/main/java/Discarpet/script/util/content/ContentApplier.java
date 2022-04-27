package Discarpet.script.util.content;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.component.HighLevelComponent;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.message.mention.AllowedMentions;

import java.io.File;
import java.net.URL;

/**
 * Class to wrap different javacord classes for building messages for different purposes, but do the same thing.
 */

public interface ContentApplier {
    void setContent(String content);

    void addAttachment(File file, boolean spoiler);
    void addAttachment(URL url, boolean spoiler);
    void addAttachment(byte[] bytes, String name, boolean spoiler);

    void addEmbed(EmbedBuilder embed);

    void addComponent(HighLevelComponent highLevelComponent);
    
    void setAllowedMentions(AllowedMentions allowedMentions);
    
    void replyTo(Message message);
    void setNonce(String nonce);
    void setTts(boolean tts);
}
