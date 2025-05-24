package net.replaceitem.discarpet.script.parsable.parsables;

import carpet.script.Context;
import carpet.script.value.NumericValue;
import carpet.script.value.Value;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

@ParsableClass(name = "timestamp")
public class TimestampParsable implements ParsableConstructor<Instant> {
    
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
