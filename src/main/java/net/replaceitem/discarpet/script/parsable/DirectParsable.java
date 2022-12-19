package net.replaceitem.discarpet.script.parsable;

import carpet.script.value.Value;

public interface DirectParsable {
    boolean tryParseDirectly(Value value);
}
