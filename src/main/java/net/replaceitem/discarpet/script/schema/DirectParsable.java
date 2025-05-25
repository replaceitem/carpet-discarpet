package net.replaceitem.discarpet.script.schema;

import carpet.script.value.Value;

public interface DirectParsable {
    boolean tryParseDirectly(Value value);
}
