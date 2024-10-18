package net.replaceitem.discarpet.script.functions;

import net.replaceitem.discarpet.script.values.common.Deletable;
import net.replaceitem.discarpet.script.values.common.Renamable;
import carpet.script.annotation.ScarpetFunction;
import carpet.script.exception.InternalExpressionException;
import carpet.script.value.Value;

import static net.replaceitem.discarpet.script.util.ValueUtil.*;

@SuppressWarnings("unused")
public class Util {
    @ScarpetFunction(maxParams = 2)
    public void dc_delete(Value value, String... reason) {
        if(!(value instanceof Deletable deletable)) throw new InternalExpressionException(value.getTypeString() + " is not deletable");
        deletable.delete(optionalArg(reason).orElse(null));
    }

    @ScarpetFunction
    public void dc_set_name(Value value, String name) {
        if(!(value instanceof Renamable renamable)) throw new InternalExpressionException(value.getTypeString() + " is not renamable");
        renamable.rename(name);
    }
}
