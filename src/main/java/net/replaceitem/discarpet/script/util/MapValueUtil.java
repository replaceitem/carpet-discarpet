package net.replaceitem.discarpet.script.util;

import carpet.script.exception.InternalExpressionException;
import carpet.script.value.StringValue;
import carpet.script.value.Value;

import java.util.Map;

public class MapValueUtil {

    private static Value getValueInMapNullable(Map<Value,Value> map, String key) {
        Value value = map.get(new StringValue(key));
        return (value == null || value.isNull()) ? null : value;
    }
    
    public static Value getValueInMap(Map<Value,Value> map, String key, boolean required) {
        Value value = getValueInMapNullable(map, key);
        if((value == null || value.isNull()) && required) {
            throw new InternalExpressionException("Missing required '" + key + "' value in map. ");
        }
        return value;
    }
}
