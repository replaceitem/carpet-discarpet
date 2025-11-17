package net.replaceitem.discarpet.script.schema.schemas;

import carpet.script.Context;
import net.replaceitem.discarpet.script.schema.DirectParsable;
import net.replaceitem.discarpet.script.schema.SchemaClass;
import net.replaceitem.discarpet.script.schema.SchemaConstructor;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.ListValue;
import carpet.script.value.NumericValue;
import carpet.script.value.Value;
import org.jetbrains.annotations.Nullable;

import java.awt.Color;
import java.util.List;

@SchemaClass(name = "color")
public class ColorSchema implements SchemaConstructor<Color>, DirectParsable {
    
    Integer r;
    Integer g;
    Integer b;
    
    @Override
    public boolean tryParseDirectly(Value value, Context context) {
        if(value instanceof ListValue listValue) {
            List<Value> list = listValue.getItems();
            if(list.size() != 3) throw new InternalExpressionException("Color list needs to a list with length 3 [r,g,b]");
            this.r = NumericValue.asNumber(list.get(0),"r").getInt();
            this.g = NumericValue.asNumber(list.get(1),"g").getInt();
            this.b = NumericValue.asNumber(list.get(2),"b").getInt();
            return true;
        }
        return false;
    }

    @Override
    public Color construct(Context context) {
        return new Color(r,g,b);
    }

    @Override
    public @Nullable Color tryCreateFromValueDirectly(Value value) {
        if(value instanceof NumericValue numericValue) {
            return new Color(numericValue.getInt());
        }
        return null;
    }
}
