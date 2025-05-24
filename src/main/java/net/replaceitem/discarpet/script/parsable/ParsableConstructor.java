package net.replaceitem.discarpet.script.parsable;

import carpet.script.Context;
import carpet.script.value.Value;
import org.jetbrains.annotations.Nullable;

public interface ParsableConstructor<T> {
    T construct(Context context);
    default @Nullable T tryCreateFromValueDirectly(Value value) {
        return null;
    }
}
