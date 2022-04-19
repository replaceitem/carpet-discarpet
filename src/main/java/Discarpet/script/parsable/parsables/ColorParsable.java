package Discarpet.script.parsable.parsables;

import Discarpet.script.parsable.DirectParsable;
import Discarpet.script.parsable.ParsableClass;
import Discarpet.script.parsable.ParsableConstructor;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.ListValue;
import carpet.script.value.NumericValue;
import carpet.script.value.Value;

import java.awt.Color;
import java.util.List;

@ParsableClass(name = "color")
public class ColorParsable implements ParsableConstructor<Color>, DirectParsable {
    
    Integer r;
    Integer g;
    Integer b;
    
    @Override
    public boolean tryParseDirectly(Value value) {
        if(value instanceof ListValue listValue) {
            List<Value> list = listValue.getItems();
            if(list.size() != 3) throw new InternalExpressionException("Color list needs to a list with length 3 [r,g,b]");
            this.r = NumericValue.asNumber(list.get(0),"r").getInt();
            this.g = NumericValue.asNumber(list.get(1),"g").getInt();
            this.b = NumericValue.asNumber(list.get(2),"b").getInt();
            return true;
        }
        if(value instanceof NumericValue numericValue) {
            Color color = new Color(numericValue.getInt());
            this.r = color.getRed();
            this.g = color.getGreen();
            this.b = color.getBlue();
            return true;
        }
        return false;
    }

    @Override
    public Color construct() {
        return new Color(r,g,b);
    }
}
