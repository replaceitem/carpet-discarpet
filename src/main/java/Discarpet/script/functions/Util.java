package Discarpet.script.functions;

import Discarpet.script.values.common.Deletable;
import Discarpet.script.values.common.Renamable;
import carpet.script.annotation.ScarpetFunction;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.Value;

@SuppressWarnings("unused")
public class Util {
    @ScarpetFunction
    public boolean dc_delete(Value value) {
        if(!(value instanceof Deletable deletable)) throw new InternalExpressionException(value.getTypeString() + " is not deletable");
        return deletable.delete();
    }

    @ScarpetFunction
    public boolean dc_set_name(Value value, String name) {
        if(!(value instanceof Renamable renamable)) throw new InternalExpressionException(value.getTypeString() + " is not renamable");
        return renamable.rename(name);
    }
}
