package Discarpet.script.util;

import carpet.script.exception.InternalExpressionException;
import carpet.script.value.ListValue;
import carpet.script.value.MapValue;
import carpet.script.value.NumericValue;
import carpet.script.value.Value;
import org.javacord.api.entity.message.WebhookMessageBuilder;
import org.javacord.api.entity.webhook.WebhookBuilder;
import org.javacord.api.entity.webhook.WebhookUpdater;

import java.awt.*;
import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static Discarpet.script.util.MapValueUtil.*;

public class MiscParser {
    public static Color parseColor(Value color) {
        try {
            if(color instanceof ListValue listColor) {
                List<Value> items = listColor.getItems();
                if(items.size() != 3) throw new InternalExpressionException("Expected a list with 3 numbers (R, G, B)");
                return new Color(NumericValue.asNumber(items.get(0)).getInt(),NumericValue.asNumber(items.get(1)).getInt(),NumericValue.asNumber(items.get(2)).getInt());
            }
            return new Color(NumericValue.asNumber(color).getInt());
        } catch (Exception ex) {
            throw new InternalExpressionException("Could not parse color: " + ex.getMessage());
        }
    }

    public static Instant parseTimestamp(Value timestamp) {
        try {
            if (timestamp instanceof NumericValue numericTimestamp) {
                return Instant.ofEpochMilli(numericTimestamp.getLong());
            }
            if(timestamp.toString().equalsIgnoreCase("now")) return Instant.now();
            return Instant.parse(timestamp.toString());
        } catch (Exception ex) {
            throw new InternalExpressionException("Could not parse timestamp: " + ex.getMessage());
        }
    }
    
    public static WebhookBuilder parseWebhookProfile(Value webhookBuilder, WebhookBuilder builder) {
        try {
            if(!(webhookBuilder instanceof MapValue mapValue)) throw new InternalExpressionException("Webhook profile needs to be a map value");
            Map<Value, Value> map = mapValue.getMap();
            builder.setName(getStringInMap(map,"name"));
            if(mapHasKey(map,"avatar")) builder.setAvatar(new URL(getStringInMap(map,"avatar")));
            if(mapHasKey(map,"reason")) builder.setAuditLogReason(getStringInMap(map,"reason"));
            return builder;
        } catch (Exception ex) {
            throw new InternalExpressionException("Could not parse webhook profile: " + ex.getMessage());
        }
    }

    public static WebhookUpdater parseWebhookProfileUpdater(Value webhookBuilder, WebhookUpdater updater) {
        try {
            if(!(webhookBuilder instanceof MapValue mapValue)) throw new InternalExpressionException("Webhook profile needs to be a map value");
            Map<Value, Value> map = mapValue.getMap();
            if(mapHasKey(map,"name")) updater.setName(getStringInMap(map,"name"));
            if(mapHasKey(map,"avatar")) updater.setAvatar(new URL(getStringInMap(map,"avatar")));
            if(mapHasKey(map,"reason")) updater.setAuditLogReason(getStringInMap(map,"reason"));
            return updater;
        } catch (Exception ex) {
            throw new InternalExpressionException("Could not parse webhook profile: " + ex.getMessage());
        }
    }

    public static WebhookMessageBuilder parseWebhookMessageContentProfile(Value webhookBuilder, WebhookMessageBuilder builder) {
        try {
            if(!(webhookBuilder instanceof MapValue mapValue)) throw new InternalExpressionException("Webhook profile needs to be a map value");
            Map<Value, Value> map = mapValue.getMap();
            if(mapHasKey(map,"name")) builder.setDisplayName(getStringInMap(map,"name"));
            if(mapHasKey(map,"avatar")) builder.setDisplayAvatar(new URL(getStringInMap(map,"avatar")));
            return builder;
        } catch (Exception ex) {
            throw new InternalExpressionException("Could not parse webhook profile: " + ex.getMessage());
        }
    }
}
