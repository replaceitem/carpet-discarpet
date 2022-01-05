package Discarpet.script.util;

import Discarpet.script.values.EmbedBuilderValue;
import carpet.CarpetServer;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.MapValue;
import carpet.script.value.Value;
import org.javacord.api.entity.message.MessageBuilder;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static Discarpet.script.util.MapValueUtil.*;

public class MessageContentParser {
    public static MessageBuilder parseMessageContent(Value value) {
        try {
            MessageBuilder m = new MessageBuilder();
            if(value instanceof MapValue mapContent) {
                Map<Value, Value> map = mapContent.getMap();

                if(mapHasKey(map,"content")) m.setContent(getStringInMap(map,"content"));
                if(mapHasKey(map,"attachments")) setAttachments(m, getListInMap(map,"attachments"));
                if(mapHasKey(map,"embeds")) setEmbeds(m, getListInMap(map,"embeds"));
                if(mapHasKey(map,"components")) setComponents(m, getListInMap(map,"components"));

                return m;
            }
            m.setContent(value.getString());
            return m;
        } catch (Exception ex) {
            throw new InternalExpressionException("Could not parse message content: " + ex.getMessage());
        }
    }

    private static void setAttachments(MessageBuilder m, List<Value> attachments) {
        for (int i = 0; i < attachments.size(); i++) {
            Value attachment = attachments.get(i);
            try {
                addAttachment(m, attachment);
            } catch (Exception ex) {
                throw new InternalExpressionException("Could not parse attachment with index " + i + " inside 'attachments' list: " + ex.getMessage());
            }
        }
    }

    private static void addAttachment(MessageBuilder m, Value attachment) {
        if(!(attachment instanceof MapValue attachmentMap)) throw new InternalExpressionException("Expected a map value");
        Map<Value, Value> map = attachmentMap.getMap();

        if(mapHasKey(map,"file")) addFileAttachment(m, map);
        else if(mapHasKey(map,"url")) addUrlAttachment(m, map);
        else if(mapHasKey(map,"bytes")) addBytesAttachment(m, map);
        else throw new InternalExpressionException("Expected either 'file', 'url' or 'bytes' value in map for attachment");
    }

    private static void addFileAttachment(MessageBuilder m, Map<Value, Value> map) {
        boolean spoiler = getBooleanInMapOrDefault(map, "spoiler", false);
        String path = getStringInMap(map,"file");
        File file = new File(path);
        if(!file.exists()) throw new InternalExpressionException("Invalid path for attachment file: '" + path + "'");
        if(spoiler) m.addAttachmentAsSpoiler(file);
        else m.addAttachment(file);
    }

    private static void addUrlAttachment(MessageBuilder m, Map<Value, Value> map) {
        boolean spoiler = getBooleanInMapOrDefault(map, "spoiler", false);
        String path = getStringInMap(map,"url");
        URL url;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            throw new InternalExpressionException("Invalid URL for attachment file: '" + path + "': " + e);
        }
        if(spoiler) m.addAttachmentAsSpoiler(url);
        else m.addAttachment(url);
    }

    private static void addBytesAttachment(MessageBuilder m, Map<Value, Value> map) {
        boolean spoiler = getBooleanInMapOrDefault(map, "spoiler", false);
        String name = getStringInMap(map,"name");
        byte[] data = getStringInMap(map,"bytes").getBytes();
        if(spoiler) m.addAttachmentAsSpoiler(data,name);
        else m.addAttachment(data,name);
    }



    private static void setEmbeds(MessageBuilder m, List<Value> embeds) {
        for (int i = 0; i < embeds.size(); i++) {
            Value embed = embeds.get(i);
            try {
                addEmbed(m, embed);
            } catch (Exception ex) {
                throw new InternalExpressionException("Could not parse embed with index " + i + " inside 'embeds' list: " + ex.getMessage());
            }
        }
    }

    private static void addEmbed(MessageBuilder m, Value embed) {
        if(embed instanceof EmbedBuilderValue embedBuilderValue) {
            // using global host, as I won't bother getting context to this deep in the trace
            CarpetServer.scriptServer.globalHost.issueDeprecation("EmbedBuilder value");
            m.addEmbed(embedBuilderValue.getEmbedBuilder());
            return;
        }
        m.addEmbed(EmbedParser.parseEmbed(embed));
    }

    private static void setComponents(MessageBuilder m, List<Value> components) {
        for (int i = 0; i < components.size(); i++) {
            Value component = components.get(i);
            try {
                m.addComponents(MessageComponentParser.parseMessageComponent(component));
            } catch (Exception ex) {
                throw new InternalExpressionException("Could not parse component row with index " + i + " inside 'components' list: " + ex.getMessage());
            }
        }
    }
}
