package Discarpet;

import carpet.script.exception.InternalExpressionException;
import carpet.script.value.ListValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;

import java.util.List;
import java.util.Map;

public class ScarpetMapValueUtil {

    public static Value getValueInMap(Map<Value,Value> map, String key) {
        return map.get(new StringValue(key));
    }

    public static String getStringInMap(Map<Value,Value> map, String key) {
        Value val = getValueInMap(map,key);
        if(val != null) {
            return val.getString();
        }
        throw new InternalExpressionException("Missing " + key + " value in map");
    }

    public static int getIntInMap(Map<Value,Value> map, String key) {
        Value val = getValueInMap(map,key);
        if(val != null) {
            if(val instanceof NumericValue) {
                return ((NumericValue) val).getInt();
            } else throw new InternalExpressionException(key + " value needs to be a number");
        }
        throw new InternalExpressionException("Missing " + key + " value in map");
    }

    public static boolean getBooleanInMap(Map<Value,Value> map, String key) {
        Value val = getValueInMap(map,key);
        if(val != null) {
            return val.getBoolean();
        }
        throw new InternalExpressionException("Missing " + key + " value in map");
    }

    public static List<Value> getListInMap(Map<Value,Value> map, String key) {
        Value val = getValueInMap(map,key);
        if(val != null) {
            if(val instanceof ListValue) {
                return ((ListValue) val).getItems();
            } else throw new InternalExpressionException(key + " value needs to be a number");
        }
        throw new InternalExpressionException("Missing " + key + " value in map");
    }


    public static boolean mapHasKey(Map<Value,Value> map, String key) {
        return map.containsKey(new StringValue(key));
    }
}
