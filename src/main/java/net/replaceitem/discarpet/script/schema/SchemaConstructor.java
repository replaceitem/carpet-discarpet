package net.replaceitem.discarpet.script.schema;

import carpet.script.Context;
import carpet.script.value.Value;
import org.jetbrains.annotations.Nullable;

public interface SchemaConstructor<T> {
    T construct(Context context);
    default @Nullable T tryCreateFromValueDirectly(Value value) {
        return null;
    }
}
