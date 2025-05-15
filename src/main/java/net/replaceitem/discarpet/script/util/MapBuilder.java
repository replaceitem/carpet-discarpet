package net.replaceitem.discarpet.script.util;

import carpet.script.value.MapValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder {
    private final Map<Value, Value> map = new HashMap<>();
    
    public MapBuilder put(String key, Value value) {
        this.map.put(StringValue.of(key), value);
        return this;
    }
    
    public MapBuilder put(String key, String value) {
        this.map.put(StringValue.of(key), StringValue.of(value));
        return this;
    }
    
    public MapValue build() {
        return MapValue.wrap(map);
    }
}
