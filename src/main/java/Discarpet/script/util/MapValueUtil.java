package Discarpet.script.util;

import carpet.script.exception.InternalExpressionException;
import carpet.script.value.ListValue;
import carpet.script.value.MapValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class MapValueUtil {

    private static Value getValueInMapNullable(Map<Value,Value> map, String key) {
        Value value = map.get(new StringValue(key));
        return (value == null || value.isNull()) ? null : value;
    }

    @NotNull
    public static Value getValueInMap(Map<Value,Value> map, String key) {
        return getValueInMap(map,key, true);
    }
    
    public static Value getValueInMap(Map<Value,Value> map, String key, boolean required) {
        Value value = getValueInMapNullable(map, key);
        if((value == null || value.isNull()) && required) {
            throw new InternalExpressionException("Missing required '" + key + "' value in map. ");
        }
        return value;
    }
    
    @NotNull
    public static String getStringInMap(Map<Value,Value> map, String key) {
        return getValueInMap(map,key).getString();
    }

    public static int getIntInMap(Map<Value,Value> map, String key) {
        Value val = getValueInMap(map,key);
        if(val instanceof NumericValue numericValue) {
            return numericValue.getInt();
        } else throw new InternalExpressionException("'" + key + "' value needs to be a number");
    }

    public static boolean getBooleanInMap(Map<Value,Value> map, String key) {
        Value val = getValueInMap(map,key);
        return val.getBoolean();
    }

    public static boolean getBooleanInMapOrDefault(Map<Value,Value> map, String key, boolean defaultValue) {
        Value val = getValueInMapNullable(map,key);
        if(val == null) return defaultValue;
        return val.getBoolean();
    }

    @NotNull
    public static List<Value> getListInMap(Map<Value,Value> map, String key) {
        Value val = getValueInMap(map,key);
        if(val instanceof ListValue listValue) {
            return listValue.getItems();
        } else throw new InternalExpressionException("'" + key + "' value needs to be a list");
    }

    @NotNull
    public static Map<Value,Value> getMapInMap(Map<Value,Value> map, String key) {
        Value val = getValueInMap(map,key);
        if(val instanceof MapValue mapValue) {
            return mapValue.getMap();
        } else throw new InternalExpressionException("'" + key + "' value needs to be a map");
    }


    public static boolean mapHasKey(Map<Value,Value> map, String key) {
        return map.containsKey(new StringValue(key));
    }
}
