package net.replaceitem.discarpet.script.parsable.parsables;

import net.replaceitem.discarpet.script.parsable.DirectParsable;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import net.replaceitem.discarpet.script.parsable.ParsableConstructor;
import carpet.script.value.NumericValue;
import carpet.script.value.Value;

import java.time.Instant;

@ParsableClass(name = "timestamp")
public class TimestampParsable implements ParsableConstructor<Instant>, DirectParsable {
    
    Long epoch_millis;
    
    @Override
    public boolean tryParseDirectly(Value value) {
        if (value instanceof NumericValue numericTimestamp) {
            this.epoch_millis = Instant.ofEpochMilli(numericTimestamp.getLong()).toEpochMilli();
            return true;
        }
        if(value.getString().equalsIgnoreCase("now")) {
            this.epoch_millis = Instant.now().toEpochMilli();
            return true;
        }
        return false;
    }

    @Override
    public Instant construct() {
        return Instant.ofEpochMilli(epoch_millis);
    }
}
