package Discarpet.script.util;

import carpet.script.exception.InternalExpressionException;
import carpet.script.value.ListValue;
import carpet.script.value.NumericValue;
import carpet.script.value.Value;

import java.awt.*;
import java.time.Instant;
import java.util.List;

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
}
