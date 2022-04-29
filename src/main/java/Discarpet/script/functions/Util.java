package Discarpet.script.functions;

import Discarpet.script.values.common.Deletable;
import carpet.script.annotation.ScarpetFunction;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.Value;

public class Util {
    @ScarpetFunction
    public boolean dc_delete(Value value) {
        if(!(value instanceof Deletable deletable)) throw new InternalExpressionException(value.getTypeString() + " is not deletable");
        return deletable.delete();
    }
}
