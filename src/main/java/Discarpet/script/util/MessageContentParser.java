package Discarpet.script.util;

import Discarpet.script.util.content.ContentApplier;
import Discarpet.script.values.MessageValue;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.MapValue;
import carpet.script.value.Value;
import org.javacord.api.entity.message.mention.AllowedMentionsBuilder;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static Discarpet.script.util.MapValueUtil.*;

public class MessageContentParser {
    public static void parseMessageContent(ContentApplier a, Value value) {
        try {
            if(value instanceof MapValue mapContent) {
                Map<Value, Value> map = mapContent.getMap();

                if(mapHasKey(map,"content")) a.setContent(getStringInMap(map,"content"));
                if(mapHasKey(map,"attachments")) setAttachments(a, getListInMap(map,"attachments"));
                if(mapHasKey(map,"embeds")) setEmbeds(a, getListInMap(map,"embeds"));
                if(mapHasKey(map,"components")) setComponents(a, getListInMap(map,"components"));
                if(mapHasKey(map,"allowed_mentions")) setAllowedMentions(a, getValueInMap(map,"allowed_mentions"));
                if(mapHasKey(map,"reply_to")) setReplyTo(a, getValueInMap(map,"reply_to"));
                if(mapHasKey(map,"nonce")) a.setNonce(getStringInMap(map,"nonce"));
                if(mapHasKey(map,"tts")) a.setTts(getBooleanInMap(map,"tts"));
                return;
            }
            a.setContent(value.getString());
        } catch (Exception ex) {
            throw new InternalExpressionException("Could not parse message content: " + ex.getMessage());
        }
    }

    private static void setAttachments(ContentApplier a, List<Value> attachments) {
        for (int i = 0; i < attachments.size(); i++) {
            Value attachment = attachments.get(i);
            try {
                addAttachment(a, attachment);
            } catch (Exception ex) {
                throw new InternalExpressionException("Could not parse attachment with index " + i + " inside 'attachments' list: " + ex.getMessage());
            }
        }
    }

    private static void addAttachment(ContentApplier a, Value attachment) {
        if(!(attachment instanceof MapValue attachmentMap)) throw new InternalExpressionException("Expected a map value");
        Map<Value, Value> map = attachmentMap.getMap();

        if(mapHasKey(map,"file")) addFileAttachment(a, map);
        else if(mapHasKey(map,"url")) addUrlAttachment(a, map);
        else if(mapHasKey(map,"bytes")) addBytesAttachment(a, map);
        else throw new InternalExpressionException("Expected either 'file', 'url' or 'bytes' value in map for attachment");
    }

    private static void addFileAttachment(ContentApplier a, Map<Value, Value> map) {
        String path = getStringInMap(map,"file");
        File file = new File(path);
        if(!file.exists()) throw new InternalExpressionException("Invalid path for attachment file: '" + path + "'");
        boolean spoiler = getBooleanInMapOrDefault(map, "spoiler", false);
        a.addAttachment(file,spoiler);
    }

    private static void addUrlAttachment(ContentApplier a, Map<Value, Value> map) {
        String path = getStringInMap(map,"url");
        URL url;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            throw new InternalExpressionException("Invalid URL for attachment file: '" + path + "': " + e);
        }
        boolean spoiler = getBooleanInMapOrDefault(map, "spoiler", false);
        a.addAttachment(url,spoiler);
    }

    private static void addBytesAttachment(ContentApplier a, Map<Value, Value> map) {
        String name = getStringInMap(map,"name");
        byte[] data = getStringInMap(map,"bytes").getBytes();
        boolean spoiler = getBooleanInMapOrDefault(map, "spoiler", false);
        a.addAttachment(data,name,spoiler);
    }



    private static void setEmbeds(ContentApplier a, List<Value> embeds) {
        for (int i = 0; i < embeds.size(); i++) {
            Value embed = embeds.get(i);
            try {
                addEmbed(a, embed);
            } catch (Exception ex) {
                throw new InternalExpressionException("Could not parse embed with index " + i + " inside 'embeds' list: " + ex.getMessage());
            }
        }
    }

    private static void addEmbed(ContentApplier a, Value embed) {
        a.addEmbed(EmbedParser.parseEmbed(embed));
    }

    private static void setComponents(ContentApplier a, List<Value> components) {
        for (int i = 0; i < components.size(); i++) {
            Value component = components.get(i);
            try {
                a.addComponent(MessageComponentParser.parseMessageComponent(component));
            } catch (Exception ex) {
                throw new InternalExpressionException("Could not parse component row with index " + i + " inside 'components' list: " + ex.getMessage());
            }
        }
    }
    
    private static void setAllowedMentions(ContentApplier a, Value value) {
        try {
            if(!(value instanceof MapValue mapValue)) throw new InternalExpressionException("Allowed mentions needs to be a map value");
            Map<Value, Value> map = mapValue.getMap();
            AllowedMentionsBuilder b = new AllowedMentionsBuilder();
            b.setMentionRoles(getBooleanInMapOrDefault(map,"mention_roles",false));
            b.setMentionUsers(getBooleanInMapOrDefault(map,"mention_users",false));
            b.setMentionEveryoneAndHere(getBooleanInMapOrDefault(map,"mention_everyone",false));
            if(mapHasKey(map,"roles")) addRoles(b,getListInMap(map,"roles"));
            if(mapHasKey(map,"users")) addUsers(b,getListInMap(map,"users"));
            a.setAllowedMentions(b.build());
        } catch (Exception ex) {
            throw new InternalExpressionException("Could not parse allowed mentions: " + ex.getMessage());
        }
    }
    
    private static void addRoles(AllowedMentionsBuilder b, List<Value> list) {
        for (int i = 0; i < list.size(); i++) {
            Value roleValue = list.get(i);
            try {
                b.addRole(MiscParser.parseRoleId(roleValue));
            } catch (Exception ex) {
                throw new InternalExpressionException("Could not parse role with index " + i + " inside 'roles' list: " + ex.getMessage());
            }
        }
    }

    private static void addUsers(AllowedMentionsBuilder b, List<Value> list) {
        for (int i = 0; i < list.size(); i++) {
            Value userValue = list.get(i);
            try {
                b.addUser(MiscParser.parseUserId(userValue));
            } catch (Exception ex) {
                throw new InternalExpressionException("Could not parse user with index " + i + " inside 'users' list: " + ex.getMessage());
            }
        }
    }

    private static void setReplyTo(ContentApplier a, Value value) {
        if(!(value instanceof MessageValue messageValue)) throw new InternalExpressionException("reply_to needs to be a message value");
        a.replyTo(messageValue.getMessage());
    }
}
