package net.replaceitem.discarpet.script.schema.schemas;

import carpet.script.Context;
import carpet.script.value.NumericValue;
import carpet.script.value.Value;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

@SchemaClass(name = "timestamp")
public class TimestampSchema implements SchemaConstructor<Instant> {
    
    Long epoch_millis;

    @Override
    public Instant construct(Context context) {
        return Instant.ofEpochMilli(epoch_millis);
    }

    @Override
    public @Nullable Instant tryCreateFromValueDirectly(Value value) {
        if (value instanceof NumericValue numericTimestamp) {
            return Instant.ofEpochMilli(numericTimestamp.getLong());
        }
        if(value.getString().equalsIgnoreCase("now")) {
            return Instant.now();
        }
        return null;
    }
}
