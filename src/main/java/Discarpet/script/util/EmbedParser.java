package Discarpet.script.util;

import Discarpet.script.values.UserValue;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.MapValue;
import carpet.script.value.Value;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.util.List;
import java.util.Map;

import static Discarpet.script.util.MapValueUtil.*;

public class EmbedParser {
    public static EmbedBuilder parseEmbed(Value value) {
        try {
            EmbedBuilder e = new EmbedBuilder();
            if (!(value instanceof MapValue)) return null;
            Map<Value, Value> map = ((MapValue) value).getMap();
            e.setTitle(getStringInMap(map, "title"));

            if(mapHasKey(map, "url")) e.setUrl(getStringInMap(map, "url"));
            if(mapHasKey(map, "description")) e.setDescription(getStringInMap(map, "description"));
            if(mapHasKey(map, "author")) setAuthor(e, getValueInMap(map, "author"));
            if(mapHasKey(map, "fields")) setFields(e, getListInMap(map, "fields"));
            if(mapHasKey(map, "color")) e.setColor(MiscParser.parseColor(getValueInMap(map, "color")));
            if(mapHasKey(map, "footer")) setFooter(e, getValueInMap(map, "footer"));
            if(mapHasKey(map, "image")) e.setImage(getStringInMap(map, "image"));
            if(mapHasKey(map, "thumbnail")) e.setImage(getStringInMap(map, "thumbnail"));
            if(mapHasKey(map, "timestamp")) e.setTimestamp(MiscParser.parseTimestamp(getValueInMap(map, "timestamp")));

            return e;
        } catch (Exception ex) {
            throw new InternalExpressionException("Could not parse embed: " + ex.getMessage());
        }
    }

    private static void setAuthor(EmbedBuilder e, Value author) {
        try {
            if (author instanceof UserValue userAuthor) {
                e.setAuthor(userAuthor.getUser());
                return;
            }
            if (author instanceof MapValue mapAuthor) {
                Map<Value, Value> map = mapAuthor.getMap();
                e.setAuthor(
                        getStringInMap(map, "name"),
                        getStringInMap(map, "url"),
                        getStringInMap(map, "icon")
                );
                return;
            }
            e.setAuthor(author.getString());
        } catch (Exception ex) {
            throw new InternalExpressionException("Could not parse author: " + ex.getMessage());
        }
    }

    private static void setFields(EmbedBuilder e, List<Value> fields) {
        for (int i = 0; i < fields.size(); i++) {
            Value field = fields.get(i);
            try {
                addField(e, field);
            } catch (Exception ex) {
                throw new InternalExpressionException("Could not parse field with index " + i + " inside 'fields' list: " + ex.getMessage());
            }
        }
    }

    private static void addField(EmbedBuilder e, Value field) {
        if(!(field instanceof MapValue fieldMap)) throw new InternalExpressionException("Expected a map value");
        Map<Value, Value> map = fieldMap.getMap();
        e.addField(
                getStringInMap(map,"name"),
                getStringInMap(map,"value"),
                getBooleanInMapOrDefault(map,"inline",false)
        );
    }


    private static void setFooter(EmbedBuilder e, Value footer) {
        try {
            if (footer instanceof MapValue mapFooter) {
                Map<Value, Value> map = mapFooter.getMap();
                e.setFooter(
                        getStringInMap(map, "text"),
                        getStringInMap(map, "icon")
                );
                return;
            }
            e.setFooter(footer.getString());
        } catch (Exception ex) {
            throw new InternalExpressionException("Could not parse footer: " + ex.getMessage());
        }
    }
}
